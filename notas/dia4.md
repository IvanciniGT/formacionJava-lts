
# Novedades Java 8

- Paquete java.util.function que da soporte a programación funcional.
- Nuevos operadores :: y ->
- Implementación del modelo de programación MAP-REDUCE, mediante el paquete java.util.stream.
- Inclusión del objeto Optional<T> para quitar ambigüedades en las firmas de los métodos.
- Nuevo paquete de fecha y hora java.time. (antiguo joda-time)
- Funciones default y static(públicas)  en interfaces.

# Novedades Java 9

- Projecto jig-saw: Modularización de Java.
- adición del fichero module-info.java
- Carga dinámica de clases desde interfaces: ServiceLoader
- Funciones static privadas en interfaces.
- Métodos .of() en List, Set y Map.

# Novedades Java 10

- Uso de la palabra reservada var para inferir el tipo de una variable.

# Novedades Java 11

- Algunos cambios String: .isBlank(), .strip(), .repeat(), .lines()
- Nuevo paquete java.net.http para peticiones HTTP.

# Novedades Java 17

- Se introduce el concepto de "sealed classes/interfaces"
- Patrones instanceof 
- Switch como expresión
- Record
- Text Blocks
- Fuertes mejoras en el rendimiento de la JVM (especialmente impulsadas por un nuevo Garbage Collector)

Adicionalmente en todas ellas se han ido introduciendo algunos cambios menores en el API. Nuevas funciones... en algunas clases de las que ya existían.