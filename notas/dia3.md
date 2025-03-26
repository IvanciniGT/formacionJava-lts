
Es buen momento para hacer las pruebas? SI/NO?

    SI IIIIIIIII
    NO I

No es buen momento porque vamos tarde... mjy tarde!

Cuando debimos hacer las pruebas?
Antes de escribir ni una sola linea de código!

Eso es lo que se llama seguir una metodología TDD (Test Driven Development)

TDD = 
    1. Diseño las pruebas
    2. Escribo el código que haga pasar las pruebas
    3. Refactorizo el código
    4. Vuelvo a asegurarme que las pruebas pasan
    5. Empiezo de nuevo

---

Una cosa es diseñar las pruebas y otra ejecutarlas

---

# Modelo de programación MAP-REDUCE.

Es un modelo que se basa en aplicar operaciones MAP/REDUCE sobre un conjunto de datos que soporte MAP-REDUCE.
La idea subyacente es tomar el conjunto de datos e irlo transformando en otro conjunto de datos.... Y así muchas veces. Eso lo hacemos mediante operaciones MAP.
Al final acabamos aplicando una operación REDUCE.


    COLECCION_INICIAL 
        -> MAP -> COLECCION_INTERMEDIA 
            -> MAP -> COLECCION_INTERMEDIA2 
                -> REDUCE -> RESULTADO_FINAL

Pero entonces, qué es una función MAP?
- Cualquier función que dado un conjunto de datos que soporte programación MAP-REDUCE, devuelva otro conjunto de datos que soporte programación MAP-REDUCE.
    Llevado a Java, cualquier función que aplicada sobre un Stream<T> devuelva otro Stream<R>, como por ejemplo, la función .map().

    Las funciones MAP se ejecutan en modo PEREZOSO (LAZY). Es decir, no se ejecutan hasta que no se necesita el resultado.

Y.. qué es una función tipo REDUCE?
- Cualquier función que dado un conjunto de datos que soporte programación MAP-REDUCE, devuelva algo que no es un conjunto de datos que soporte programación MAP-REDUCE.
    Llevado a Java, cualquier función que aplicada sobre un Stream<T> devuelva algo que no es un Stream, como por ejemplo, la función .reduce().

    Las funciones REDUCE se ejecutan en modo ANSIOSO (EAGER). Es decir, se ejecutan en cuanto se invocan. Y entonces, llegado a este punto, se provoca un efecto dominó!
    Al querer sacar el valor, necesita partir de un dato... que es el que se producía con un map... y entonces es cuando se ejecuta ese map...
    Pero ese map partía del resultado de otro map... y entnoces es cuando ese otro map se ejecuta... y así sucesivamente!


List<T> soporta map-reduce?  NO
Map<T,R> soporta map-reduce? NO
Set<T> soporta map-reduce?   NO
Stream<T> soporta map-reduce? SI



Un tipo especial de proyectos maven son los proyectos multi-módulo.

Un proyecto multi-módulo contiene otros proyectos hijos.
Eso me permite desde el proyecto padre, poder gestionar todos los proyectos hijos de una sola vez.


proyecto-raiz/
    pom.xml
    modulo1/
        src/
        pom.xml
    modulo2/
        src/
        pom.xml
    modulo3/
        src/
        pom.xml

El proyecto padre no tiene carpeta src, porque no tiene código.
Es más. En el proyecto padre, vamos a poner una marca especial en el pom.xml:

    <packaging>pom</packaging>
    <!-- Esto indica que este proyecto no debe empaquetar código... Solo tiene un pox.xml -->

Adicionalmente, hemos de declarar en este fichero los módulos hijos:
    
    <modules>
        <module>modulo1</module>
        <module>modulo2</module>
        <module>modulo3</module>
    </modules>



---


   app.jar   ----->    dni-api.jar     <------    dni-impl.jar

En JAVA 9, se introduce el concepto de MODULOS.... igual que en maven!
Hasta JAVA 9, podíamos crear en JAVA:
    modulo:
         packages:
            classes
            interfaces
            enums

