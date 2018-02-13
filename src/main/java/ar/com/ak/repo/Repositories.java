package ar.com.ak.repo;

public class Repositories {

    private static Articulos articulos;
    private static Categorias categorias;
    private static Empresas empresas;
    private static Parametros parametros;
    private static Talles talles;
    private static Transacciones transacciones;
    private static Usuarios usuarios;

    private Repositories() {
    }

    public static Articulos Articulo() {
        return (articulos == null) ? articulos = new Articulos() : articulos;
    }

    public static Categorias Categoria() {
        return (categorias == null) ? categorias = new Categorias() : categorias;
    }

    public static Empresas Empresa() {
        return (empresas == null) ? empresas = new Empresas() : empresas;
    }

    public static Parametros Parametro() {
        return (parametros == null) ? parametros = new Parametros() : parametros;
    }

    public static Talles Talle() {
        return (talles == null) ? talles = new Talles() : talles;
    }

    public static Transacciones Transaccion() {
        return (transacciones == null) ? transacciones = new Transacciones() : transacciones;
    }

    public static Usuarios Usuario() {
        return (usuarios == null) ? usuarios = new Usuarios() : usuarios;
    }

}
