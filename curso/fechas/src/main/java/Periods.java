import java.time.ZonedDateTime;

public class Periods {

    public static String tiempoRelativoConRespectoAAhora(ZonedDateTime fecha){
        return tiempoRelativo(ZonedDateTime.now(), fecha);
    }

    public static String tiempoRelativo(ZonedDateTime fechaReferencia, ZonedDateTime fecha){
        return  UnidadTemporal.UNIDADES_TEMPORALES
                   .stream()                                                       // Para cada unidad de medida
                   .map( unidadTemporal -> new DiferenciaDeFechasEnUnidadTemporal(unidadTemporal, fechaReferencia, fecha))   // Calculamos la diferencia
                   .filter( DiferenciaDeFechasEnUnidadTemporal::existe )            // Me quedo con las diferencias no nulas (las que existen)
                   .findFirst()                                                     // Tomo la primera (que es la mayor, y la que me interesa)
                   .map( DiferenciaDeFechasEnUnidadTemporal::toString )            // Si la hay, la formateo
                   .orElse("Ahora mismo");                                   // Si no hay ninguna, devuelvo "Ahora mismo", ya que significa que las fechas son iguales

        // Las funciones TIPO MAP de los STREAMS (map, filter) se ejecutan en modo lazy (perezoso)
        // No se ejecutan hasta que su resultado es necesario
        // Una de las grandes ventajas de la programación MAP REDUCE, es que como los maps no se van ejecutando sobre la marcha,
        // es la JVM la que toma la decisión de cuándo, cómo y qué ejecutar.

        // Puede parecer que ese código requiere 2 bucles para ser ejecutado: Uno para el map y otro para el filter
        // Pero la realidad es que la JVM no hace eso.

        // De entrada lo que ha apuntado es que sobre cada dato hay que ejecutar la función de transformación (la que pasamos al map)
        // Y filtrar el dato en base a la función de filtro (la que pasamos al filter)
        // Cuando llega la función de reduce (en nuestro caso: findFirst) la JVM empieza el trabajo...
        // Un trabajo que debido a la función de reduce, que estamos usando (findFirst), quizás no necesita ejecutarse sobre todos los datos...
        // Solo hasta que encuentra uno válido.

        // La JVM ejecuta un único for.

    }

}
