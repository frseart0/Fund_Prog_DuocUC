package Exp2_S8_Francisco_Arellano;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;

public class Exp2_S8_Francisco_Arellano {
    
    static Scanner read = new Scanner(System.in);
    
    static int totalVentas = 0;
    static int totalIngresos = 0;
    
    public static int validarInt(int min, int max) {
        boolean valido = false;
        int numero = 0;
        while(valido==false){
            System.out.print("ingresar->");
            try {
                numero = read.nextInt();
                if (numero >= min && numero <= max) {
                    valido = true;
                } else {
                    System.out.println("entrada invalida");
                }
            } catch (InputMismatchException e) {
                System.out.println("entrada invalida");
                read.nextLine(); 
            }
        }
        return numero;
    }
    
    public static class Entrada{
        private int idEntrada;
        private int ubicacionEntrada;
        private int precioEntrada;
        private String tipoEntrada;         // general, estudiante, tercera edad
        
        private String nombreCliente;
        
        private boolean entradaComprada;
        private boolean entradaReservada;
        
        public Entrada(int idEntrada, int ubicacionEntrada, String tipoEntrada){
            this.idEntrada = idEntrada;
            this.ubicacionEntrada = ubicacionEntrada;
            this.tipoEntrada = tipoEntrada;
            this.precioEntrada = PrecioEntrada();
            this.nombreCliente = null;
            this.entradaComprada = false;
            this.entradaReservada = false;
        }
        
        private int PrecioEntrada(){
            int precio = switch(tipoEntrada){
                case "general" -> 5000;
                case "estudiante" -> 4500;
                case "tercera edad" -> 4250;
                default -> 0;
            };
            return precio;
        }
    }
    
    public static class Cliente {
        int idCliente;
        String nombreCliente;

        public Cliente(int idCliente, String nombreCliente){
            this.idCliente = idCliente;
            this.nombreCliente = nombreCliente;
        }
        
        public String getNombre() {
            return nombreCliente;
        }
        public int getId() {
            return idCliente;
        }
    }
    
    public static void agregarCliente(List<Cliente> clientes) {
        System.out.println("---------------------------------------------");
        System.out.print("ingrese el nombre del cliente: ");
        String nombre = read.next();
        int id = clientes.size() + 1;
        clientes.add(new Cliente(id, nombre));
        System.out.println("cliente agregado con id: " + id);
        System.out.println("---------------------------------------------");
    }

    public static void reservarEntrada(List<Entrada> entradas, List<Cliente> clientes) {
        System.out.println("---------------------------------------------");
        if (clientes.isEmpty()) {
            System.out.println("no hay clientes registrados.");
            return;
        }

        if (entradas.size() >= 20) {
            System.out.println("todas las entradas ya han sido reservadas o compradas");
            return;
        }

        System.out.println("seleccione el id del cliente para reservar entrada:");
        System.out.println("-------------------");
        for (Cliente c : clientes) {
            System.out.println("id: " + c.getId() + ", nombre: " + c.getNombre());
        }
        System.out.println("-------------------");
        int idCliente = validarInt(1, clientes.size());
        Cliente cliente = clientes.get(idCliente - 1);
        

        System.out.println("asientos disponibles (1 al 20):");

        boolean[] ocupados = new boolean[21];
        for (Entrada e : entradas) {
            ocupados[e.ubicacionEntrada] = true;
        }

        for (int i = 1; i <= 20; i++) {
            if (!ocupados[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        System.out.println("ingrese ubicacion (numero de asiento del 1 al 20):");
        int ubicacion = validarInt(1, 20);

        if (ocupados[ubicacion]) {
            System.out.println("ese asiento ya esta reservado o comprado.");
            return;
        }

        System.out.println("Seleccione el tipo de entrada:");
        System.out.println("1. general ($5000)");
        System.out.println("2. estudiante ($4500)");
        System.out.println("3. tercera edad ($4250)");
        int tipoSeleccion = validarInt(1, 3);

        String tipo = switch (tipoSeleccion) {
            case 1 -> "general";
            case 2 -> "estudiante";
            case 3 -> "tercera edad";
            default -> "general";
        };

        int idEntrada = entradas.size() + 1;
        Entrada nuevaEntrada = new Entrada(idEntrada, ubicacion, tipo);
        nuevaEntrada.entradaReservada = true;
        nuevaEntrada.nombreCliente = cliente.getNombre();
        entradas.add(nuevaEntrada);

        System.out.println("entrada reservada exitosamente para " + cliente.getNombre() + " en el asiento " + ubicacion);
        System.out.println("---------------------------------------------");
    }

    public static void comprarEntrada(List<Entrada> entradas, List<Cliente> clientes) {
        System.out.println("---------------------------------------------");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("seleccione el id del cliente para comprar entrada:");
        for (Cliente c : clientes) {
            System.out.println("id: " + c.getId() + ", nombre: " + c.getNombre());
        }
        int idCliente = validarInt(1, clientes.size());
        Cliente cliente = clientes.get(idCliente - 1);

        boolean encontrado = false;
        for (Entrada e : entradas) {
            if (e.nombreCliente != null && e.nombreCliente.equals(cliente.getNombre())) {
                if (e.entradaComprada) {
                    System.out.println("la entrada ya fue comprada.");
                    return;
                }
                if (!e.entradaReservada) {
                    System.out.println("la entrada no esta reservada.");
                    return;
                }
                e.entradaComprada = true;
                totalVentas++;
                totalIngresos += e.precioEntrada;
                System.out.println("entrada comprada con exito para " + cliente.getNombre());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("no se encontro una entrada reservada para este cliente.");
        }
        System.out.println("---------------------------------------------");
    }
    
    public static void mostrarResumen(List<Entrada> entradas) {
        System.out.println("---------------------------------------------");
        System.out.println("resumen de ventas:");
        System.out.println("total entradas compradas: " + totalVentas);
        System.out.println("total ingresos: $" + totalIngresos);
        System.out.println("entradas reservadas (sin comprar):");
        for (Entrada e : entradas) {
            if (e.entradaReservada && !e.entradaComprada) {
                System.out.println("ID: " + e.idEntrada + ", Cliente: " + e.nombreCliente +
                        ", ubicacion: " + e.ubicacionEntrada + ", Tipo: " + e.tipoEntrada);
            }
        }
        System.out.println("---------------------------------------------");
    }
    
    public static void main(String[] args) {
        List<Entrada> entradas = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        
        boolean salir = false;
        int selector;
        
        while(!salir){
            System.out.println("sistema de venta de entradas Teatro Moro");
            System.out.println("seleccione una opcion");
            System.out.println("1. agregar cliente");
            System.out.println("2. reservar entrada");
            System.out.println("3. comprar entrada");
            System.out.println("4. resumen de venta");
            System.out.println("5. salir");
            
            selector = validarInt(1, 5);
            switch (selector) {
                case 1 -> agregarCliente(clientes);
                case 2 -> reservarEntrada(entradas, clientes);
                case 3 -> comprarEntrada(entradas, clientes);
                case 4 -> mostrarResumen(entradas);
                case 5 -> salir = true;
            }
        }
    }
}