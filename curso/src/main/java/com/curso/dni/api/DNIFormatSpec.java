package com.curso.dni.api;

import lombok.Builder;
import lombok.Value;

@Value // Nos regala: equals, hashCode, toString, getters
       // Nos añade en automático un constructor con todos los argumentos
        // Y también "private final" a los atributos
@Builder
public class DNIFormatSpec {
    @Builder.Default
    boolean mayuscula= true;
    @Builder.Default
    boolean separadorDigitos = false;
    @Builder.Default
    Character separadorLetra = null;
    @Builder.Default
    boolean cerosALaIzquierda = false;
    @Builder.Default
    boolean espaciosALaDerecha= false;
}
