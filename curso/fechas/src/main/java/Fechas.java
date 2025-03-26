import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Fechas {

    public static void main(String[] args) {
        System.out.println("Hola, Fechas de JAVA");

        // En Java 1.8 se añade la antigua librería joda-time para la gestión de fechas
        // dentro del api de java... bajo el nombre: java.time

        LocalDate fecha = LocalDate.now();
        System.out.println("Fecha de hoy: " + fecha);

        LocalDate fechaDescubrimientoAmerica = LocalDate.of(1492, 10, 12);
        System.out.println("Descubrimiento de América: " + fechaDescubrimientoAmerica);

        // Puedo hacer operaciones sobre esas fechas fácilmente
        var ayer = fecha.minusDays(1);
        var mañana = fecha.plusDays(1);
        var haceUnMes = fecha.minusMonths(1);
        var dentroDeUnAño = fecha.plusYears(1);
        var dentroDe5Días = fecha.plus(5, ChronoUnit.DAYS);
        var hace7Semanas = fecha.minus(7, ChronoUnit.WEEKS);

        // Peiodos de tiempo. Un periodo de tiempo es la diferencia entre dos fechas
        Period periodo = fechaDescubrimientoAmerica.until(fecha);
        System.out.println("Periodo entre el descubrimiento de América y hoy: " + periodo);
        periodo = Period.between(fechaDescubrimientoAmerica, fecha);
        System.out.println("Periodo entre el descubrimiento de América y hoy: " + periodo);

        Period dosMeses = Period.ofMonths(2);
        System.out.println("Dos meses: " + dosMeses);
        Period unAñoUnMesUnDia = Period.of(1, 1, 1);
        System.out.println("Un año, un mes y un día: " + unAñoUnMesUnDia);

        var dentroDe2Meses = fecha.plus(dosMeses);
        var dentroDe1Año1Mes1Dia = fecha.plus(unAñoUnMesUnDia);
        System.out.println("Dentro de dos meses: " + dentroDe2Meses);
        System.out.println("Dentro de un año, un mes y un día: " + dentroDe1Año1Mes1Dia);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Fecha de hoy formateada: " + fecha.format(formato));
        System.out.println("Descubrimiento de América formateado: " + fechaDescubrimientoAmerica.format(formato));

        // Antiguamente trabajábamos mucho con la clase Date
        // Ahora podemos convertir de LocalDate a Date y viceversa... A través de la clase Instant
        Date ahora = new Date();
        System.out.println("Fecha de ahora: " + ahora);
        var instanteAhora = ahora.toInstant();
        System.out.println("Instante de ahora: " + instanteAhora);

        var fechaDeAhora = instanteAhora.atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("Fecha de ahora: " + fechaDeAhora);

        // Además de LocalDate tenemos otras clases para trabajar con fechas y horas.
        // LocalDateTime        Esto es una fecha, con hora... sin incluir información de zona horaria
        // ZonedDateTime        Esto es una fecha, con hora... incluyendo información de zona horaria

        LocalDateTime ahoraMasTarde = LocalDateTime.now();
        ZonedDateTime ahoraEnMadrid = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        ZonedDateTime ahoraEnColombia = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        System.out.println("Ahora en Madrid: " + ahoraEnMadrid);
        System.out.println("Ahora en Colombia: " + ahoraEnColombia);

        var hoyAlComienzoDelDía = LocalDate.now().atStartOfDay();

        // Periodo de tiempo se mide en días, meses y años
        // Para unidades más pequeñas, se habla de Duración
        Duration dosHoras = Duration.ofHours(2);
        Duration tresMinutos = Duration.of(3, ChronoUnit.MINUTES);
        var enMadridDentroDe2Horas = ahoraEnMadrid.plus(dosHoras);

        // Operaciones sobre el LocalDate se hacen con el objeto Period
        // Operaciones sobre el LocalDateTime o ZonedDateTime se hacen con el objeto Duration
        var hoy = ahoraEnMadrid.toLocalDate();

        var dateAhoraEnColombia = Date.from(ahoraEnColombia.toInstant());
        System.out.println("Date de ahora en Colombia: " + dateAhoraEnColombia);

        // Hay otra clase que es la clase Time... si solo tengo información horaria.
        LocalTime hora = LocalTime.now();
        System.out.println("Hora de ahora: " + hora);


        var horaDeAhora = ahoraMasTarde.toLocalTime();
        System.out.println("Hora de ahora: " + horaDeAhora);
        var diaDeAhora = ahoraMasTarde.toLocalDate();
        System.out.println("Día de ahora: " + diaDeAhora);

        //ahoraEnMadrid
        //        Tenemos la función between(ahoraEnColombia, ahoraEnMadrid) de la clase Duration
        //Duration.between(ahoraEnColombia, ahoraEnMadrid);
        var diferenciaDeFechas = Duration.between(ahoraEnColombia, ahoraEnMadrid);
        System.out.println("Diferencia de fechas: " + diferenciaDeFechas);

        Period.between(ahoraEnColombia.toLocalDate(), ahoraEnMadrid.toLocalDate());


        ///  Muy util y cómoda (especialmente -aunque no solo- para programación funcional)
        ChronoUnit.YEARS.between(ahoraEnColombia, ahoraEnMadrid);
        ChronoUnit.MONTHS.between(ahoraEnColombia, ahoraEnMadrid);
        ChronoUnit.DAYS.between(ahoraEnColombia, ahoraEnMadrid);
        ChronoUnit.HOURS.between(ahoraEnColombia, ahoraEnMadrid);
        ChronoUnit.MINUTES.between(ahoraEnColombia, ahoraEnMadrid);
        ChronoUnit.SECONDS.between(ahoraEnColombia, ahoraEnMadrid);

        // Podríamos tener un List<ChronoUnit>
        // Y calcular la diferencia de la fecha en cada uno de los elementos de la lista
        // La primera que sea mayor que cero (en valor absoluto) es la que nos interesa
    }

/*
    Nos gustaría una función a la que le paso una fecha... y me explica en texto el tiempo que ha pasado o que falta hasta la misma.
    Queremos el típico pie de mensaje de red social:
    Hace 2 horas
    Dentro de 3 días
    Hace 1 minuto
    El año pasado
    El mes pasado
    Ahora mismo

    Necesitamos comparar ambas fechas/horas. RESTA:

    Vamos a montar una segunda función... que haga lo mismo pero con 2 fechas
    Vamos a montar una segunda función... que haga lo mismo pero con 1 fecha y una zona horaria.
*/
}
