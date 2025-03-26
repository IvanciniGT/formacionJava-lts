package com.curso.dni.impl;

import com.curso.dni.api.*;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DNIUtilsImpl implements DNIUtils {

    public static final int NUMERO_DNI_MAXIMO = 99999999;
    public static final int NUMERO_DNI_MINIMO = 1;
    public static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static final String PARTE_NUMERICA = "^((\\d{1,8})|(\\d{1,2}(\\.\\d{3}){2})|(\\d{1,3}\\.\\d{3}))";
    public static final String LETRA_FINAL="[a-zA-Z]$";
    public static final String SEPARADOR = "[0-9 -].$";
    public static final String NO_VACIO = "^.+$";

    public static final Map<String, TipoErrorDNI> VALIDACIONES = Map.of(
        NO_VACIO, TipoErrorDNI.DNI_VACIO,
        PARTE_NUMERICA, TipoErrorDNI.NUMERO_INCORRECTO,
        SEPARADOR, TipoErrorDNI.FORMATO_INCORRECTO,
        LETRA_FINAL, TipoErrorDNI.LETRA_INCORRECTA
    );

    @Override
    public Respuesta of(@NonNull String dni) {
        return VALIDACIONES.entrySet().stream() // Para cada validación (PATRON + ERROR)
                .map(
                        validation -> dni.matches(validation.getKey()) ? null : validation.getValue()
                ) // Devuelve el error si no se cumple el patrón ... ACABO CON UNA ESPECIE DE LISTA (Stream) que contiene errores o nulos
                .filter(Objects::nonNull) // Pues me quedo con los errores
                .findFirst() // Tomo el primero... si existe (OPTIONAL<TipoErrorDNI>)
                .map(error -> ((Respuesta) new ErrorDNI(error, dni)) ) // Aplica la transformación SOLO SI el optional no está vacío. (Optional<Respuesta>)
                .orElseGet(() -> { // Si no hay respuesta, devuelve lo que devuelva esta función: Otra Respuesta (DNI)
                    String dniLimpio = dni.replaceAll("[. -]", "");
                    return of(
                            Integer.parseInt(dniLimpio.substring(0, dniLimpio.length() - 1)),
                            dniLimpio.charAt(dniLimpio.length() - 1)
                    );
                }); // Tengo una respuesta
    }


        /*// Comprobar que el DNI no está vacío
        if(!dni.matches(NO_VACIO)) {
            return new ErrorDNI(TipoErrorDNI.DNI_VACIO, dni);
        }
        // Probar los patrones de uno en uno
        if(!dni.matches(PARTE_NUMERICA )){
            // Si el DNI tiene un patrón reconocible, separar la letra del número y llamar a la de abajo
            return new ErrorDNI(TipoErrorDNI.NUMERO_INCORRECTO, dni);
        }
        if(!dni.matches(SEPARADOR)) {
            return new ErrorDNI(TipoErrorDNI.FORMATO_INCORRECTO, dni);
        }
        if(!dni.matches(LETRA_FINAL)){
            return new ErrorDNI(TipoErrorDNI.LETRA_INCORRECTA, dni);
        }*/







    @Override
    public Respuesta of(int numero, char letra) {
        // convertir la letra a mayúsculas
        letra = Character.toUpperCase(letra);
        // Llamar a la de abajo
        Respuesta respuesta = of(numero);
        // Si el dni se procesa bien y su letra es la correcta, devuelvo el DNI
        if(respuesta instanceof DNI dni && dni.getLetra() == letra){
            return dni;
        } else {
            return new ErrorDNI(
                    // Si ha habido un problema con la función anterior, devuelvo ese problema
                    // Si no, devuelvo que la letra es incorrecta
                    !respuesta.isValid() ? ((ErrorDNI) respuesta).getProblema() : TipoErrorDNI.LETRA_INCORRECTA,
                    "" + numero + letra
            );
        }
    }

    @Override
    public Respuesta of(int numero) {
        // Si está fuera de rango, doy un error
        if(numero < NUMERO_DNI_MINIMO || numero > NUMERO_DNI_MAXIMO){
            return new ErrorDNI(TipoErrorDNI.NUMERO_FUERA_DE_RANGO, String.valueOf(numero));
        } else {
            return new DNI(numero, calcularLetra(numero));
        }
    }

    private static char calcularLetra(int numero){
        return LETRAS_DNI.charAt(numero % LETRAS_DNI.length());
    }










    @Override
    public String format(@NonNull DNI dni) {
        return "";
    }

    @Override
    public String format(@NonNull DNI dni, @NonNull DNIFormatSpec spec) {
        return "";
    }










    @Override
    public List<DNI> extract(@NonNull String texto) {
        return List.of();
    }













    @Override
    public DNI random() {
        return null;
    }

    @Override
    public List<DNI> random(int cantidad) {
        return List.of();
    }
}
