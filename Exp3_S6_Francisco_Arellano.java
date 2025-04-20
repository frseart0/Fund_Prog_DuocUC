package Exp3_S6_Francisco_Arellano;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Exp3_S6_Francisco_Arellano {
    
    static Scanner read = new Scanner(System.in);
    
    static int totalVentas = 0;
    static int totalIngresos = 0;
    static int asientosSinOcupar = 20;
    
    // Funcion para validar entradas numero entero
    public static int validarInt(int min, int max) {
        boolean valido = false;
        int numero = 0;
        while(valido==false){
            System.out.print("Ingresar->");
            try {
                numero = read.nextInt();
                if (numero >= min && numero <= max) {
                    valido = true;
                } else {
                    System.out.println("Entrada invalida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida");
                read.nextLine(); 
            }
        }
        return numero;
    }
    
    public static void main(String[] args) {
        
        boolean salir = false;
        int selector1;
        int selector2;
        
        boolean[] asientoUsado = new boolean[20];
        int[] tipoEntrada = new int[20];
        int[] precioEntrada = new int[20];
        boolean[] entradaComprada = new boolean[20];
        int ubicacionEntrada;
        int totalVentaActual = 0;
        int modificarAsiento;
        int nuevoAsiento;
        
        for(int i=0;i<=19;i++){
            asientoUsado[i] = false;
        }

        System.out.println("Sistema de reserva y venta de entradas");
        
        while(!salir){
            System.out.println("Seleccione funcion:");
            System.out.println("1.Reservar entrada");
            System.out.println("2.Comprar entrada");
            System.out.println("3.Modificar venta existente");
            System.out.println("4.Imprimir Boleta");
            System.out.println("5.Salir");
            
            selector1 = validarInt(1,5);
            if(selector1==1){
                
                System.out.println("---Reservar entrada---");
                System.out.println("Ingresar ubicacion de asiento");
                System.out.println("Asientos Disponibles:");
                for(int i=0;i<=19;i++){
                    if( asientoUsado[i]==false){
                        System.out.print(i+1 + ", ");
                    }
                }
                System.out.println();
                    
                ubicacionEntrada = validarInt(1,20);
                    if(asientoUsado[ubicacionEntrada-1]==true){
                        System.out.println("Asiento ocupado, seleccione otro");
                        while(asientoUsado[ubicacionEntrada-1]==true){
                            ubicacionEntrada = validarInt(1,20);
                        }
                    }
                asientoUsado[ubicacionEntrada-1] = true;
                
                System.out.println("Ingresar tipo de entrada");
                System.out.println("1. General");
                System.out.println("2. Estudiante");
                
                tipoEntrada[ubicacionEntrada-1] = validarInt(1,2);
                
                if(tipoEntrada[ubicacionEntrada-1]==1){
                    precioEntrada[ubicacionEntrada-1] = 10000;
                }else if(tipoEntrada[ubicacionEntrada-1]==2){
                    precioEntrada[ubicacionEntrada-1] = 6000;
                }
                System.out.println("----------------------------------------------------");
            }else if(selector1==2){
                
                System.out.println("---Comprar entrada---");
                
                System.out.println("Resumen reserva");
                for(int i=0;i<=19;i++){
                    if(asientoUsado[i]==true && entradaComprada[i]==false){
                        System.out.println("numero de asiento:" + (i+1));
                        if(tipoEntrada[i]==1){
                            System.out.println("valor de entrada: 10000$");
                        }else if(tipoEntrada[i]==2){
                            System.out.println("valor de entrada: 6000$");
                        }
                        totalVentaActual += precioEntrada[i];
                    }
                }
                
                System.out.println("¿Confirmar venta?");
                System.out.println("1. Si");
                System.out.println("2. No");
                selector2 = validarInt(1,2);
                
                if(selector2==1){
                    for(int i=0;i<=19;i++){
                        if(asientoUsado[i]==true&&entradaComprada[i]==false){
                            entradaComprada[i]=true;
                            totalVentas++;
                        }
                    }
                    totalIngresos += totalVentaActual;
                }
                System.out.println("----------------------------------------------------");
            }else if(selector1==3){
                
                System.out.println("---Modificar Compra---");
                
                System.out.println("selecciona numero de asiento a modificar");
                for(int i=0;i<=19;i++){
                    if(entradaComprada[i]==true){
                        System.out.print("numero asiento: " + (i+1) + ", tipo de entrada: ");
                        if(tipoEntrada[i]==1){
                            System.out.println("general");
                        }else if(tipoEntrada[i]==2){
                            System.out.println("estudiante");
                        }
                    }
                }
                
                modificarAsiento = validarInt(1,20);
                while(entradaComprada[modificarAsiento-1]==false){
                    System.out.println("Asiento invalido, seleccione un asiento de la lista");
                    modificarAsiento = validarInt(1,20);
                }
                
                System.out.println("Cambiar ubicacion asiento:");
                nuevoAsiento = validarInt(1,20);
                while(asientoUsado[nuevoAsiento-1]==true){
                    System.out.println("Asiento invalido");
                    nuevoAsiento = validarInt(1,20);
                }
                asientoUsado[modificarAsiento-1] = false;
                entradaComprada[modificarAsiento-1] = false;
                asientoUsado[nuevoAsiento-1] = true;
                entradaComprada[nuevoAsiento-1] = true;
                if(tipoEntrada[modificarAsiento-1]==1){
                    tipoEntrada[nuevoAsiento-1]=1;
                }else if(tipoEntrada[modificarAsiento-1]==2){
                    tipoEntrada[nuevoAsiento-1]=2;
                }
                
                System.out.println("----------------------------------------------------");
            }else if(selector1==4){
                
                System.out.println("---Imprimir Boleta---");
                
                System.out.println("----------------------------------------------------");
                System.out.println("Boleta Compra Teatro Moro");
                for(int i=0;i<=19;i++){
                    if(entradaComprada[i]==true){
                        System.out.print("numero asiento: " + (i+1) + ", tipo de entrada: ");
                        if(tipoEntrada[i]==1){
                            System.out.println("general, precio: 10000$");
                        }else if(tipoEntrada[i]==2){
                            System.out.println("estudiante precio: 6000$");
                        }
                    }
                }
                System.out.println("Total: " + totalVentaActual + "$");
                System.out.println("----------------------------------------------------");
                    
            }else if(selector1==5){
                
                System.out.println("----------------------------------------------------");
                salir=true;
                System.out.println("Total Ventas:" + totalVentas);
                System.out.println("Total Ingresos:" + totalIngresos + "$");
                
                System.out.println("----------------------------------------------------");
            }
        }
    }
}