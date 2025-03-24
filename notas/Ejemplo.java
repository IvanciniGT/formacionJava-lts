import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*; // Esto es lo que aparece en Java 1.8
// Este paquete define interfaces, que permiten crear variables que apuntan a métodos
// De hecho las llamamos "interfaces funcionales"
// Las más importantes son:
// - Consumer<T>    Representa una función que recibe un argumento de tipo T y no devuelve nada
//                   .setXXX(Object value);
// - Function<T,R>  Representa una función que recibe un argumento de tipo T y devuelve un valor de tipo R
// - Supplier<R>    Representa una función que no recibe argumentos y devuelve un valor de tipo R
//                   .getXXX();
// - Predicate<T>   Representa una función que recibe un argumento de tipo T y devuelve un booleano
//                   .isXXX();
//                   .hasXXX();
// Esas interfaces definen métodos a través de los cuales invocamos a los métodos que apuntan las variables
// Consumer<T> -> void accept(T t);
// Function<T,R> -> R apply(T t);
// Supplier<R> -> R get();
// Predicate<T> -> boolean test(T t);
public class Ejemplo {

    private static void saluda(String nombre) {
        System.out.println("Hola " + nombre);
    }

    private static String generarSaludoInformal(String nombre) {
        return "Hola " + nombre;
    }

    private static String generarSaludoFormal(String nombre) {
        return "Buenos días " + nombre;
    }

    private static void imprimirSaludo(Function<String, String> funcionGeneradoraDeSaludos, String nombre){
        System.out.println(funcionGeneradoraDeSaludos.apply(nombre));
    }

    public static void main(String[] args) {
        saluda("Menchu");

        Consumer<String> variable = Ejemplo::saluda; // En java 1.8 sale un nuevo operador ::
        System.out.println("variable: " + variable);
        variable.accept("Ivan");

        imprimirSaludo(Ejemplo::generarSaludoFormal, "Felipe");
        imprimirSaludo(Ejemplo::generarSaludoInformal, "Federico");

        // El api de JAVA está migrando a programación funcional.
        List<Integer> numeros = new ArrayList<>(); // Esto es la guarrada que teníamos que hacer en JAVA antes...
        numeros.add(1);
        numeros.add(2);
        numeros.add(3);
        numeros.add(4);

        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9); // Ruina!

        List<Integer> numeros3 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9); // Java 1.9
        // Aprovechando una funcionalidad que sale en Java 1.8: 
        // La capacidad de pasar definir funciones static dentro de interfaces

        // Pre java 1.5... Como itero esa lista?
        for(int i = 0; i < numeros.size(); i++) {
            System.out.println(numeros.get(i));
        }
        // Entre java 1.5 y java 1.8. En Java 1,5 aparece el concepto de iterador
        for(Integer numero : numeros) {
            System.out.println(numero);
        }
        // Desde Java 1.8, todas las collections incluyen la función: .forEach(Consumer<T> consumer)
        numeros3.forEach(System.out::println); // Pero no solo ahorra código. Va mucho más rápido que los bucles for
                                               // Bucle interno

        numeros3.forEach(Ejemplo::operacionSuperCompleja); // Que hace?? Que sale por pantalla?

        // En casos donde solamente creamos una función por necesidad y el tener la función definida de forma independiente
        // no mejora la legibilidad del código, podemos usar expresiones lambda.

        // Qué es una expresión lambda?
        // Ante todo una expresión. Qué es una expresión? Un trozo de código que devuelve un valor.

        String unaVariable = "hola";  // Esto es un statement (sentencia: oración, frase): Esto es una Frase en JAVA
        int numero = 5*7;             // Esto es otro statement
                     /// Eso es una expresión: Trozo de código que devuelve un valor
                     /// 
         // Una lambda es un trozo de código que devuelve un valor... Qué valor?
         // Una función anónima definida dentro de la propia expresión.
         // Es una alternativa a la hora de definir una función.

        Consumer<Integer> otraVariable = Ejemplo::operacionSuperCompleja;
        Consumer<Integer> otraVariable2 = (Integer unNumero) -> { // en Java 1.8 aparece el operador ->
            System.out.println(unNumero * 2);
        };
    
        numeros3.forEach(Ejemplo::operacionSuperCompleja); // Que hace?? Que sale por pantalla?

        numeros3.forEach( (Integer unNumero) -> { // en Java 1.8 aparece el operador ->
            System.out.println(unNumero * 2);
        } );

        numeros3.forEach( (unNumero) -> { // En las lambda el tipo de los argumentos se infiere de la asignación
                                          // En las lambda el tipo devuelto se infiere del código
            System.out.println(unNumero * 2);
        } );

        numeros3.forEach( unNumero -> { // Cuando tengo solo un argmento me puedo fumar los paréntesis
            System.out.println(unNumero * 2);
        } );

        // Y si tengo una sola linea de cósigo, me puedo fumar las llaves
        numeros3.forEach( unNumero -> System.out.println(unNumero * 2) );

    }


    private static void operacionSuperCompleja(int numero) {
        System.out.println(numero * 2);
    }

}