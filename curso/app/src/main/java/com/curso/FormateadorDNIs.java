package com.curso;

import com.curso.dni.api.*;

import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;



//import com.curso.dni.impl.DNIUtilsImpl; // Pero esta linea es una CAGADA !!!!
// Nos estamos pasando por las narices el principio de inversión de dependencias

public class FormateadorDNIs {

    private static final long NUMERO_DNIS_VALIDOS = 20*1000*1000;
    private static final long NUMERO_DNIS_INVALIDOS = 20*1000*1000;
    private static final int NUMERO_DNI_MINIMO = 1;
    private static final int NUMERO_DNI_MAXIMO = 99999999;

    public static void main(String[] args){
        // Vamos a generar N dnis... un montón. VALIDOS
        List<DNI> dnis = generarColeccionDNISDeTodoTipo();
        System.out.println("Tenemos " + dnis.size() + " DNIs");
        long tin = System.currentTimeMillis();
        List<String> formateados = dnis.stream()//.parallel()
                // Esta función parallel lo que hace por detrás es abrir un pool de hilos
                // (tantos como cores tengas), en automático.
                // Eso me permite paralelizar los trabajos que hago en el stream, para aprovechar al máximo la CPU
                // que tenga disponible.
                // Sin necesidad de preocuparme de los hilos, ni de la concurrencia, ni de consolidar los resultados.
                // No siempre me interesa usarlo.
                // ME interesa usarlo cuando en mi máquina SOLO ESTOY EJECUTANDO ese proceso. AGOTA LA CPU.
                // Si estoy montando un servlet o un microservicio.. que puede atender 200 peticiones en paralelo,
                // NNO ME INTERESA ya que 1 sola de esas peticiones se comería todos los cores.
                // ME interesa si estoy montando un proceso BATCH, que va a estar ejecutándose en mi máquina
                .map(
                        dni ->{
                            if(dni.isValid()){ // Pattern Matching for instanceof
                                return getDNIUtils().format((DNIValido)dni);
                            } else {
                                return "DNI INVALIDO ("+((DNIInvalido)dni).getOriginal()+")";
                            }
                        }
                                // ESTO ES JAVA 21
                                //switch (dni) {
                                //   case DNIValido dniValido -> getDNIUtils().format(dniValido);
                                //   case DNIInvalido dniInvalido -> "DNI INVALIDO (" + dniInvalido.getOriginal() + ")";
                                // }
                ).toList();
        long tout = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (tout - tin) + " ms");
        formateados.stream().limit(20).forEach(System.out::println);
        //formateados.forEach(System.out::println);
         // Los vamos a formatear
        // Si son válidos, los formateo con el formato por defecto
        // Si no lo son, sacamos ("DNI INVALIDO (original)")
    }

    private static List<DNI> generarColeccionDNISDeTodoTipo() {
        List<DNI> validos = getDNIUtils().random(NUMERO_DNIS_VALIDOS);
        // Vamos a generar M dnis... un montón. INVALIDOS
        List<DNI> invalidos = invalidRandomDNIs(NUMERO_DNIS_INVALIDOS);
        // Los junto en una lista... y los mezclo (barajo)
        return Stream.of(validos, invalidos)
                .flatMap(List::stream)
                //.sorted(
                //        (dni1, dni2) -> Math.random() - 0.5 > 0 ? 1 : -1
                //)
                .toList();

        // que hace map? Transformar cada elemento de un stream según una función de transformación... generando un stream con los resultados de la función
        // flatMap es un map seguido de un flatten.
        // flatten: coger varios streams y juntar sus elementos en un solo stream

        /// COLECCIÓN DE PARTIDA        ---> map (split)    ---> COLECCIÓN
        //   Element 1: "hola amigo"                                Elemento1: ["hola", "amigo"]
        //   Element 2: "adios amigo"                               Elemento2: ["adios", "amigo"]

        /// COLECCIÓN DE PARTIDA        ---> flatMap (split)    ---> COLECCIÓN
        //   Element 1: "hola amigo"                                "hola"
        //   Element 2: "adios amigo"                               "amigo"
        //                                                          "adios"
        //                                                          "amigo"
    }

    // LA CPU... cualquier CPU tiene una CACHE.
    // Una vez un proceso entra en una cpu, se queda ahí un rato, haciendo cosas.
    // Y los datos que necesita, los trae a la cache (de la CORE)
    // Con la palabra volatile, le decimos a JAVA que evite ese comportamiento.
    // Que esa variable puede haber varios hilos que la estén tocando en paralelo.
    // Y no queremos que esa variable se quede en la cache de la CORE.
    private static volatile DNIUtils dniUtils;
    private static DNIUtils getDNIUtils(){
        if (dniUtils == null) {  // Para evitar la ejecución del synchronized si ya tenemos una instancia de DNIUtils
                                 // Ya que un synchronized es caro en términos de rendimiento
            synchronized (FormateadorDNIs.class){ // Si 2 o más hilos intentan ejecutar simultáneamente este código de dentro del synchronized
                                                  // Queremos generar una COLA... y que las ejecuciones se hagan de una en una.
                                                  // Esto se pone para evitar condiciones de carrera
                if (dniUtils == null) { // Para asegurarme que solo obtenemos una instancia de DNIUtils si no la tenemos
                    // Obtengo una instancia de DNIUtils... La que haya en el classpath!
                    Iterable<DNIUtils> implementaciones = ServiceLoader.load(DNIUtils.class);
                    if (!implementaciones.iterator().hasNext()) {
                        System.out.println("No se ha encontrado ninguna implementación de DNIUtils, y no puedo continuar");
                        System.exit(1);
                    }
                    dniUtils = implementaciones.iterator().next();
                }
            }
        }
        return dniUtils;
    }

    public static List<DNI> invalidRandomDNIs(long cantidad) {
        return ThreadLocalRandom.current()
                .ints(cantidad, NUMERO_DNI_MINIMO, NUMERO_DNI_MAXIMO)
                .mapToObj(numeroAleatorio -> (DNI)new DNIInvalido(TipoErrorDNI.LETRA_INCORRECTA, numeroAleatorio + "Ñ"))
                .toList();
    }
}
