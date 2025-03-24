public class Usuario {
    public String nombre;
    public int edad;

    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        usuario.nombre = "Felipe";
        usuario.edad = 33;

        System.out.println(usuario.nombre);
        System.out.println(usuario.edad);
    }
}
