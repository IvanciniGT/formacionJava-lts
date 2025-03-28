package com.curso.dni.impl;

import com.curso.dni.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DNIUtilsImplTest {

    private static final DNIUtilsImpl dniUtils = new DNIUtilsImpl();

    @Test
    @DisplayName("Crear un DNI desde un Número válido")
    void crearDniDesdeUnNumeroValido() {
        // Dado
        int numeroValido = 230000;
        char letraCorrecta = 'T';
        // Cuando
        DNI respuestaDevuelta = dniUtils.of(numeroValido);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertTrue(respuestaDevuelta.isValid());
        assertInstanceOf(DNIValido.class, respuestaDevuelta);
        assertEquals(numeroValido, ((DNIValido)respuestaDevuelta).getNumero());
        assertEquals(letraCorrecta, ((DNIValido)respuestaDevuelta).getLetra());
    }
    @ParameterizedTest
    @ValueSource(ints = {-2000,200000000})
    @DisplayName("Crear un DNI desde un Número inválido")
    void crearDniDesdeUnNumeroInvalido(int numeroValido) {
        // Cuando
        DNI respuestaDevuelta = dniUtils.of(numeroValido);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(DNIInvalido.class, respuestaDevuelta);
        assertEquals(Integer.toString(numeroValido), ((DNIInvalido)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.NUMERO_FUERA_DE_RANGO, ((DNIInvalido)respuestaDevuelta).getError());
    }


    @Test
    @DisplayName("Crear un DNI desde un texto válido")
    void crearDniDesdeUnTextoValido() {
        // Dado
        int numeroValido = 2300000;
        char letraCorrecta = 'T';
        String textoValido= ""+numeroValido+letraCorrecta;
        // Cuando
        DNI respuestaDevuelta = dniUtils.of(textoValido);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertTrue(respuestaDevuelta.isValid());
        assertInstanceOf(DNIValido.class, respuestaDevuelta);
        assertEquals(numeroValido, ((DNIValido)respuestaDevuelta).getNumero());
        assertEquals(letraCorrecta, ((DNIValido)respuestaDevuelta).getLetra());
    }

    @Test
    @DisplayName("Crear un DNI con un separador inválido")
    void crearDniConSeparadorInvalido() {
        // Dado
        String textoConSeparador = "2300000$T";
        // Cuando
        DNI respuestaDevuelta = dniUtils.of(textoConSeparador);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(DNIInvalido.class, respuestaDevuelta);
        assertEquals(textoConSeparador, ((DNIInvalido)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.FORMATO_INCORRECTO, ((DNIInvalido)respuestaDevuelta).getError());
    }
    @Test
    @DisplayName("Crear un DNI sin letra")
    void crearDniSinLetra() {
        // Dado
        String textoSinLetra = "2300000";
        // Cuando
        DNI respuestaDevuelta = dniUtils.of(textoSinLetra);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(DNIInvalido.class, respuestaDevuelta);
        assertEquals(textoSinLetra, ((DNIInvalido)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.LETRA_INCORRECTA, ((DNIInvalido)respuestaDevuelta).getError());
    }

    @Test
    @DisplayName("Crear un con número incorrecto")
    void crearDniConNumeroIncorrecto() {
        // Dado
        String textoConNumeroIncorrecto = "FEDERIC-O";
        // Cuando
        DNI respuestaDevuelta = dniUtils.of(textoConNumeroIncorrecto);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(DNIInvalido.class, respuestaDevuelta);
        assertEquals(textoConNumeroIncorrecto, ((DNIInvalido)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.NUMERO_INCORRECTO, ((DNIInvalido)respuestaDevuelta).getError());
    }

    @Test
    @DisplayName("Crear muchos DNIs Aleatorios")
    void generarDNIsAleatorios(){
        int cuantos = 50;
        List<DNIValido> dnisGenerados = dniUtils.random(cuantos);
        assertNotNull(dnisGenerados);
        assertEquals(cuantos, dnisGenerados.size());
        dnisGenerados.forEach(dni -> {
            assertNotNull(dni);
            assertTrue(dni.isValid());
            //System.out.println(dni);
        });
    }

    @Test
    @DisplayName("Formatear un DNI")
    void formatearDNI(){
        // Dado
        DNIValido dni = new DNIValido(2300000, 'T');
        DNIFormatSpec formato = DNIFormatSpec.builder()
                                            .mayuscula(false)
                                            .separadorDigitos(true)
                                            .separadorLetra('-')
                                            .cerosALaIzquierda(true)
                                            .anchoMinimo(15)
                                            .build();
        // Cuando
        String dniFormateado = dniUtils.format(dni, formato);
        // Entonces
        assertEquals("02.300.000-t   ", dniFormateado);
    }
}

