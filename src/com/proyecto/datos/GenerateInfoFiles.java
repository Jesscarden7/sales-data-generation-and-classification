package com.proyecto.datos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase encargada de la generación pseudoaleatoria de archivos planos de entrada.
 * 
 * @author Equipo de Trabajo #6 - Conceptos Fundamentales de Programación
 */
public class GenerateInfoFiles {

    /**
     * Punto de entrada principal para ejecutar la generación de todos los archivos de prueba.
     * 
     */
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando la generación de archivos de prueba...");

            // 1. Generar archivo de productos
            createProductsFile(10);
            System.out.println("Archivo 'productos.csv' creado exitosamente.");

            // 2. Generar archivo con información general de vendedores
            List<Salesman> salesmen = createSalesManInfoFile(5);
            System.out.println("Archivo 'vendedores.csv' creado exitosamente.");

            // 3. Generar un archivo de ventas por cada vendedor de la lista
            Random random = new Random();
            for (Salesman salesman : salesmen) {
                int randomSalesCount = 5 + random.nextInt(11);
                String fileName = salesman.getFirstName() + "_" + salesman.getLastName();
                createSalesMenFile(randomSalesCount, fileName, salesman.getDocumentNumber());
                System.out.println("Archivo de ventas de '" + fileName + "' creado exitosamente.");
            }

            System.out.println("\n¡TODOS LOS ARCHIVOS SE GENERARON CON ÉXITO!");

        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado en la generación de datos: " + e.getMessage());
        }
    }

    /**
     * Crea un archivo con información pseudoaleatoria de productos.
     * Formato: IDProducto;NombreProducto;PrecioPorUnidadProducto
     * 
     * @param productsCount cantidad de productos pseudoaleatorios a generar
     */
    public static void createProductsFile(int productsCount) {
        String[] productNames = { 
            "Café Especial Andino", "Café Tradición Colombiana", "Cacao Premium", "Azúcar Orgánica", "Galletas de Café", 
            "Chocolate de Mesa", "Miel de Abejas", "Panela Fraccionada", "Mermelada Artesanal", "Té de Frutos Rojos" 
        };
        Random random = new Random();

        try (FileWriter writer = new FileWriter("productos.csv")) {
            for (int i = 0; i < productsCount; i++) {
                int id = i + 1;
                String name = productNames[i % productNames.length];
                int price = 10000 + random.nextInt(40000);

                writer.write("P" + id + ";" + name + ";" + price + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de productos: " + e.getMessage());
        }
    }

    /**
     * Crea un archivo con información de vendedores generada de manera pseudoaleatoria y coherente.
     * Formato: TipoDocumento;NúmeroDocumento;NombresVendedor;ApellidosVendedor
     * 
     * @param salesmanCount cantidad de vendedores pseudoaleatorios a generar
     * @return la lista de vendedores generados para mantener coherencia
     */
    public static List<Salesman> createSalesManInfoFile(int salesmanCount) {
        String[] documentTypes = { "CC"};
        String[] firstNames = { "Carlos", "Laura", "Andrea", "Miguel", "Daniel", "Natalia", "Juan", "Paula" };
        String[] lastNames = { "Rodriguez", "Gomez", "Martinez", "Torres", "Ramirez", "Hernandez", "Castro" };
        Random random = new Random();

        List<Salesman> salesmen = new ArrayList<>();
        try (FileWriter writer = new FileWriter("vendedores.csv")) {
            for (int i = 0; i < salesmanCount; i++) {
                String documentType = documentTypes[random.nextInt(documentTypes.length)];
                long documentNumber = 10000000L + random.nextInt(90000000);
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];

                writer.write(documentType + ";" + documentNumber + ";" + firstName + ";" + lastName + "\n");
                
                salesmen.add(new Salesman(documentType, documentNumber, firstName, lastName));
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de vendedores: " + e.getMessage());
        }
        return salesmen;
    }

    /**
     * Crea un archivo pseudoaleatorio de ventas de un vendedor específico.
     * Formato:
     * TipoDocumentoVendedor;NúmeroDocumentoVendedor
     * IDProducto;CantidadProductoVendido;
     * 
     * @param randomSalesCount cantidad de ventas pseudoaleatorias a generar
     * @param name             nombre del vendedor
     * @param id               número de documento del vendedor
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        Random random = new Random();
        String nameWithoutSpaces = name.replace(" ", "_");
        String fileName = "ventas_" + nameWithoutSpaces + "_" + id + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("CC;" + id + "\n");

            for (int i = 0; i < randomSalesCount; i++) {
                int productIdNumber = 1 + random.nextInt(10);
                String productId = "P" + productIdNumber;
                int quantity = 1 + random.nextInt(10);

                writer.write(productId + ";" + quantity + ";\n");
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de ventas de " + name + ": " + e.getMessage());
        }
    }
}
