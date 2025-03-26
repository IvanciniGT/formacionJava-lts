
# Proyecto.

Librería para trabajar con .... DNIs (España) (Dejamos nie fuera)

- Validar un DNI
- Formatear un DNI
- Extraer los DNIs de un texto

## Queremos 2 proyectos:

- API: Lo que define cómo vamos a comunicarnos con la librería.
  - interfaces, enumeraciones, pojos...
- Implementación de la librería

```java
package com.curso.dni.api;

public final class DNI {  // INMUTABLE OBJETO DE TRANSPORTE DE DATOS. POJO

    private final char letra;
    private final int numero;

    public DNI(char letra, int numero) {
        this.letra = letra;
        this.numero = numero;
    }

    public char getLetra() {
        return letra;
    }

    public int getNumero() {
        return numero;
    }

}
// Esto sería pre java 15
// Desde Java 15:
public record DNI(char letra, int numero) {}

```
12345678A

23000001 | 23
         +-------
       1   1000000 (me la pela)
       ^
       Este es el que vale = R

- Calcular la letra que le toca a un número
- Formatear DNI

## Cómo veis eso del record?

Esta bien... Pero llega tarde! Llega tarde porque ya tenemos Lombok.

```java
package com.curso.dni.api;

import lombok.Value;

@Value
public class DNI {
    char letra;
    int numero;
}
```


---
OPCION 1:
- validar(DNI dniAValidar) : boolean
    Qué me cuenta? 
        Para saber si un DNI es válido
        Y si no? qué le pasa al DNI? NPI!

    Qué le tengo que pasar? un Objeto de clase DNI???? y eso???
    Y pa' crear ese objeto necesito? char, int
    Quien tiene eso?
        La gente tendrá un String con el DNI
        Si acaso tendrán un char y un int (de una BBDD que hayan guardao por separado)
        Si son más listos, incluso habrán guardado en una BBDD solamente el número

- validar(String letra, int numero) : Optional<DNI>
    Qué devuelve?  un DNI
    Siempre? y si me pasan como número: -192848291837482734
    null??
    Pero... y si no viene nada en la caja, que significa?
    Que no es correcto... por qué? NPI

- DNI.of(String dni)
- DNI.of(int numero, char letra)
- DNI.of(int numero)                : DNI

        throws IllegalPatternException,
            NumberOutOfRangeException,
            LetterOutOfRangeException,

            InvalidDNIException
                errorCode: InvalidDNIException.INVALID_LETTER
                errorCode: InvalidDNIException.INVALID_NUMBER
                errorCode: InvalidDNIException.INVALID_PATTERN

            Tengo la información que quiero? SI
            Pero esto es una guarrada!!!!
            NUNCA JAMAS USO UNA EXCEPCION PARA CONTROL DE LOGICA DE NEGOCIO
            LAs excepciones son para controlar errores inesperados: Unexpected

            Ve a la BBDD y haz una query
            Se a priori si voy a poder ir a la BBDD? NO
            Quizás se ha caído... Bueno... pues INTENTALO (try)

            Una Exception es muy cara de generar (a nivel computacional)
            Lo primero que hace la JVM es hacer un volcado de la pila de llamadas.

  - La letra no encaja con el número
  - Has puesto mal los signos de puntuación
  - Has metido un número fuera de rango
  - El separador de la letra no es correcto

"12345678T" ME LO TRAGO
"12345678-T" ME LO TRAGO
"12345678 T" ME LO TRAGO

"12345678#T" NO CUELA
"12345678TT" NO CUELA
"12345678,T" NO CUELA
"12345678:T" NO CUELA
"12345678@T" NO CUELA

"12.345.678-T"                      ME LO TRAGO
"1.2....3.45.678-T"                 NO ME LO TRAGO
"-12.345.678-T"                     ME LO TRAGO
"FEDERICO"                          NO ME LO TRAGO  Formato incorrecto








---




DNI en BBDD. Cuánto ocupa?
- Letras: 1
- Dígitos: 8
- Total: 9 caracteres.
- Cuánto ocupa 1 carácter en disco? Depende del JUEGO DE CARACTERES
    - ASCII: 1 byte
    - UTF-8: 1-4 byte
    - UTF-16: 2-4 bytes
    - UTF-32: 4 bytes
    - ISO-8859-1: 1 byte

En una BBDD lo normal es UTF-8... Aunque fuera ASCII, tendríamos que guardar 9 bytes.

