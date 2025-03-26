package com.curso.dni.api;

import lombok.Value;

@Value
public class ErrorDNI extends Respuesta {
    TipoErrorDNI problema;
    String original;
}
