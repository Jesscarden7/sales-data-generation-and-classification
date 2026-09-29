package com.proyecto.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Segunda clase principal del proyecto.
 * Lee los archivos de entrada generados en la raíz, procesa los totales de ventas
 * y genera los reportes de vendedores y productos ordenados de mayor a menor.
 * 
 * @author Equipo de Trabajo #6 - Conceptos Fundamentales de Programación
 */
public class main {

    public static final int MAX_PRODUCTS = 100;
    public static final int MAX_SALESMEN = 100;

    // Arreglos para almacenar datos de productos
    public static String[] productIds = new String[MAX_PRODUCTS];
    public static String[] productNames = new String[MAX_PRODUCTS];
    public static int[] productPrices = new int[MAX_PRODUCTS];
    public static int[] productQuantities = new int[MAX_PRODUCTS];
    public static int productCount = 0;

    // Arreglos para almacenar datos de vendedores
    public static String[] salesmanDocumentTypes = new String[MAX_SALESMEN];
    public static long[] salesmanIds = new long[MAX_SALESMEN];
    public static String[] salesmanNames = new String[MAX_SALESMEN];
    public static String[] salesmanLastNames = new String[MAX_SALESMEN];
    public static long[] salesmanMoney = new long[MAX_SALESMEN];
    public static int salesmanCount = 0;

