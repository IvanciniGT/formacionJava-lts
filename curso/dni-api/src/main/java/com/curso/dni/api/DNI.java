package com.curso.dni.api;

import lombok.Value;

@Value
public class DNI extends Respuesta {
    int numero;
    char letra;
}
