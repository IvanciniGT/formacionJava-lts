package com.curso.dni.api;

import lombok.NonNull;

import java.util.function.Consumer;

public abstract sealed class Respuesta permits DNI, ErrorDNI {
    // Java 15 ^^^ sealed classes

    public final Respuesta ifValid(@NonNull Consumer<DNI> funcionAEjecutarSiElDNIEsValido){
        if(this instanceof DNI dni){ // Java 15 Pattern Matching for instanceof
            funcionAEjecutarSiElDNIEsValido.accept(dni);
        }
        return this;
    }

    public final Respuesta ifError(@NonNull Consumer<ErrorDNI> funcionAEjecutarSiElDNIEsInvalido){
        if(this instanceof ErrorDNI error){
            funcionAEjecutarSiElDNIEsInvalido.accept(error);
        }
        return this;
    }

    public final boolean isValid(){
        return this instanceof DNI;
    }
}
/*
Respuesta r = DNIUtils.of("12345678Z");
r.ifValid(dni -> System.out.println("DNI válido: " + dni))
 .ifValid(dni -> System.out.println("DNI válido: " + dni))
 .ifError(error -> System.out.println("Error: " + error));
 */