Cuánto ocupa el entero 99.999.999?
1 byte? 256
2 bytes? 65.536
4 bytes? 4.294.967.296

Es decir, si guardo el número tengo un ahorro de 5/9 % = 55,55% de espacio.
No está mal!


Cuándo deberíamos generar un objeto DNI?
Cuando tengamos un DNI que sea BUENO.
Esto es un DNI? 10294829101898383 TTTKKK

DNI.of(String dni) -> Respuesta

DNI extends Respuesta
    numero
    letra
ErrorDeValidación extends Respuesta
    problema
    source

```java
Respuesta respuesta = DNI.of("123456789T");
if(respuesta instanceOf DNI dni) { // Pattern Matching de InstanceOf
    // Hacer cosas con el DNI
    respuesta.getDNI();
} else {
    // Hacer cosas con el error
    respuesta.getProblema();
}

// JAVA ESTA TIRANDO POR AQUI      VVVVVVV
DNI.of("123456789T")
   .ifValid( dni   -> {Haz algo});
   .else(    error -> {Haz otra cosa});
// Programación funcional
```

.format(DNI dni)
.format(DNI dni, DNIFormatSpec spec) -> String


DNIFormatSpec
 - Letra en mayuscula o minúscula
 - Separadores de dígitos
 - Separador de letra
 - Complete con ceritos a la izquierda
 - Complete con espacios a la derecha

```java
public class DNIFormatSpec {
    private final boolean mayuscula;
    private final boolean separadorDigitos;
    private final Character separadorLetra;
    private boolean cerosALaIzquierda;
    private boolean espaciosALaDerecha;
    @Getter 

    public DNIFormatSpec(boolean mayuscula, 
                        boolean separadorDigitos, 
                        Character separadorLetra, 
                        boolean cerosALaIzquierda, 
                        boolean espaciosALaDerecha) {
        this.mayuscula = mayuscula;
        this.separadorDigitos = separadorDigitos;
        this.separadorLetra = separadorLetra;
        this.cerosALaIzquierda = cerosALaIzquierda;
        this.espaciosALaDerecha = espaciosALaDerecha;
    }
}

//En lugar de todo ese rollo, podría montar un record
public record DNIFormatSpec(boolean mayuscula, 
                            boolean separadorDigitos, 
                            Character separadorLetra, 
                            boolean cerosALaIzquierda, 
                            boolean espaciosALaDerecha) {}

// ^^^ ESO SERIA UNA KK
// Nada cómodo de usar.
// Que me puede interesar aquí para crear fácilmente objetos de tipo DNIFormatSpec? UN PATRON BUILDER!!!!

var spec = DNIFormatSpec.builder()
                        .mayuscula(true)
                        .espaciosALaDerecha(true)
                        .build();
@Builder // LOMBOK
@Value
public class DNIFormatSpec {
    boolean mayuscula;
    boolean separadorDigitos;
    Character separadorLetra;
    boolean cerosALaIzquierda;
    boolean espaciosALaDerecha;
}
```

DNI (12345678, A)

    345678A
    345678-A
    345678 A
    345678 a
    00345678A
    "345678A  "
    "345.678-A"
    "345.678 A"
    "00.345.678 a"

Texto -> Todos los DNIs que encuentre
.buscarEnTexto  (String texto) -> List<DNI>
.extract        (String texto) -> List<DNI>

.random() -> DNI
.random(int cantidad) -> List<DNI>

---

# Donde defino todas esas funciones?

.of(String dni) -> Respuesta
.of(int numero, char letra) -> Respuesta
.of(int numero) -> Respuesta
.format(DNI dni) -> String
.format(DNI dni, DNIFormatSpec spec) -> String
.extract(String texto) -> List<DNI>
.random() -> DNI
.random(int cantidad) -> List<DNI>

DNIManager
DNIUtils
DNIHelper
DNI


^^^ Interfaz DNIUtils

```java
package com.curso.dni.api;

public interface DNIUtils {
    Respuesta of(String dni);
    Respuesta of(int numero, char letra);
    Respuesta of(int numero);
    String format(DNI dni);
    String format(DNI dni, DNIFormatSpec spec);
    List<DNI> extract(String texto);
    DNI random();
    List<DNI> random(int cantidad);
}

public class DNIUtilsImpl implements DNIUtils{
    // y aquí la implementación completa!!!
}
```