Los módulos permiten:
  - Controlar lo que un desarrollador quiere hacer público de su módulo (qué clases o interfaces se pueden usar desde fuera)
     ESTO SE PODÍA ANTES EN JAVA? SIII, pero CON GRAVES LIMITACIONES

        Si un desarrollador montaba una librería.. por ejemplo, una para trabajar con DNIs.
        Y  la empaqueta en un .jar
        Y ahí tiene una clase publica llamada: Dni
        Desde fuera de la librería, alguien que usase esa librería, podía hacer? CLARO!!
            Dni dni = new Dni();

    Un módulo puede exportar algunos de sus paquetes... pero otros NO.
    Y aunque en esos otros paquetes haya cosas publicas, no se podrán usar desde fuera del módulo.
  - Los módulos DEBEN DECLARAR EXPLICITAMENTE DE QUE MÓDULOS DEPENDEN.
      (básicamente lo mismo que hacemos en MAVEN)
      Eso lo controlará la JVM. De forma que controla el acceso a las dependencias... El hecho de que haya un .jar en el classpath no implica que una clase tenga derecho a usar lo que hay en el .jar.
  - Permitir a un módulo acceder a implementaciones de una interfaz sin conocer quien la implementa.
      (esto es una forma de evitar romper el principio de inversión de dependencias)
      Y de paso nos evita también el crearnos ESTUPIDAS CLASES FACTORIA.
      Que antes creábamos decenas de ellas.. solo por no tener una forma mejor de evitar romper el PPO de Inversión de Dependencias.

      Un módulo puede declarar implementaciones para una interfaz que se pueden usar desde fuera del módulo... sin que los otros que la usan sepan quien la implementa.

        provides com.dnis.api.DniUtils with com.dnis.impl.DniUtilsImpl;

      Otros módulos peuden indicar a la JVM que necesitan una implementación de una interfaz... y la JVM se encargará de buscarla en el classpath.

        uses com.dnis.api.DniUtils;

---
dnis-library.jar
    package com.dnis.api
        public class Dni extends Respuesta
        public interface DniUtils
        public abstract class Respuesta
        public class ErrorDNI extends Respuesta

        import com.dnis.impl.DniUtilsImpl;
        public interface DniUtilsFactory{
            private static DniUtils instance = null;
            static getInstance(){
                if(instance == null){
                    instance = new DniUtilsImpl();
                }
                return instance;
            }
        }

    package com.dnis.impl
        public class DniUtilsImpl implements DniUtils
            // Imaginad que cuesta un huevo y parte de otro instanciar esta clase. Requiere mucha RAM y CPU... carga una BBDD en memoria..

---
app.jar
    package com.app
        import com.dnis.api.DniUtilsFactory;
        public class App
            main(){
                DNIUtils dniUtils = DniUtilsFactory.getInstance();
                // Esto funcionaría genial.
                // Y nos respetaría el PPO de Inversión de Dependencias
                // Ahora bien... Me limita a mi !
                // Oh! Creador de la App! a escribir la siguiente linea de código:
                DniUtilsImpl dniUtils = new DniUtilsImpl();
                // Es decir, yo puedo seguir haciendo esto.
                // Y eso es bueno? NO
                // Por varios motivos:
                // 1. Desde el punto de vista de nosotros Oh! Creadores de la aplicación, podemos meter la pata a la mínima.
                // 2. Desde el punto de vista de los creadores de la librería, es una vulverabilidad de seguridad potencial.

                // Queremos resolver este entuerto.
                // Quiero desde la librería LIMITAR la posibilidad de que alguien, al usar la librería pueda escribir la linea de arriba.
            }




---
                                            ZonedDateTime
Nos gustaría una función a la que le paso una fecha.-.. y me explica en texto el tiempo que ha pasado o que falta hasta la misma.
Queremos el típico pie de mensaje de red social:
    Hace 2 horas
    Dentro de 3 días
    Hace 1 minuto
    El año pasado
    El mes pasado

Vamos a montar una segunda función... que haga lo mismo pero con 2 fechas
Vamos a montar una segunda función... que haga lo mismo pero con 1 fecha y una zona horaria.
