package com.curso.dni.api;

import lombok.Value;

@Value
public class DNIValido extends DNI {
    int numero;
    char letra;
}