Ahora me pongo a trabajar en mi proyecto... y necesito usar esa librería:

```java
import com.curso.dni.api.DNIUtils;          // Interfaz
import com.curso.dni.api.Respuesta;         // Interfaz
import com.curso.dni.api.DNI;               // Record
import com.curso.dni.api.DNIUtilsImpl;      // Clase con muchooo código
    // ^^^ ESTA LINEA ES LA MUERTA DEL PROYECTO
    // Añadir una dependencia a una implementación 
    // Al hacer esto nos acabamos de CAGAR en el principio de inversión de dependencias

public class ClaseDeMiProyecto {
    public void miFuncioncitaDeMarras(){
        String dni="Algo";
        // Y quiero validar el DNI
        DNIUtils utils = new DNIUtilsImpl();
        Respuesta respuesta = utils.of(dni);
        if(respuesta instanceOf DNI dni) {
            // Hacer cosas con el DNI
            respuesta.getDNI();
        } else {
            // Hacer cosas con el error
            respuesta.getProblema();
        }
    }
}
```

## SOLID

# D de SOLID: Principio de Inversión de Dependencias. Tiene más de 25 años.

Una clase de alto nivel NUNCA debe depender de otras clases de bajo nivel...
Sino que en su lugar AMBAS DEBEN DEPENDER DE ABSTRACCIONES (interfaces)

# Inyección de Dependencias

Un patrón de diseño, por el cuál las clases no crean instancias de los objetos que necesitan, sino que le son suministradas.

# Patrones Factory

---

No es que JAVA haya evolucionado...
Y esto no va de aprender las novedades de JAVA 17.
Si alguien está aquí con esa intención, se ha equivocado de curso.
Para eso que vaya a cualquier tutorial, blog o video de youtube... y apañao!

El mundo cambia. Los problemas cambian. Las soluciones cambian.

Las arquitecturas cambian.                  MONOLITO -> MICROSERVICIOS
                                                        DESACOPLADAS
Las metodologías de gestión cambian.        CASCADA -> AGILE 
Las metodologías de desarrollo cambian.     ESCRIBIENDO CODIGO -> TDD

Y para ello, necesito herramientas y lenguajes que me permitan adaptarme a esos cambios.

    Las herramientas cambian.
    Los lenguajes cambian.

Y todas esas cosas cambian a la vez, entre todas adaptándose a las nuevas realidades y necesidades.


---

# Animalitos Fermín

Fermín quiera vender comida de animales.
Le montamos una tienda online... una web para vender la comida? NO
Eso se lo habríamos montado hace 15 años... o 20.

No quiero una app WEB (.war, .ear)
Quiero un sistema completo...

FRONTAL                                                         SERVIDOR
---------                                                       ---------              
web                                                             json <--- APP <---- BD
    json -> html (JS)
app Android
app iOS
Asistente de voz (Alexa, Google Home, OK Google)
IVR (Interactive Voice Response)
API REST

---

# MAVEN

Maven es una herramienta de automatización de tareas típicas en proyectos de desarrollo, especialmente en proyectos Java.

Nos permite automatizar cosas como:
- Compilación
- Empaquetado
- Ejecución de tests
- Generar informes de tests
- Generar documentación
- Empaquetar código dentro de una imagen de Docker
- Enviar el código a un sonar

Además, nos ayuda con la gestión de dependencias.

La estructura típica de un proyecto maven es:
 
 proyecto/
    src/
        main/
            java/
            resources/
        test/
            java/
            resources/
    target/
        classes/
        test-classes/
        surefire-reports/
    pom.xml

    En el pom.xml definimos:
        - Coordenadas del proyecto: groupId, artifactId, version
        - Metadatos del proyecto: nombre, descripción, url, licencia, desarrolladores
        - Las dependencias que necesitamos
        - Los plugins que vamos a usar
        - Propiedades de configuración para los plugins
  
Todo en maven se basa en plugins. Maven no sabe más que ejecutar plugins.

Cada tarea de maven (GOAL) es ejecutada por un plugin.

Goals estándar de maven:
- compile       -> Compila los archivos .java de la carpeta src/main/java
                      dejando los .class dentro de la carpeta target/classes
                   Copia lo que tenemos en src/main/resources a target/classes
- test-compile  -> Compila los archivos .java de la carpeta src/test/java
                      dejando los .class dentro de la carpeta target/test-classes
                   Copia lo que tenemos en src/test/resources a target/test-classes
