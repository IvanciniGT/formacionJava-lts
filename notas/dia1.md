JAVA LTS (Long Term Support) releases
- Java 8 (LTS) - 2014
- Java 9 - 2017
- Java 11 (LTS) - 2018
- Java 15 - 2020
- Java 17 (LTS) - 2021
~~Java 21 (LTS) - 2023~~

---

# JAVA

Es un lenguaje de programación, que:

## Tipado dinámico vs tipado estático

Java es tipado fuerte o estático. Qué significa eso?

- Lenguaje de tipado estático o fuerte es aquel en el que las VARIABLES TIENEN un tipo de datos asignado.
- Los lenguajes de tipado dinámico son aquellos en los que las variables no tienen un tipo de datos asignado.

En los programas que montamos, manejamos DATOS... y TODO lenguaje de programación tiene TIPOS DE DATOS.

```js
'hola': string
3.14: number
true: boolean
```

```python
'hola': str
3.14: float
True: bool
```

```java
"hola": String
3.14: double
true: boolean
```

Qué es una variable?
- Un espacio de memoria al que accedo mediante una referencia (posición de memoria)
Una variable no es una cajita donde pongo cosas... o al menos no en JAVA... ni en JS, ni en python.

Y el tema es que el concepto de variable cambia de lenguaje a lenguaje.
- En C o Fortran.. o ADA, una variable SI SERIA ESO... una cajita (espacio de memoria) donde pongo cosas.
- En JAVA, PYTHON, JS, una variable es una referencia a un dato persistido en RAM.

Ahora bien.. hay lenguajes de programación en los que a las variables TAMBIEN se les asigna un tipo de datos.

```java
String texto = "hola";
```
Asignamos la variable "texto" al valor "hola"!

Qué hace esa linea de código? 3 cosas:
1. "hola"               Crea un objeto de tipo String con valor "hola" en Memoria RAM.
2. String texto         Crea una variable llamada "texto" que puede apuntar a un objeto de tipo String.
3. =                    Asigna la variable "texto" al objeto de tipo String con valor "hola".


```java
texto = "adios";
```
1. "adios"              Crea un objeto de tipo String con valor "adios" en Memoria RAM.
                        ¿Dónde? En el mismo sitio donde estaba el "hola" o en otro? En otro
                        Y llegados a este punto, tenemos 2 objetos de tipo String en Memoria RAM.
2. texto =              Arrancar el postit de donde estaba pegado... 
                        y moverlo al lado del nuevo String ("adios") en Memoria RAM.
                        Al hacerlo, el objeto de tipo String "hola" queda huérfano de variable...
                        Se convierte en BASURA (garbage) y puede ocurrir (o no... npi) que el recolector de basura en algún momento lo elimine de la memoria (Tenemos un comportamiento no determinístico -> No elegiríamos JAVA como lenguaje de programación para sistemas en tiempo real).
                        Esto funcionaría porque la variable ha sido definida como variable de TIPO String...
                        Y puedo hacer que apunte a cualquier objeto de tipo String (o incluso un subtipo de String).

```js
var texto = "hola";
texto = 33;
texto = false;
```

En js, las variables no tienen un tipo asignado (los datos SI), y por ende pueden apuntar a datos de cualquier tipo.

```java
var texto = "hola";   // En java 10 se introdujo la palabra reservada "var" para definir variables de forma más sencilla.
//texto = 33;           Pero esto no podría hacerse.
//texto = false;
```

La palabra var de java 10 no es igual que la palabra var de js.
JAVA SIGUE SIENDO UN LENGUAJE DE TIPO ESTÁTICO.
Con var lo que evitamos es declarar el tipo de la variable, ya que el tipo se inferirá del primer valor que se le asigne.

## Paradigmas de programación

