package Prueba_errores;

import Clases.Facturas;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
public class FacturasTest {


    @Test
    public void testCalcularTotalConDescuento() {
        BigDecimal subtotal = new BigDecimal("100.00");
        BigDecimal descuento = new BigDecimal("10.00"); // 10%

        Facturas factura = new Facturas(1, "2023-10-10 10:00:00", BigDecimal.ZERO, "12345678A", descuento, subtotal, "Pagado", "2023-10-10 12:00:00", "Tarjeta");
        BigDecimal totalConDescuento = factura.calcularTotalConDescuento(subtotal, descuento);

        assertEquals(new BigDecimal("90.00"), totalConDescuento);
    }


}