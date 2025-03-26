package com.curso.dni.api;

import lombok.NonNull;

import java.util.List;

public interface DNIUtils {

    Respuesta of(@NonNull String dni);
    Respuesta of(int numero, char letra);
    Respuesta of(int numero);
    String format(@NonNull DNI dni);
    String format(@NonNull DNI dni, @NonNull DNIFormatSpec spec);
    List<DNI> extract(@NonNull String texto);
    DNI random();
    List<DNI> random(int cantidad);

}

// Un DNI es una Respuesta? SI
// Un ErrorDNI es una Respuesta? SI
// Por lo tanto, mi función .of puede devolver un DNI? SI
// Y puede devolver un ErrorDNI? SI
// Eso es lo que queríamos? SI
// Estamos implementando la interfaz? NO
// Quién la va a implementar? Qué ser humano va a escribir una clase que implemente la interfaz DNIUtils? NPI
// Realmente qué hemos dicho a nivel de la interfaz que devuelve la función .of()?
// Un DNI o un ErrorDNI? NO
// Lo que hemos dicho es que esa función devuelve una Respuesta
// Quién sea que implemente la interfaz podría
// optar por devolver un objeto llamado FEDERICO, que implemente la interfaz Respuesta? SI
// Queremos que eso ocurra? NO
// Cómo limito eso en JAVA? Hasta Java 15, esto no se podía hacer, dejando la puerta abierta
// a implementaciónes que rompieran con el principio de Liskov
// Desde java 15 podemos hacerlo con sealed classes/interfaces