Son la forma en la que podemos usar un lenguaje para expresarnos. No es algo exclusivo de los lenguajes de programación.
- Imperativo                Es cuando le damos a la computadora instrucciones que deben procesarse
                            secuencialmente. En algunos escenarios nos interesa romper la secuencialidad... y usamos las típicas estructuras de control (if, for, while, switch, etc).
- Procedural                Es cuando el lenguaje me permite agrupar una secuencia de código imperativo en un
                            bloque de código al que le pongo un nombre (procedimiento o función, método, subrutina, etc). Y el lenguaje me permite posteriormente invocar ese bloque de código por su nombre.
                            Para qué?
                            - Reutilización de código
                            - Mejorar la legibilidad del código (mejorar su estructura) para facilitar su mantenimiento.
                            - Cuando usamos programación funcional, porque necesitamos tener una función que pasar a otra como argumento. POR EL ARTICULO 33
- Orientado a objetos       TODO lenguaje de programación me permite trabajar con datos de distintos TIPOS.
                            Los lenguajes vienen con una serie de tipos de datos PREDEFINIDOS.
                            Hay lenguajes que me permiten definir mis propios tipos de datos... y usarlos: CLASE.
                                        Características                Funciones
                            String      secuencia de caracteres        length(), toUpperCase(), toLowerCase()

                            RUINA !!!
                            java.util.Date
                            java.sql.Date
                            java.util.Calendar
                            java.util.GregorianCalendar
                            
                            LocalDate = GUAY!!! Java 1.8 .... java.time.LocalDate
                                                                        LocalDateTime
                                                                        ZoneDateTime
                                                                        Instant
                                                                        Duration
                                        Es decir... la antigua librería de JAVA JODA-TIME

                                        DIA  int                        caesEnAñoBisiesto()
                                        MES  int                        suma4Días()
                                        AÑO  int
                            Usuario     nombre String                   saludar()
                                        edad int                        felicitarCumpleaños()
                                        dni String
- Declarativo
    La mayor parte del código que escribimos es IMPERATIVO.... y ODIAMOS el lenguaje IMPERATIVO... cada día más.
    Por qué? PORQUE ES UNA CASTAÑA DEL 15.

    > Felipe, SI (IF) hay algo que no sea una silla debajo de la ventana:
        > QUÍTALO !!!                                 IMPERATIVO
    > Felipe, IF not SILLA in ventana:
        > Felipe, if silla == null (no hay sillas) 
            > Felipe: GOTO IKEA !!! comprar silla!
        > Felipe, pon una silla debajo de la ventana!     IMPERATIVO

    El problema del lenguaje imperativo es que hace olvidar mi objetivo: Que haya una silla debajo de la ventana
    pero... me he centrado en explicarle a Felipe lo que debe hacer... no lo que quiero que ocurra.

    > Felipe: Debajo de la ventana ha de haber una silla.  DECLARATIVO

    Al usar lenguaje declarativo, me centro en lo que quiero conseguir, dejando la responsabilidad de cómo hacerlo al lenguaje de programación. DEFINO UN ESTADO A CONSEGUIR!

    Java soporta lenguaje DECLARATIVO? SI... desde java 1.5: ANOTACIONES

- Funcional
    Soporta JAVA paradigma funcional? SI... desde java 1.8 (aleluya!!!!)
                               Cuando el lenguaje de programación me permite que una variable apunte a una función y posteriormente ejecutar la función desde la variable, decimos que el lenguaje soporta el paradigma funcional.
                                Lo importante no es lo que es la programación funcional.
                                Sino lo que puedo llegar a hacer una vez que el lenguaje soporta eso:
                                - Definir una función que tome otra función como argumento.
                                - Devolver una función como resultado de otra función (closure).
---

