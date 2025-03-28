import java.time.temporal.ChronoUnit;
import java.util.List;

public record UnidadTemporal(ChronoUnit unidad, String nombreSingular, String nombrePlural) {

    public static final List<UnidadTemporal> UNIDADES_TEMPORALES = List.of(
            new UnidadTemporal(ChronoUnit.YEARS, "año", "años"),
            new UnidadTemporal(ChronoUnit.MONTHS, "mes", "meses"),
            new UnidadTemporal(ChronoUnit.DAYS, "día", "días"),
            new UnidadTemporal(ChronoUnit.HOURS, "hora", "horas"),
            new UnidadTemporal(ChronoUnit.MINUTES, "minuto", "minutos"),
            new UnidadTemporal(ChronoUnit.SECONDS, "segundo", "segundos")
    );

}
