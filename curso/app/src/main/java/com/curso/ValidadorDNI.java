package com.curso;

import com.curso.dni.api.DNIUtils; // Ésta está guay. Apunta a una API
import com.curso.dni.api.DNI; // Ésta también está guay. Apunta a una API

import java.util.ServiceLoader;

//import com.curso.dni.impl.DNIUtilsImpl; // Pero esta linea es una CAGADA !!!!
// Nos estamos pasando por las narices el principio de inversión de dependencias

public class ValidadorDNI {

    public static void main(String[] args){

        System.out.println("Validador de DNIs®");
        if(args.length !=1){
            System.out.println("Uso: java -jar validador-dni.jar dni1");
            System.exit(1);
        }

        String dni = args[0];
        // Validar el DNI e indicar si el dni es válido por consola.
        Iterable<DNIUtils> implementaciones = ServiceLoader.load(DNIUtils.class);
        if(!implementaciones.iterator().hasNext()){
            System.out.println("No se ha encontrado ninguna implementación de DNIUtils, y no puedo continuar");
            System.exit(1);
        }
        DNIUtils dniUtils = implementaciones.iterator().next();
        DNI respuesta= dniUtils.of(dni);
        respuesta.ifValid(dniValido -> System.out.println("DNI válido: " + dniValido))
                 .ifError(error -> System.out.println("Error: " + error));
    }

}