# Novedades de Java 1.8
- Nuevo paquete java.time.
- Nuevo paquete java.util.function.
- Nuevo operador :: (operador de referencia).
- Nuevo operador -> (operador lambda).
- Se añade en java el paquete java.util.stream, para implementar en Java el modelo de programación MAP-REDUCE:
    java.util.stream.Stream<T>
    Qué es un Stream<T>? Similar a un Set<T> o List<T>... pero en lugar de tener dentro funciones típicas de colecciones(add, delete, get, etc), tiene funciones de programación funcional (map, filter, reduce, etc).
    Podemos transformar cualquier Collection de java en un Stream, mediante el método .stream().
    Podemos transformar cualquier Stream de java en un Collection, mediante el método .collect(RECOLETOR AL TIPO DE COLLECTION QUE QUERAMOS OBTENER).
    Esto es ultra-potente!
- La posibilidad de definir funciones con código "default" en interfaces.
- Posibilidad de definir funciones static con código en interfaces (siempre y cuando sean public = CAGADA!!)
- Optional<T> (java.util.Optional).
  Es un objeto similar a una caja. Puede estar lleno o vacío. Trabaja con genéricos. El tipo T indica lo que puede ser el contenido de la caja. 

# Novedades de Java 1.9
- Posibilidad de definir funciones static private con código en interfaces.
- Se añaden los métodos .of en todas las interfaces funcionales de Collections (List, Map, Set...)
- La posibilidad de usar anotaciones en los argumentos de una expresión lambda.... Precisamente para evitar ambigüedades en el código.
   (@NonNull Integer numero) -> numero * 7 
- Proyecto Jigsaw: Módulos <- Principio de inversión de dependencias

# Novedades de Java 1.17
- Records
- Sealed classes/interfaces <- Principio de Liskov / Open-Closed

    module-info.java
```java
module miModulo {
    exports miPaquete;
    requires otroModulo;
    uses miInterfaz;
    provides miInterfaz with miClase;
}
```

```java

public class LibreriaMatematica {

    private LibreriaMatematica() { }

    public static int doblar(int numero){
        return numero * 2;
    }

}


public interface OperacionesMatematicas {

    static int doblar(int numero){
        return LibreriaMatematica.doblar(numero);
    }

}
```


---

# Getter y Setter en java

```java

public class Usuario {
    public String nombre;
    public int edad;
} // MUY MALA PRACTICA EN JAVA: ES CIERTO. Debido a una carencia grave del lenguaje.

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        usuario.nombre = "Felipe";
        usuario.edad = 33;

        System.out.println(usuario.nombre);
        System.out.println(usuario.edad);
    }
}

```

## Buena práctica
```java
public class Usuario {
    private String nombre;
    private int edad;

    public Usuario(String nombre, int edad){
        this.setNombre(nombre);
        this.setEdad(edad);
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        // CODIGO DE VALIDACION
        this.nombre = nombre;
    }

    public int getEdad(){
        return edad;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }
}

import org.lombok.Getter;
import org.lombok.Setter;

@Getter @Setter //Compilador de Java: QUIERO tener setter y getter para todas las propiedades de la clase.
public class Usuario {
    private String nombre;
    private int edad;
}
```

Los getters y los setters sirven para CONTROLAR el acceso a las propiedades de un objeto.
El tema es si no quiero controlarlo.

```java
// DIA 1
public class Usuario {
    public String nombre;
    public int edad;
}
// DIA 2-100 se empieza a usar la clase
//...
Usuario usuario = new Usuario();
usuario.nombre = "Felipe";
usuario.edad = 33;
System.out.println(usuario.nombre);
System.out.println(usuario.edad);

// DIA 101 se me ocurre querer asegurarme que la edad sea mayor o igual a cero.
public class Usuario {
    public String nombre;
    private int edad;
    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad){
        if(edad < 0){
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        this.edad = edad;
    }
}
// DIA 102 TENGO tropecientas personas kalasnikov en mano, buscándome por ahi!
// Para evitar estos problemas, en JAVA, la buena práctica es crear SIEMPRE getters y setters de TODOS las propiedades de un objeto.
// Por si acaso el día de mañana se me ocurre meter algo de código ahí.
```

