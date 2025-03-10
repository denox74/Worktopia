package Controlador;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;

public class ControladorEmail {

    private static final String correo = "woktopiacoworking@gmail.com"; // Obtiene de variable de entorno
    private static final String contrasena = "zywzibuvkyhqzmvk"; // Obtiene de variable de entorno

    public String cargarPlantilla(String ruta, String cliente, String horaInicio, String horaFin, String espacio, String subtotal,int idReserva) {
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
                .replace("{{SUBTOTAL}}", subtotal)
                .replace("{{IDRESERVA}}", String.valueOf(idReserva));
    }

    public void enviarCorreo(String destinatario, String asunto, String mensajeHtml) {

        Properties propiedades = new Properties();
        propiedades.setProperty("mail.mime.adress.strict", "false");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        propiedades.put("mail.smtp.charset", "utf-8");
        propiedades.setProperty("mail.smtp.auth", "true");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        propiedades.setProperty("mail.smtp.port", "587");

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
            email.setContent(mensajeHtml, "text/html; charset=utf-8");

            new Thread(() -> { // se queda la aplicacion abierto y no se bloquea cuando mande el correo
                try {
                    Transport transport =  session.getTransport("smtp");
                    transport.connect("smtp.gmail.com", correo, contrasena);
                    transport.sendMessage(email, email.getAllRecipients());
                    transport.close();
                    System.out.println("Se ha enviado el email correctamente.");
                } catch (NoSuchProviderException e) {
                    throw new RuntimeException(e);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    System.out.println("Error al enviar el email correctamente.");
                }
            }).start();
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


