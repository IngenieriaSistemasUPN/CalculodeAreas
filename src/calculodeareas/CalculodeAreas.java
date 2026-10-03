/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculodeareas;
import java.util.Scanner;

// ============================================================================
// 1. MODELO DE DOMINIO (Modularidad y Abstracción)
// ============================================================================

abstract class FiguraGeometrica {
    private final String nombre;

    protected FiguraGeometrica(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract double calcularArea();
}

class Circulo extends FiguraGeometrica {
    private final double radio;

    public Circulo(double radio) {
        super("Círculo");
        this.radio = radio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * Math.pow(radio, 2);
    }
}

class Rectangulo extends FiguraGeometrica {
    private final double base;
    private final double altura;

    public Rectangulo(double base, double altura) {
        super("Rectángulo");
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return base * altura;
    }
}

class Triangulo extends FiguraGeometrica {
    private final double base;
    private final double altura;

    public Triangulo(double base, double altura) {
        super("Triángulo");
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return (base * altura) / 2.0;
    }
}

// ============================================================================
// 2. SERVICIO / CAPA DE ENTRADA (Fabrica las figuras y captura inputs)
// ============================================================================

class FabricadorDeFiguras {

    public static FiguraGeometrica crearDesdeTeclado(int opcion, Scanner lector) {
        return switch (opcion) {
            case 1 -> {
                double radio = pedirDimension(lector, "Ingrese el radio del círculo: ");
                yield new Circulo(radio);
            }
            case 2 -> {
                double base = pedirDimension(lector, "Ingrese la base del rectángulo: ");
                double altura = pedirDimension(lector, "Ingrese la altura del rectángulo: ");
                yield new Rectangulo(base, altura);
            }
            case 3 -> {
                double base = pedirDimension(lector, "Ingrese la base del triángulo: ");
                double altura = pedirDimension(lector, "Ingrese la altura del triángulo: ");
                yield new Triangulo(base, altura);
            }
            default -> null;
        };
    }

    private static double pedirDimension(Scanner lector, String mensaje) {
        System.out.print(mensaje);
        return lector.nextDouble();
    }
}

// ============================================================================
// 3. CLASE PRINCIPAL / INTERFAZ DE USUARIO (Responsabilidad Única)
// ============================================================================

public class CalculodeAreas {

    private static final int OPCION_CIRCULO = 1;
    private static final int OPCION_RECTANGULO = 2;
    private static final int OPCION_TRIANGULO = 3;
    private static final int OPCION_SALIR = 4;

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        boolean continuar = true;

        mostrarEncabezado();

        while (continuar) {
            mostrarMenu();
            int opcion = pedirOpcion(lector);

            if (opcion == OPCION_SALIR) {
                System.out.println("\n¡Gracias por usar CalculodeAreas!");
                continuar = false;
            } else if (esOpcionValida(opcion)) {
                FiguraGeometrica figura = FabricadorDeFiguras.crearDesdeTeclado(opcion, lector);
                mostrarResultado(figura);
            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        lector.close();
    }

    private static void mostrarEncabezado() {
        System.out.println("========================================");
        System.out.println("      CÁLCULO DE ÁREAS - MÓDULO POO     ");
        System.out.println("========================================");
    }

    private static void mostrarMenu() {
        System.out.println("\nSeleccione la figura geométrica:");
        System.out.println("1. Círculo");
        System.out.println("2. Rectángulo");
        System.out.println("3. Triángulo");
        System.out.println("4. Salir");
        System.out.print("Opción (1-4): ");
    }

    private static int pedirOpcion(Scanner lector) {
        return lector.nextInt();
    }

    private static boolean esOpcionValida(int opcion) {
        return opcion >= OPCION_CIRCULO && opcion <= OPCION_TRIANGULO;
    }

    private static void mostrarResultado(FiguraGeometrica figura) {
        if (figura != null) {
            System.out.printf("%n-> Área del %s: %.2f unidades cuadradas%n",
                    figura.getNombre(), figura.calcularArea());
        }
    }
}