import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class MapReduce {
    

    public static void main(String[] args) {
        
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Stream<Integer> stream = numeros.stream();
        //List<Integer> numerosOtraVez = stream.collect(Collectors.toList());// Estro sería Java 1.8
        // En Java 1.17 sería:
        List<Integer> numerosOtraVez = stream.toList();

        // Imaginad que quiero:
        // - multiplicar los números por 2, 
        // - quitarles 5, 
        // - multiplicarlos por 3 
        // - y quedarme con los mayores que 10... en un nuevo List<Integer>

        List<Integer> transformados = numeros.stream()
                // Transformar los elementos de una colección mediante una función de transformación
               .map( unNumero -> unNumero * 2 ) // Aplicar un Function<T,R> a cada elemento de un stream y guarda los resultados en un nuevo stream
               .map( unNumero -> unNumero - 5 )
               .map( unNumero -> unNumero * 3 )
               .filter( unNumero -> unNumero > 10 )
               .toList();
        System.out.println(transformados);
    }

}
