package com.curso.dni.api;

import lombok.Value;

@Value
public class DNIInvalido extends DNI {
    TipoErrorDNI error;
    String original;
}