Los getters y setters vacíos están ahí para facilitar el mantenimiento futuro del código.
Esto no pasa en ningún otro lenguaje de programación.
Otros lenguajes tienen en su gramática el concepto de PROPIEDAD.
En JAVA no existe el concepto de propiedad.

```python
class Usuario:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

usuario = Usuario("Felipe", 33)
print(usuario.nombre)
print(usuario.edad)
```
Y si el día de mañana hay que meter un cambio en la edad:

```python
class Usuario:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self._edad = edad

    @property
    def edad(self):
        return self._edad

    @edad.setter
    def edad(self, edad):
        if edad < 0:
            raise ValueError("La edad no puede ser negativa")
        self._edad = edad

usuario = Usuario("Felipe", 33)
usuario.edad= 44;
print(usuario.nombre)
print(usuario.edad)


```

# Funciones default en interfaces

```java

public interface Usuario {
    getNombre();
    setNombre(String nombre);
    getEdad();
    setEdad(int edad);
    // Desde java 1.8 podemos poner funciones con código en interfaces: default
    default void saludar(){
        System.out.println("Hola, soy " + getNombre());
    }
}
```

## Para qué sirve eso?

- Para tener código que hereden las implementaciones:
Esto NUNCA lo usaríamos para tener un código en la interfaz que hereden las implementaciones. NUNCA !
Para eso tenemos en JAVA otro concepto: Herencia + Clase Abstracta

- Para facilitar el mnto. futuro del código.
```java

// DIA 1
public interface Usuario {
    getNombre();
    setNombre(String nombre);
    getEdad();
    setEdad(int edad);
}

// DIA 2-100 uso la interfaz
// ...
Usuario usuario = new UsuarioImpl();
usuario.setNombre("Felipe");
usuario.setEdad(33);
System.out.println(usuario.getNombre());
System.out.println(usuario.getEdad());

// El día 101, quiero meter la propiedad "dni" en la interfaz Usuario
public interface Usuario {
    getNombre();
    setNombre(String nombre);
    getEdad();
    setEdad(int edad);
    getDni();
    setDni(String dni);
}

// Qué pasa el día 102? Rompo el código de todo el mundo hasta que no implementen la nueva propiedad dni.

public interface Usuario {
    getNombre();
    setNombre(String nombre);
    getEdad();
    setEdad(int edad);
    default getDni(){
        throw new NotImplementedException("No implementado");
    }
    default setDni(String dni){
        throw new NotImplementedException("No implementado");
    }
}
```
Que me garantiza esta implementación?
Que el código del resto de la gente sigue compilando...y funcionando? o les va a saltar la excepción?

Un producto de SOFTWARE POR DEFINICIÓN es un producto sujeto a MANTENIMIENTOS Y EVOLUTIVOS.

El problema no es hacer un programa que funcione. Eso se da por descontado.
El problema es hacer un programa sea fácil de mantener y evolucionar en el tiempo.

Los creadores de coches (ingenieros) no solo se preocupan de que el coche funcione... eso se da por descontado.
Tienen en cuenta que el coche requiere mantenimiento en el tiempo.
Que al año habrá que cambiarle el aceite.

    PICAR CODIGO <> PRUEBAS -> OK -> REFACTORIZAR <> PRUEBAS -> OK
    <------50% del trabajo------>    <------50% del trabajo------>
         8 horas de trabajo               8 horas de trabajo


```java

public interface Dato {

}

public class MiClase {
    public Dato miMetodo(){
        // lo que sea...
    }
}

// Usar ese método
//...
MiClase miClase = new MiClase();
Dato dato = miClase.miMetodo();
if(dato == null){
    // hacer algo
}
else{
    // hacer otra cosa
}
```

Desde Java 8, eso lo escribiríamos:

