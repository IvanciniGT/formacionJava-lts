package com.curso.dni.impl;

import com.curso.dni.api.*;
import lombok.NonNull;
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
        Respuesta respuestaDevuelta = dniUtils.of(numeroValido);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertTrue(respuestaDevuelta.isValid());
        assertInstanceOf(DNI.class, respuestaDevuelta);
        assertEquals(numeroValido, ((DNI)respuestaDevuelta).getNumero());
        assertEquals(letraCorrecta, ((DNI)respuestaDevuelta).getLetra());
    }
    @ParameterizedTest
    @ValueSource(ints = {-2000,200000000})
    @DisplayName("Crear un DNI desde un Número inválido")
    void crearDniDesdeUnNumeroInvalido(int numeroValido) {
        // Cuando
        Respuesta respuestaDevuelta = dniUtils.of(numeroValido);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(ErrorDNI.class, respuestaDevuelta);
        assertEquals(Integer.toString(numeroValido), ((ErrorDNI)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.NUMERO_FUERA_DE_RANGO, ((ErrorDNI)respuestaDevuelta).getProblema());
    }


    @Test
    @DisplayName("Crear un DNI desde un texto válido")
    void crearDniDesdeUnTextoValido() {
        // Dado
        int numeroValido = 2300000;
        char letraCorrecta = 'T';
        String textoValido= ""+numeroValido+letraCorrecta;
        // Cuando
        Respuesta respuestaDevuelta = dniUtils.of(textoValido);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertTrue(respuestaDevuelta.isValid());
        assertInstanceOf(DNI.class, respuestaDevuelta);
        assertEquals(numeroValido, ((DNI)respuestaDevuelta).getNumero());
        assertEquals(letraCorrecta, ((DNI)respuestaDevuelta).getLetra());
    }

    @Test
    @DisplayName("Crear un DNI con un separador inválido")
    void crearDniConSeparadorInvalido() {
        // Dado
        String textoConSeparador = "2300000$T";
        // Cuando
        Respuesta respuestaDevuelta = dniUtils.of(textoConSeparador);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(ErrorDNI.class, respuestaDevuelta);
        assertEquals(textoConSeparador, ((ErrorDNI)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.FORMATO_INCORRECTO, ((ErrorDNI)respuestaDevuelta).getProblema());
    }
    @Test
    @DisplayName("Crear un DNI sin letra")
    void crearDniSinLetra() {
        // Dado
        String textoSinLetra = "2300000";
        // Cuando
        Respuesta respuestaDevuelta = dniUtils.of(textoSinLetra);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(ErrorDNI.class, respuestaDevuelta);
        assertEquals(textoSinLetra, ((ErrorDNI)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.LETRA_INCORRECTA, ((ErrorDNI)respuestaDevuelta).getProblema());
    }

    @Test
    @DisplayName("Crear un con número incorrecto")
    void crearDniConNumeroIncorrecto() {
        // Dado
        String textoConNumeroIncorrecto = "FEDERIC-O";
        // Cuando
        Respuesta respuestaDevuelta = dniUtils.of(textoConNumeroIncorrecto);
        // Entonces
        assertNotNull(respuestaDevuelta);
        assertFalse(respuestaDevuelta.isValid());
        assertInstanceOf(ErrorDNI.class, respuestaDevuelta);
        assertEquals(textoConNumeroIncorrecto, ((ErrorDNI)respuestaDevuelta).getOriginal());
        assertEquals(TipoErrorDNI.NUMERO_INCORRECTO, ((ErrorDNI)respuestaDevuelta).getProblema());
    }

    @Test
    @DisplayName("Crear muchos DNIs Aleatorios")
    void generarDNIsAleatorios(){
        int cuantos = 50;
        List<DNI> dnisGenerados = dniUtils.random(cuantos);
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
        DNI dni = new DNI(2300000, 'T');
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