    /**
     * Punto de entrada principal para el procesamiento y generación de reportes.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando procesamiento de archivos para reporte...");

            readProductsFile();
            readSalesmenFile();
            readSalesFiles();

            sortSalesmen();
            sortProducts();

            createSalesmenReport();
            createProductsReport();

            System.out.println("\n¡PROCESAMIENTO FINALIZADO CON ÉXITO!");
            System.out.println("Archivos 'reporte_vendedores.csv' y 'reporte_productos.csv' generados en la raíz.");

        } catch (Exception exception) {
            System.err.println("Ocurrió un error al procesar los archivos: " + exception.getMessage());
        }
    }

    /**
     * Lee el archivo productos.csv.
     * 
     * @throws FileNotFoundException si el archivo productos.csv no existe
     */
    public static void readProductsFile() throws FileNotFoundException {
        File file = new File("productos.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("No se encontró el archivo productos.csv.");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] data = line.split(";");
                    if (data.length < 3) continue;

                    productIds[productCount] = data[0];
                    productNames[productCount] = data[1];
                    productPrices[productCount] = Integer.parseInt(data[2]);
                    productQuantities[productCount] = 0;
                    productCount++;
                }
            }
        }
    }

    /**
     * Lee el archivo vendedores.csv.
     * 
     * @throws FileNotFoundException si el archivo vendedores.csv no existe
     */
    public static void readSalesmenFile() throws FileNotFoundException {
        File file = new File("vendedores.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("No se encontró el archivo vendedores.csv.");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] data = line.split(";");
                    if (data.length < 4) continue;

                    salesmanDocumentTypes[salesmanCount] = data[0];
                    salesmanIds[salesmanCount] = Long.parseLong(data[1]);
                    salesmanNames[salesmanCount] = data[2];
                    salesmanLastNames[salesmanCount] = data[3];
                    salesmanMoney[salesmanCount] = 0;
                    salesmanCount++;
                }
            }
        }
    }

    /**
     * Busca y procesa todos los archivos de ventas(prefijo 'ventas_' y extensión '.csv').
     * 
     * @throws FileNotFoundException si ocurre un error al abrir un archivo
     */
    public static void readSalesFiles() throws FileNotFoundException {
        File folder = new File(".");
        File[] files = folder.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("No se pudo acceder al directorio raíz.");
        }

        int salesFilesCount = 0;
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile() && fileName.startsWith("ventas_") && fileName.endsWith(".csv")) {
                readOneSalesFile(file);
                salesFilesCount++;
            }
        }

        if (salesFilesCount == 0) {
            throw new IllegalArgumentException("No se encontraron archivos de ventas con prefijo 'ventas_'.");
        }
    }

    /**
     * Procesa un archivo individual de ventas y acumula montos y cantidades.
     * 
     * @param salesFile archivo de ventas a procesar
     * @throws FileNotFoundException si no se puede leer el archivo
     */
    public static void readOneSalesFile(File salesFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(salesFile)) {
            if (!scanner.hasNextLine()) return;

            String firstLine = scanner.nextLine().trim();
            String[] salesmanData = firstLine.split(";");
            if (salesmanData.length < 2) return;

            long salesmanId = Long.parseLong(salesmanData[1]);
            int salesmanPosition = findSalesman(salesmanId);
            if (salesmanPosition == -1) return;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] saleData = line.split(";");
                    if (saleData.length < 2) continue;

                    String productId = saleData[0];
                    int quantity = Integer.parseInt(saleData[1]);

                    int productPosition = findProduct(productId);
                    if (productPosition != -1 && quantity > 0) {
                        productQuantities[productPosition] += quantity;
                        salesmanMoney[salesmanPosition] += (long) productPrices[productPosition] * quantity;
                    }
                }
            }
        }
    }

    /**
     * Busca la posición de un producto en los arreglos por su identificador.
     * 
     * @param productId identificador único del producto
     * @return índice del producto o -1 si no fue encontrado
     */
    public static int findProduct(String productId) {
        for (int i = 0; i < productCount; i++) {
            if (productIds[i].equals(productId)) return i;
        }
        return -1;
    }

    /**
     * Busca la posición de un vendedor en los arreglos por su número de documento.
     * 
     * @param salesmanId número de documento del vendedor
     * @return índice del vendedor o -1 si no fue encontrado
     */
    public static int findSalesman(long salesmanId) {
        for (int i = 0; i < salesmanCount; i++) {
            if (salesmanIds[i] == salesmanId) return i;
        }
        return -1;
    }

    /**
     * Ordena los vendedores de mayor a menor recaudo acumulado.
     */
    public static void sortSalesmen() {
        for (int i = 0; i < salesmanCount - 1; i++) {
            for (int j = 0; j < salesmanCount - 1 - i; j++) {
                if (salesmanMoney[j] < salesmanMoney[j + 1]) {
                    long tempMoney = salesmanMoney[j];
                    salesmanMoney[j] = salesmanMoney[j + 1];
                    salesmanMoney[j + 1] = tempMoney;

                    long tempId = salesmanIds[j];
                    salesmanIds[j] = salesmanIds[j + 1];
                    salesmanIds[j + 1] = tempId;

                    String tempType = salesmanDocumentTypes[j];
                    salesmanDocumentTypes[j] = salesmanDocumentTypes[j + 1];
                    salesmanDocumentTypes[j + 1] = tempType;

                    String tempName = salesmanNames[j];
                    salesmanNames[j] = salesmanNames[j + 1];
                    salesmanNames[j + 1] = tempName;

                    String tempLastName = salesmanLastNames[j];
                    salesmanLastNames[j] = salesmanLastNames[j + 1];
                    salesmanLastNames[j + 1] = tempLastName;
                }
            }
        }
    }

    /**
     * Ordena los productos de mayor a menor cantidad vendida.
     */
    public static void sortProducts() {
        for (int i = 0; i < productCount - 1; i++) {
            for (int j = 0; j < productCount - 1 - i; j++) {
                if (productQuantities[j] < productQuantities[j + 1]) {
                    int tempQty = productQuantities[j];
                    productQuantities[j] = productQuantities[j + 1];
                    productQuantities[j + 1] = tempQty;

                    String tempId = productIds[j];
                    productIds[j] = productIds[j + 1];
                    productIds[j + 1] = tempId;

                    String tempName = productNames[j];
                    productNames[j] = productNames[j + 1];
                    productNames[j + 1] = tempName;

                    int tempPrice = productPrices[j];
                    productPrices[j] = productPrices[j + 1];
                    productPrices[j + 1] = tempPrice;
                }
            }
        }
    }

    /**
     * Genera el archivo reporte_vendedores.csv.
     * 
     * @throws FileNotFoundException si no se puede crear el archivo
     */
    public static void createSalesmenReport() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("reporte_vendedores.csv"))) {
            for (int i = 0; i < salesmanCount; i++) {
                writer.println(salesmanNames[i] + " " + salesmanLastNames[i] + ";" + salesmanMoney[i]);
            }
        }
    }

    /**
     * Genera el archivo reporte_productos.csv.
     * 
     * @throws FileNotFoundException si no se puede crear el archivo
     */
    public static void createProductsReport() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("reporte_productos.csv"))) {
            for (int i = 0; i < productCount; i++) {
                if (productQuantities[i] > 0) {
                    writer.println(productNames[i] + ";" + productPrices[i]);
                }
            }
        }
    }
}
