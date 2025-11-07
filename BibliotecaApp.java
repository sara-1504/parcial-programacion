
// -------------------------------------------------------------
// PROGRAMA: Sistema de gestión de biblioteca
// ASIGNATURA: Programación Orientada a Objetos
// OBJETIVO: Gestionar préstamos, devoluciones y multas de libros
// AUTORA: [Sara Ximena Delgado Aguirre, Andres Alfonso Carlos Gaviria]
// -------------------------------------------------------------

import java.util.ArrayList;
import java.util.Scanner;

// -------------------------------------------------------------
// CLASE BASE: MaterialBibliografico
// Representa cualquier material que exista en la biblioteca.
// -------------------------------------------------------------
class MaterialBibliografico {
    // Atributos comunes a todos los materiales
    protected String codigo;
    protected String titulo;
    protected boolean disponible;

    // Constructor
    public MaterialBibliografico(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.disponible = true; // por defecto, el material está disponible
    }

    // Método que muestra información básica del material
    public void mostrarInfo() {
        System.out.println("Código: " + codigo + ", Título: " + titulo + ", Disponible: " + disponible);
    }

    // Getters y setters
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }
}

// -------------------------------------------------------------
// CLASE DERIVADA: Libro
// Hereda de MaterialBibliografico y agrega más atributos
// -------------------------------------------------------------
class Libro extends MaterialBibliografico {
    private String autor;
    private String genero;
    private int paginas;

    // Constructor con herencia
    public Libro(String codigo, String titulo, String autor, String genero, int paginas) {
        super(codigo, titulo); // Llamada al constructor de la clase padre
        this.autor = autor;
        this.genero = genero;
        this.paginas = paginas;
    }

    // Sobrescritura del método mostrarInfo() -> Polimorfismo
    @Override
    public void mostrarInfo() {
        System.out.println("Código: " + codigo + ", Título: " + titulo +
                ", Autor: " + autor + ", Género: " + genero +
                ", Páginas: " + paginas + ", Disponible: " + disponible);
    }
}

// -------------------------------------------------------------
// CLASE: Prestamo
// Representa un préstamo realizado en la biblioteca
// -------------------------------------------------------------
class Prestamo {
    private Libro libro;
    private int diasPrestamo;

    public Prestamo(Libro libro, int diasPrestamo) {
        this.libro = libro;
        this.diasPrestamo = diasPrestamo;
        libro.setDisponible(false); // El libro prestado ya no está disponible
    }

    // Método para calcular multa si se pasa de 7 días
    public double calcularMulta() {
        if (diasPrestamo > 7) {
            return (diasPrestamo - 7) * 500; // multa de $500 por día adicional
        }
        return 0;
    }

    public Libro getLibro() {
        return libro;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }
}

// -------------------------------------------------------------
// CLASE PRINCIPAL: BibliotecaApp
// Contiene el menú principal y la lógica del programa
// -------------------------------------------------------------
public class BibliotecaApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Libro> biblioteca = new ArrayList<>();
        ArrayList<Prestamo> prestamos = new ArrayList<>();

        // -------------------------------------------------------------
        // INVENTARIO INICIAL (OBJETOS DE TIPO LIBRO)
        // -------------------------------------------------------------
        biblioteca.add(new Libro("L001", "Cien años de soledad", "Gabriel García Márquez", "Realismo mágico", 471));
        biblioteca.add(new Libro("L002", "1984", "George Orwell", "Distopía", 328));
        biblioteca.add(new Libro("L003", "El principito", "Antoine de Saint-Exupéry", "Fábula", 96));
        biblioteca.add(new Libro("L004", "Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", 863));
        biblioteca.add(new Libro("L005", "Orgullo y prejuicio", "Jane Austen", "Romance", 432));
        biblioteca.add(new Libro("L006", "Crimen y castigo", "Fiódor Dostoyevski", "Novela", 671));
        biblioteca.add(new Libro("L007", "Harry Potter y la piedra filosofal", "J. K. Rowling", "Fantasía", 309));
        biblioteca.add(new Libro("L008", "Los juegos del hambre", "Suzanne Collins", "Ciencia ficción", 390));
        biblioteca.add(new Libro("L009", "La sombra del viento", "Carlos Ruiz Zafón", "Misterio", 565));
        biblioteca.add(new Libro("L010", "El alquimista", "Paulo Coelho", "Fábula", 208));

        int opcion; // Variable para controlar el menú

        // -------------------------------------------------------------
        // MENÚ PRINCIPAL (con condicionales y switch)
        // -------------------------------------------------------------
        do {
            System.out.println("\n===== MENÚ BIBLIOTECA =====");
            System.out.println("1. Inventario");
            System.out.println("2. Préstamo");
            System.out.println("3. Devolución");
            System.out.println("4. Multas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- INVENTARIO DE LIBROS ---");
                    for (Libro l : biblioteca) {
                        l.mostrarInfo();
                    }
                    break;

                case 2:
                    System.out.println("\n--- REALIZAR PRÉSTAMO ---");
                    System.out.print("Ingrese el código del libro: ");
                    String codigo = sc.nextLine();
                    boolean encontrado = false;

                    for (Libro l : biblioteca) {
                        if (l.getCodigo().equalsIgnoreCase(codigo)) {
                            encontrado = true;
                            if (l.isDisponible()) {
                                System.out.print("Ingrese los días de préstamo: ");
                                int dias = sc.nextInt();
                                sc.nextLine();

                                Prestamo p = new Prestamo(l, dias);
                                prestamos.add(p);
                                System.out.println(" Préstamo realizado correctamente.");
                            } else {
                                System.out.println(" El libro no está disponible.");
                            }
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println(" No se encontró un libro con ese código.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- DEVOLUCIÓN DE LIBRO ---");
                    System.out.print("Ingrese el código del libro a devolver: ");
                    String codigoDev = sc.nextLine();
                    boolean devuelto = false;

                    for (Prestamo p : prestamos) {
                        if (p.getLibro().getCodigo().equalsIgnoreCase(codigoDev)) {
                            p.getLibro().setDisponible(true);
                            prestamos.remove(p);
                            System.out.println(" Devolución exitosa. El libro vuelve al inventario.");
                            devuelto = true;
                            break;
                        }
                    }

                    if (!devuelto) {
                        System.out.println(" No hay registro de préstamo con ese código.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- CONSULTA DE MULTAS ---");
                    for (Prestamo p : prestamos) {
                        double multa = p.calcularMulta();
                        if (multa > 0) {
                            System.out.println("Libro: " + p.getLibro().getTitulo() +
                                    " | Días: " + p.getDiasPrestamo() +
                                    " | Multa: $" + multa);
                        }
                    }
                    break;

                case 5:
                    System.out.println(" Saliendo del sistema...");
                    break;

                default:
                    System.out.println(" Opción no válida, intente nuevamente.");
            }

        } while (opcion != 5);
    }
}