- test          -> Ejecuta los tests de la carpeta target/test-classes
                   Realmente le pide al plugin surefire que ejecute los test.
                   El plugin surefire busca por defecto los test definidos en archivos que empiecen o acaben por "Test", y que hayan sido construidos con JUnit(v4 o v5) dependiendo de la versión de surefire que estemos usando.
                   Además genera un informe de los tests en formato XML, que guarda en target/surefire-reports
- package       -> Empaqueta el proyecto en un .jar o .war... básicamente mete en un .zip lo que hay en target/classes
- install       -> Copia el empaquetado dentro de mi carpeta .m2, de forma que otros proyectos locales puedan usarlo como dependencia.
- clean         -> Borra la carpeta target (como si le a borrar desde el explorador de archivos)


---

# Carpeta .m2

Una carpeta que maven crea en mi carpeta de usuario, donde guarda:
- Un archivo llamado settings.xml que contiene la configuración global de maven:
  - De que repositorios de maven quiero descargar las dependencias
  - Que servidores de sonar tengo configurados
  - Qué proxy tengo configurado
-  todas las dependencias que va descargando.


# Patrones de expresiones regulares en JAVA
 
Usamos la sintaxis de PERL en java para las expresiones regulares.
El trabajo con expresiones regulares en java se basa en el concepto de PATRON.

Qué es un PATRON?

Un patron es una secuencia o alternancia de SUBPATRONES.

Qué es un subpatron?

Una secuencia de caracteres seguida de un modificador de cantidad.

    SECUENCIA DE CARACTERES             LO QUE SIGNIFICA

    "hola"                              Debe contener la palabra "hola"
    "[hola]"                            Debe contener una h, o, l o a
    "[^hola]"                           No debe contener una h, o, l ni a
    "[a-z]"                             Debe contener una letra minúscula en
                                         el rango a-z
    "[A-Z]"                             Debe contener una letra mayúscula en
                                         el rango A-Z
    "[a-zA-ZñÑ]"                        Debe contener una letra en el rango
                                         a-z, A-Z, ñ o Ñ
    "[0-9]"                             Debe contener un dígito en el rango
                                         0-9
    .                                   Cualquier carácter
    \w                                  Cualquier carácter alfanumérico
    \W                                  Cualquier carácter no alfanumérico
    \d                                  Cualquier dígito
    \D                                  Cualquier carácter no dígito

Modificadores de cantidad
    - No poner nada                     La secuencia anterior debe aparecer
                                        una vez                             1
    ?                                   La secuencia anterior puede aparecer
                                        o no                              0-1
    *                                   La secuencia anterior puede no 
                                        aparecer o aparecer varias veces  0-n
    +                                   La secuencia anterior debe aparecer
                                        al menos una vez                  1-n
    {5}                                 La secuencia anterior debe aparecer
                                        exactamente 5 veces               5
    {5,}                                La secuencia anterior debe aparecer
                                        al menos 5 veces                  5-n
    {5,10}                              La secuencia anterior debe aparecer
                                        entre 5 y 10 veces                5-10

Caracteres especiales:
    |                                     o
    ()                                    agrupación
    $                                     fin de línea
    ^                                     principio de línea

# Ejemplo:

Número entre 1 y 19
    [1-19]? NO... trabajamos con caracteres
    Eso significaría un dígito entre 1 y 1 o el 9

    (1[0-9])|([1-9]) -> 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19


345.678A
12.345.678-A
345.678-A
12.345.678 A
345.678 A
12.345.678a
345.678a

√ 12345678A
√ 12345678-A
√ 12345678 A
√ 12345678a
√ 345678a

\d{1,8}

√ 12.345.678A

[0-9]{1,2}(\.[0-9]{3}){2}

√ 345.678a

[0-9]{1,3}\.[0-9]{3}

PARTE_NUMERICA = "^((\\d{1,8})|(\\d{1,2}(\\.\\d{3}){2})|(\\d{1,3}\\.\\d{3}))"
Si esto no se cumple.. Donde está el problema?  PARTE NUMERICA

LETRA FINAL="[a-zA-Z]$"
Si esto no se cumple... Donde está el problema? LETRA FINAL

SEPARADOR = "[0-9 -].$"
Si esto no se cumple... Donde está el problema? SEPARADOR
