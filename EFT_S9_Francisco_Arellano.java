package EFT_S9_Francisco_Arellano;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class EFT_S9_Francisco_Arellano {

    static Scanner read = new Scanner(System.in);

    static int totalVentas = 0;
    static int totalIngresos = 0;

    static boolean[] ocupadosVip = new boolean[5];
    static boolean[] ocupadosPalco = new boolean[10];
    static boolean[] ocupadosPlateaB = new boolean[15];
    static boolean[] ocupadosPlateaA = new boolean[15];
    static boolean[] ocupadosGaleria = new boolean[20];

    public static int validarInt(int min, int max) {
        boolean valido = false;
        int numero = 0;
        while (!valido) {
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

    public static class Entrada {

        private int idEntrada, precioEntrada, precioFinalEntrada, ubicacionEntrada;
        private String tipoEntrada;
        private float descuentoEntrada;
        private int idClienteDueño;
        private boolean entradaComprada;
        private boolean entradaReservada;

        public Entrada(int idEntrada, String tipoEntrada, int ubicacionEntrada,
                int idClienteDueño, Cliente clienteDueño) {
            this.idEntrada = idEntrada;
            this.tipoEntrada = tipoEntrada;
            this.ubicacionEntrada = ubicacionEntrada;
            this.idClienteDueño = idClienteDueño;
            this.precioEntrada = PrecioEntrada();
            this.descuentoEntrada = calcularDescuento(clienteDueño);
            this.precioFinalEntrada = (int) (precioEntrada * (1 - descuentoEntrada));
        }

        private int PrecioEntrada() {
            return switch (tipoEntrada) {
                case "vip" ->
                    20000;
                case "palco" ->
                    15000;
                case "platea baja" ->
                    10000;
                case "platea alta" ->
                    7000;
                case "galeria" ->
                    5000;
                default ->
                    0;
            };
        }

        private float calcularDescuento(Cliente cliente) {
            boolean esNino = cliente.getEdadCliente() < 12;
            boolean esMujer = cliente.getSexoCliente().equalsIgnoreCase("femenino");
            boolean esEstudiante = cliente.esEstudiante();
            boolean esTerceraEdad = cliente.getEdadCliente() > 65;

            if (esTerceraEdad) {
                return 0.25f;
            }
            if (esMujer) {
                return 0.20f;
            }
            if (esEstudiante) {
                return 0.15f;
            }
            if (esNino) {
                return 0.10f;
            }
            return 0f;
        }

        public int getIdEntrada() {
            return idEntrada;
        }

        public String getTipoEntrada() {
            return tipoEntrada;
        }

        public int getUbicacionEntrada() {
            return ubicacionEntrada;
        }

        public int getPrecioEntrada() {
            return precioEntrada;
        }

        public boolean isReservada() {
            return entradaReservada;
        }

        public boolean isComprada() {
            return entradaComprada;
        }

        public int getIdClienteDueño() {
            return idClienteDueño;
        }

        public int getPrecioFinalEntrada() {
            return precioFinalEntrada;
        }

        public void setReservada(boolean reservada) {
            this.entradaReservada = reservada;
        }

        public void setComprada(boolean comprada) {
            this.entradaComprada = comprada;
        }
    }

    public static class Cliente {

        private int idCliente;
        private int diaNacimiento, mesNacimiento, anoNacimiento, edadCliente;
        private String sexoCliente, nombreCliente;
        private boolean esEstudiante;

        public Cliente(int idCliente, String nombreCliente,
                int diaNacimiento, int mesNacimiento, int anoNacimiento,
                String sexoCliente,
                boolean esEstudiante) {
            this.idCliente = idCliente;
            this.nombreCliente = nombreCliente;
            this.diaNacimiento = diaNacimiento;
            this.mesNacimiento = mesNacimiento;
            this.anoNacimiento = anoNacimiento;
            this.sexoCliente = sexoCliente;
            this.esEstudiante = esEstudiante;
            this.edadCliente = calcularEdad();
        }

        private int calcularEdad() {
            LocalDate nacimiento = LocalDate.of(anoNacimiento, mesNacimiento, diaNacimiento);
            return Period.between(nacimiento, LocalDate.now()).getYears();
        }

        public int getId() {
            return idCliente;
        }

        public String getNombre() {
            return nombreCliente;
        }

        public int getEdadCliente() {
            return edadCliente;
        }

        public String getSexoCliente() {
            return sexoCliente;
        }

        public boolean esEstudiante() {
            return esEstudiante;
        }
    }

    public static void agregarCliente(List<Cliente> clientes) {
        System.out.println("---------------------------------------------");
        read.nextLine(); // limpiar buffer
        System.out.print("ingrese el nombre del cliente: ");
        String nombre = read.nextLine();

        System.out.print("ingrese el dia de nacimiento (1-31): ");
        int dia = validarInt(1, 31);
        System.out.print("ingrese el mes de nacimiento (1-12): ");
        int mes = validarInt(1, 12);
        System.out.print("Ingrese el año de nacimiento: ");
        int anio = validarInt(1900, LocalDate.now().getYear());

        System.out.println("Seleccione el sexo:");
        System.out.println("1. Masculino");
        System.out.println("2. Femenino");
        int opcionSexo = validarInt(1, 2);
        String sexo = (opcionSexo == 1) ? "masculino"
                : "femenino";

        System.out.print("Es estudiante (1 = si, 0 = no): ");
        boolean estudiante = validarInt(0, 1) == 1;

        int id = clientes.size() + 1;
        clientes.add(new Cliente(id, nombre, dia, mes, anio, sexo,
                estudiante));
        System.out.println("Cliente agregado con ID: " + id);
        System.out.println(
                "---------------------------------------------");
    }

    public static void reservarEntrada(List<Entrada> entradas, List<Cliente> clientes) {
        System.out.println("---------------------------------------------");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("Seleccione el cliente:");
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId() + " - " + c.getNombre());
        }
        int idCliente = validarInt(1, clientes.size());
        Cliente cliente = clientes.get(idCliente - 1);

        System.out.println("Seleccione el tipo de asiento:");
        System.out.println("1. VIP");
        System.out.println("2. Palco");
        System.out.println("3. Platea Baja");
        System.out.println("4. Platea Alta");
        System.out.println("5. Galería");

        int tipo = validarInt(1, 5);
        String tipoTexto = switch (tipo) {
            case 1 ->
                "vip";
            case 2 ->
                "palco";
            case 3 ->
                "platea baja";
            case 4 ->
                "platea alta";
            case 5 ->
                "galeria";
            default ->
                "";
        };

        boolean[] ocupados = switch (tipo) {
            case 1 ->
                ocupadosVip;
            case 2 ->
                ocupadosPalco;
            case 3 ->
                ocupadosPlateaB;
            case 4 ->
                ocupadosPlateaA;
            case 5 ->
                ocupadosGaleria;
            default ->
                null;
        };

        if (ocupados == null) {
            return;
        }

        System.out.print("Asientos disponibles en " + tipoTexto + ": ");
        boolean algunoLibre = false;
        for (int i = 0; i < ocupados.length; i++) {
            if (!ocupados[i]) {
                System.out.print((i + 1) + " ");
                algunoLibre = true;
            }
        }
        System.out.println();

        if (!algunoLibre) {
            System.out.println("No hay asientos disponibles en esta categoria.");
            return;
        }

        System.out.println("Seleccione el numero de asiento:");
        int asiento = validarInt(1, ocupados.length);

        if (ocupados[asiento - 1]) {
            System.out.println("Ese asiento ya esta ocupado.");
            return;
        }

        int idEntrada = entradas.size() + 1;
        Entrada nuevaEntrada = new Entrada(idEntrada, tipoTexto, asiento, cliente.getId(), cliente);
        nuevaEntrada.setReservada(true);
        entradas.add(nuevaEntrada);
        ocupados[asiento - 1] = true;

        System.out.println("Reserva exitosa para " + cliente.getNombre() + " en " + tipoTexto + ", asiento " + asiento);
    System.out.println("---------------------------------------------");
    }

    public static void comprarEntrada(List<Entrada> entradas, List<Cliente> clientes) {
        System.out.println("---------------------------------------------");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("Seleccione el ID del cliente:");
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId()
                    + ", Nombre: " + c.getNombre());
        }
        int idCliente = validarInt(1, clientes.size());
        Cliente cliente = clientes.get(idCliente - 1);

        List<Entrada> reservadasDelCliente = new ArrayList<>();
        for (Entrada e : entradas) {
            if (e.isReservada() && !e.isComprada()
                    && e.getIdClienteDueño() == cliente.getId()) {
                reservadasDelCliente.add(e);
            }
        }

        if (reservadasDelCliente.isEmpty()) {
            System.out.println(
                    "Este cliente no tiene entradas reservadas.");
            return;
        }

        System.out.println("Entradas reservadas:");
        for (int i = 0; i < reservadasDelCliente.size(); i++) {
            Entrada e = reservadasDelCliente.get(i);
            System.out.println((i + 1)
                    + ". ID Entrada: " + e.getIdEntrada()
                    + ", Tipo: " + e.getTipoEntrada()
                    + ", Asiento: " + e.getUbicacionEntrada()
                    + ", Precio: $" + e.getPrecioFinalEntrada());
        }

        System.out.println(
                "Seleccione el numero de la entrada que desea comprar:");
        int seleccion = validarInt(1, reservadasDelCliente.size());
        Entrada entradaSeleccionada
                = reservadasDelCliente.get(seleccion - 1);

        entradaSeleccionada.setComprada(true);
        totalVentas++;
        totalIngresos += entradaSeleccionada.getPrecioFinalEntrada();

        System.out.println(
                "Compra realizada con exito para " + cliente.getNombre()
                + " en asiento " + entradaSeleccionada.getUbicacionEntrada()
                + " (" + entradaSeleccionada.getTipoEntrada() + ")");
    System.out.println("---------------------------------------------");
    }

    public static void mostrarResumen(List<Entrada> entradas) {
        System.out.println("---------------------------------------------");
        System.out.println("Resumen de Ventas");
        System.out.println("Total entradas compradas: " + totalVentas);
        System.out.println("Total ingresos: $" + totalIngresos);
        System.out.println("Reservas pendientes:");

        for (Entrada e : entradas) {
            if (e.isReservada() && !e.isComprada()) {
                System.out.println(
                        "ID Entrada: " + e.getIdEntrada() + ", Cliente ID: "
                        + e.getIdClienteDueño() + ", Tipo: " + e.getTipoEntrada()
                        + ", Asiento: " + e.getUbicacionEntrada());
            }
        }
        System.out.println("---------------------------------------------");
    }

    public static void main(String[] args) {
        List<Entrada> entradas = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        boolean salir = false;

        while (!salir) {
            System.out.println(
                    "Sistema de Venta de Entradas - Teatro Moro");
            System.out.println("Seleccione una opcion:");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Reservar entrada");
            System.out.println("3. Comprar entrada");
            System.out.println("4. Mostrar resumen");
            System.out.println("5. Salir");

            int opcion = validarInt(1, 5);
            switch (opcion) {
                case 1 ->
                    agregarCliente(clientes);
                case 2 ->
                    reservarEntrada(entradas, clientes);
                case 3 ->
                    comprarEntrada(entradas, clientes);
                case 4 ->
                    mostrarResumen(entradas);
                case 5 ->
                    salir = true;
            }
        }
    }
}