```java
import java.util.Optional;

public interface Dato {

}

public class MiClase {
    public Optional<Dato> miMetodo(){
        // lo que sea...
    }
}

// Usar ese método
//...
MiClase miClase = new MiClase();
Optional<Dato> dato = miClase.miMetodo();
if(dato.isEmpty()){ // .isPresent()
    // hacer algo
}
else{
    Dato elDatoDeDentro = dato.get();
    // hacer otra cosa
}
```

Para crear un Optional:
- Optional.empty() -> Caja vacía
- Optional.of(dato) -> Caja con dato
- Optional.ofNullable(dato) -> Caja con dato si dato no es null, caja vacía si dato es null.

## Para qué sirve?

Evitar ambigüedades en el código. SOLAMENTE!
Facilitar la legibilidad del código.


```java
public interface MiInterfaz {
    public Dato miMetodo();
}
```
Pregunta: Qué devuelve esa función? Alguien lo sabe?
Qué puede devolver? un objeto Dato... solo? también podría devolver null.
Seguro? que puede devolver null? Cómo lo sé? Quiero decir... potencialmente SI puede devolver nulo...
Pero.. en la realidad devolverá null en algún escenario esa función?
Entonces, que opciones me quedan para entender qué me puede EFECTIVAMENTE devolver la función?
- Mirar el código de la implementación
- Mirar la documentación... si la hay.
En serio??? año 2025?
En Java 1.8 se añade la clase Optional para evitar estos escenarios. Junto con una convención de uso:
- Desde Java 1.8 está considerado una horriblemente mala práctica que una función devuelva null.
- SONARQUBE os escupe la función a la cara!

Si una función puede devolver null... o mejor dicho, no devolver lo que debería devolver... lo que hacemos es que devuelva un Optional. De esa forma, nada más ver la declaración de la función, sabemos que esa función puede devolver un dato o no.

```java 

public interface Usuario {
   int getId();
   String getNombre();
   int getEdad();
}

public interface UserManager {
    Optional<Usuario> getUser(int id);
    // Lo único que consigo con esto es explicitar el comportamiento de la función.
    // La función puede ser que en su funcionamiento normal devuelva un usuario... o no.
    // En cualquier caso, siempre me dará una cajita... con un usuario dentro... o vacía.
}

```

# Los principios SOLID de desarrollo de software

Son 5 principios que me ayudan a crear código fácil de mantener y evolucionar en el tiempo.
- S = Single Responsability Principle
- O = Open/Closed Principle             
- L = Liskov Substitution Principle     <<<<>>>>
        Un subtipo debe poder ser sustituido por su tipo base sin que el programa se comporte de forma diferente.
        También se puede interpretar como: Un subtipo debe poder ser sustituido por otro subtipo sin que el programa se comporte de forma diferente.
- I = Interface Segregation Principle
- D = Dependency Inversion Principle // Este motiva uno de los mayores cambios en JAVA (9): Módulos + Servicios


En ciencias exactas, la palabra principio es sinónimo de LEY. Algo que es así por definición.. y es inquebrantable.
En las ciencias no exactas (sociología, psicología, economía, etc), la palabra principio es sinónimo de guía moral.

```java 

public interface UserManager {
    Usuario getUser(int id);
}

```
La gente va a usar mi interfaz. El problema es que la interfaz no es explicita... es ambigua.
Qué me va a devolver la función getUser? Puede llegar a devolver null? NPI...
Queda en manos de quién esa decisión? de quién lo implemenmte:

```java
public class UserManagerImpl1 implements UserManager {
    public Usuario getUser(int id){
        // lo que sea
        if(condicion) return null;
        return new Usuario();
    }
}

public class UserManagerImpl2 implements UserManager {
    public Usuario getUser(int id){
        // lo que sea
        if(condicion) throw new RuntimeException("Error");
        return new Usuario();
    }
}
```


```java 

import java.util.Optional;
import org.lombok.NonNull;

public interface UserManager {
    Optional<Usuario> getUser(@NonNull Integer id);
}

```

Desgraciadamente java de serie no tiene la anotación @NonNull. Pero la podemos conseguir con LOMBOK.
