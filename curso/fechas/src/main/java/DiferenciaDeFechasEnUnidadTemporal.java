import java.time.ZonedDateTime;

final class DiferenciaDeFechasEnUnidadTemporal {

    private final long diferencia;
    private final UnidadTemporal unidadTemporal;
    private final boolean pasado;

    public DiferenciaDeFechasEnUnidadTemporal(UnidadTemporal unidadTemporal, ZonedDateTime fechaReferencia, ZonedDateTime fecha) { // Es una sintaxis especial que tienen los records para completar el constructor
        this.unidadTemporal = unidadTemporal;
        this.pasado = fecha.isBefore(fechaReferencia);
        this.diferencia = Math.abs(unidadTemporal.unidad().between(fechaReferencia, fecha));
        System.out.println("DIFERENCIA DE FECHAS CREADA");
    }

    public boolean existe() {
        return diferencia > 0;
    }

    public String toString() {
        String primeraParte = pasado ? "Hace " : (diferencia == 1 ? "Falta " : "Faltan ");
        String cantidad = diferencia + " ";
        String unidad = diferencia == 1 ? unidadTemporal.nombreSingular() : unidadTemporal.nombrePlural();
        return primeraParte + cantidad + unidad;
    }
}
