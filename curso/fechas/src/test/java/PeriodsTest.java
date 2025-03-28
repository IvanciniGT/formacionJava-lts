import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PeriodsTest {

    @Test
    void tiempoRelativoConRespectoAAhoraTest() {
        ZonedDateTime ahora = ZonedDateTime.now();
        String resultado = Periods.tiempoRelativoConRespectoAAhora(ahora);
        System.out.println("Resultado: " + resultado);
        assertEquals("Ahora mismo", resultado);

        ahora=ahora.plusSeconds(3);
        ahora=ahora.plusMinutes(1);
        resultado = Periods.tiempoRelativoConRespectoAAhora(ahora);
        System.out.println("Resultado: " + resultado);
        assertEquals("Falta 1 minuto", resultado);

        ahora=ahora.plusMinutes(5);
        resultado = Periods.tiempoRelativoConRespectoAAhora(ahora);
        System.out.println("Resultado: " + resultado);
        assertEquals("Faltan 6 minutos", resultado);

        ahora=ZonedDateTime.now().minusDays(5);
        ahora=ahora.minusSeconds(40);
        resultado = Periods.tiempoRelativoConRespectoAAhora(ahora);
        System.out.println("Resultado: " + resultado);
        assertEquals("Hace 5 días", resultado);
    }


    @Test
    void tiempoRelativoConRespectoAAhoraTest2() {
        ZonedDateTime ahora = ZonedDateTime.now();
        ahora=ahora.plusSeconds(2);
        String resultado = Periods.tiempoRelativoConRespectoAAhora(ahora);
        System.out.println("Resultado: " + resultado);
    }



}