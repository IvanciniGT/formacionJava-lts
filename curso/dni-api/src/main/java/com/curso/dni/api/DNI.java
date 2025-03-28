package com.curso.dni.api;

import lombok.NonNull;

import java.util.function.Consumer;

public abstract sealed class DNI permits DNIValido, DNIInvalido {
    // Java 15 ^^^ sealed classes

    public final DNI ifValid(@NonNull Consumer<DNIValido> funcionAEjecutarSiElDNIEsValido){
        if(this instanceof DNIValido dni){ // Java 15 Pattern Matching for instanceof
            funcionAEjecutarSiElDNIEsValido.accept(dni);
        }
        return this;
    }

    public final DNI ifError(@NonNull Consumer<DNIInvalido> funcionAEjecutarSiElDNIEsInvalido){
        if(this instanceof DNIInvalido error){
            funcionAEjecutarSiElDNIEsInvalido.accept(error);
        }
        return this;
    }

    public final boolean isValid(){
        return this instanceof DNIValido;
    }
}
/*
Respuesta r = DNIUtils.of("12345678Z");
r.ifValid(dni -> System.out.println("DNI válido: " + dni))
 .ifValid(dni -> System.out.println("DNI válido: " + dni))
 .ifError(error -> System.out.println("Error: " + error));
 */