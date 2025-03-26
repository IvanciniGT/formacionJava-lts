package com.curso.dni.impl;

import com.curso.dni.api.*;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class DNIUtilsImpl implements DNIUtils {

    public static final int NUMERO_DNI_MAXIMO = 99999999;
    public static final int NUMERO_DNI_MINIMO = 1;
    public static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static final String PARTE_NUMERICA = "((\\d{1,8})|(\\d{1,2}(\\.\\d{3}){2})|(\\d{1,3}\\.\\d{3})).+";
    public static final String LETRA_FINAL=".+[a-zA-Z]";
    public static final String SEPARADOR = ".+[0-9 -].";
    public static final String NO_VACIO =".+";

    public static final Map<String, TipoErrorDNI> VALIDACIONES = Map.of( // Java 9
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
                .findFirst()
                .map(tipoError -> ((Respuesta) new ErrorDNI(tipoError, dni)) ) // Aplica la transformación SOLO SI el optional no está vacío. (Optional<Respuesta>)
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
        }

        String dniLimpio = dni.replaceAll("[. -]", "");
        return new DNI (
            Integer.parseInt(dniLimpio.substring(0, dniLimpio.length() - 1)),
            dniLimpio.charAt(dniLimpio.length() - 1)
        );

        */







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
        return format(dni, DNIFormatSpec.builder().build());
    }

    @Override
    public String format(@NonNull DNI dni, @NonNull DNIFormatSpec spec) {
        // Ceros a la izquierda
        String parteNumerica = ""+dni.getNumero();
        if(spec.isCerosALaIzquierda()){
            parteNumerica = ("00000000"+parteNumerica).substring(parteNumerica.length());
        }
        // Separador dígitos miles y millones
        StringBuilder parteNumericaFormateada = new StringBuilder();
        for(int i = parteNumerica.length()-1; i >= 0; i-=1){
            parteNumericaFormateada.append(parteNumerica.charAt(i));
            if((parteNumerica.length() -i - 1)%3 == 2 && i > 0)
                parteNumericaFormateada.append('.');
        }

        // mayúscula
        // separador de letra
        String separador = ""+(spec.getSeparadorLetra()!= null? spec.getSeparadorLetra() : "");
        char letra = spec.isMayuscula()?Character.toUpperCase(dni.getLetra()):Character.toLowerCase(dni.getLetra());
        String parteLetra = separador + letra;

        // Espacios a la derecha
        String formateado = parteNumericaFormateada.reverse().toString() + parteLetra;
        if(spec.getAnchoMinimo() > 0 && formateado.length() < spec.getAnchoMinimo()){
            return ((formateado)+("                        ")).substring(0,spec.getAnchoMinimo());
        }

        return formateado;
    }





    @Override
    public List<DNI> extract(@NonNull String texto) {
        return List.of();
    }






    @Override
    public DNI random() {
        /*
        int numero = (int)(Math.random()*(NUMERO_DNI_MAXIMO-NUMERO_DNI_MINIMO)+NUMERO_DNI_MINIMO);
        return (DNI) of(numero);
        */
        return random(1).get(0);
    }

    @Override
    public List<DNI> random(int cantidad) {
        /*
        List<DNI> dnis = new ArrayList<>();
        for(int i = 0; i < cantidad; i++){
            dnis.add(random());
        }
        return dnis;
        */
        /*
        return IntStream.range(0,cantidad)              // Genero una secuencia con "cantidad" números
                        .mapToObj(numero-> random()) // Y en lugar de los números pongo un DNI aleatorio
                        .toList();                      // Y lo convierto en una lista
                        */
        return ThreadLocalRandom.current()
                .ints(cantidad, NUMERO_DNI_MINIMO, NUMERO_DNI_MAXIMO)
                .mapToObj(numeroAleatorio -> (DNI)this.of(numeroAleatorio))
                .toList();
    }

    // IntStream.range
}
