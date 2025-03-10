package Controlador;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;



import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class ControladorEmail {

    private static final String correo = System.getenv("woktopiacoworking@gmail.com"); // Obtiene de variable de entorno
    private static final String contrasena = System.getenv("zywz ibuv kyhq zmvk"); // Obtiene de variable de entorno

    public String cargarPlantilla(String ruta, String cliente, String horaInicio, String horaFin, String espacio, String subtotal) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error cargando la plantilla";
        }

        // Reemplazar los valores dinámicos
        return contenido.toString()
                .replace("{{CLIENTE}}", cliente)
                .replace("{{HORA_INICIO}}", horaInicio)
                .replace("{{HORA_FIN}}", horaFin)
                .replace("{{ESPACIO}}", espacio)
                .replace("{{SUBTOTAL}}", subtotal);
    }

    public void enviarCorreo(String destinatario, String asunto, String mensajeHtml) {
        if (correo == null || contrasena == null) {
            System.out.println("Error: Credenciales de email no configuradas correctamente.");
            return;
        }

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");

        Session session = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo, contrasena);
            }
        });

        try {
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress(correo));
            email.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            email.setSubject(asunto);
            email.setContent(mensajeHtml, "text/html");

            Transport.send(email);
            System.out.println("Se ha enviado el email correctamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar el correo.");
        }
    }
}


