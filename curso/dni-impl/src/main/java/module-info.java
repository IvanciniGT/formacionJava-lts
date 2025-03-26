module dni.impl {
    requires dni.api;
    requires static lombok;

    provides com.curso.dni.api.DNIUtils with com.curso.dni.impl.DNIUtilsImpl;

}