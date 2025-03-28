package com.curso.palabras;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ContarPalabras {

    record Palabra(String palabra, int numeroOcurrencias) {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String webConHTML = """
        https://www.gutenberg.org/files/2701/2701-h/2701-h.htm
        """;
        String contenido = traermeElContenidoDeLaWeb(webConHTML.strip());
        String textoLimpio = limpiarHTML(contenido);
        var palabrasEnElTexto = contarPalabras(textoLimpio);
        imprimirResultado(palabrasEnElTexto, 50);
    }

    private static void imprimirResultado(List<Palabra> palabrasEnElTexto, int i) {
        palabrasEnElTexto.stream()
                .limit(i)
                .forEach(palabra -> System.out.println(palabra.palabra() + " -> " + palabra.numeroOcurrencias()));
    }

    private static List<Palabra> contarPalabras(String textoLimpio) {
        // Quiero separar por espacios, signos de puntuación, etc...... Mediante REGEX
        // Posteriormente obtener un Matcher y del matcher obtener un Stream

        Pattern patron = Pattern.compile("\\b\\w+\\b");
        return patron.matcher(textoLimpio)
                .results() // Stream<String>
                .map(matchResult -> matchResult.group().toLowerCase())
                .filter( palabra -> palabra.length() > 4) // Quitando las palabras que aportan poco valor
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                // Llegados a este punto, ya tenemos las palabras y su cantidad de ocurrencias

                // ORDENAMOS POR OCURRENCIA
                .entrySet()
                .stream()
                .map(entry -> new Palabra(entry.getKey(), entry.getValue().intValue()))
                .sorted(Comparator.comparingInt(Palabra::numeroOcurrencias).reversed()) // De la que más ocurrencias tiene, a la que menos
                .toList();
    }


    private static String limpiarHTML(String contenido) {
        return contenido.replaceAll("<--.*-->", " ") // Para salvar comentarios con > : <!---  > --->
                        .replaceAll("<[^>]*>", " ");
    }

    private static String traermeElContenidoDeLaWeb(String webConHTML) throws IOException, InterruptedException {
        HttpClient cliente = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webConHTML))
                .GET()
                .build();

        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
