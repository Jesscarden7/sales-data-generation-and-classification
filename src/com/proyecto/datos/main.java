package com.proyecto.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Segunda clase principal ejecutable del proyecto.
 * Lee los archivos planos de prueba desde la raíz, procesa y acumula 
 * los totales de ventas utilizando listas dinámicas (ArrayList), ordena los datos 
 * de mayor a menor y genera los reportes de ventas finales.
 * 
 * @author Equipo de Trabajo #6 - Conceptos Fundamentales de Programación
 */
public class main {

    /**
     * Clase interna para representar los datos acumulados de un producto.
     */
    static class ProductItem {
        String id;
        String name;
        int price;
        int quantitySold;

        public ProductItem(String id, String name, int price) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantitySold = 0;
        }
    }

    /**
     */
    static class SalesmanItem {
        String documentType;
        long documentNumber;
        String name;
        String lastName;
        long totalMoney;

        public SalesmanItem(String documentType, long documentNumber, String name, String lastName) {
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.name = name;
            this.lastName = lastName;
            this.totalMoney = 0;
        }
    }

    // Colecciones dinámicas para almacenamiento sin límites de capacidad
    private static List<ProductItem> products = new ArrayList<>();
    private static List<SalesmanItem> salesmen = new ArrayList<>();

    /**
     * Punto de entrada principal para el procesamiento de archivos y generación de reportes.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando procesamiento de archivos para reporte...");

            // 1. Cargar datos de productos y vendedores
            readProductsFile();
            readSalesmenFile();

            // 2. Procesar todos los archivos individuales de ventas
            
            readSalesFiles();

            // 3. Ordenar listas dinámicas de mayor a menor
            sortSalesmen();
            sortProducts();

            // 4. Generar archivos de reporte CSV
            createSalesmenReport();
            createProductsReport();

            System.out.println("\n¡PROCESAMIENTO Y GENERACIÓN DE REPORTES FINALIZADOS CON ÉXITO!");
            System.out.println("Archivos 'reporte_vendedores.csv' y 'reporte_productos.csv' creados.");

        } catch (Exception exception) {
            System.err.println("Ocurrió un error en el procesamiento de datos: " + exception.getMessage());
        }
    }

    /**
     * Lee el archivo 'productos.csv' y llena la lista dinámica de productos.
     * 
     * @throws FileNotFoundException si el archivo no existe
     */
    public static void readProductsFile() throws FileNotFoundException {
        File file = new File("productos.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("No se encontró el archivo 'productos.csv'.");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(";");
                if (data.length >= 3) {
                    try {
                    	String id = data[0].trim();
                    	String name = data[1].trim();
                    	int price = Integer.parseInt(data[2].trim());
                    	if (price <= 0) {
                    	    System.err.println("Advertencia: Producto con precio no válido omitido en productos.csv: " + line);
                    	    continue;
                    	}
                    	products.add(new ProductItem(id, name, price));
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: Línea con precio inválido omitida en productos.csv: " + line);
                    }
                }
            }
        }
        System.out.println("Cargados " + products.size() + " productos desde 'productos.csv'.");
    }

    /**
     * Lee el archivo 'vendedores.csv' y llena la lista dinámica de vendedores.
     * 
     * @throws FileNotFoundException si el archivo no existe
     */
    public static void readSalesmenFile() throws FileNotFoundException {
        File file = new File("vendedores.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("No se encontró el archivo 'vendedores.csv'.");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(";");
                if (data.length >= 4) {
                    try {
                        String docType = data[0].trim();
                        long docNum = Long.parseLong(data[1].trim());
                        String name = data[2].trim();
                        String lastName = data[3].trim();
                        salesmen.add(new SalesmanItem(docType, docNum, name, lastName));
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: Línea con documento inválido omitida en vendedores.csv: " + line);
                    }
                }
            }
        }
        System.out.println("Cargados " + salesmen.size() + " vendedores desde 'vendedores.csv'.");
    }

    /**
     * Busca y procesa todos los archivos de ventas(prefijo 'ventas_' y extensión '.csv').
     * 
     * @throws FileNotFoundException si no se puede acceder a la carpeta
     */
    public static void readSalesFiles() throws FileNotFoundException {
        File folder = new File(".");
        File[] files = folder.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("No se pudo acceder al directorio raíz del proyecto.");
        }

        int processedFilesCount = 0;
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile() && fileName.startsWith("ventas_") && fileName.endsWith(".csv")) {
                readOneSalesFile(file);
                processedFilesCount++;
            }
        }

        if (processedFilesCount == 0) {
            throw new IllegalArgumentException("No se encontraron archivos de ventas con el prefijo 'ventas_'.");
        }
        System.out.println("Procesados " + processedFilesCount + " archivos de ventas individuales.");
    }

    /**
     * Lee un archivo individual de ventas y acumula montos y cantidades vendidas.
     * 
     * @param salesFile archivo individual de ventas a procesar
     */
    public static void readOneSalesFile(File salesFile) {
        try (Scanner scanner = new Scanner(salesFile)) {
            if (!scanner.hasNextLine()) return;

            // Encabezado del vendedor: TipoDocumento;NúmeroDocumento
            String firstLine = scanner.nextLine().trim();
            String[] salesmanHeader = firstLine.split(";");
            if (salesmanHeader.length < 2) return;

            long salesmanId;
            try {
                salesmanId = Long.parseLong(salesmanHeader[1].trim());
            } catch (NumberFormatException e) {
                return;
            }

            SalesmanItem salesman = findSalesmanById(salesmanId);
            if (salesman == null) return;

            // Transacciones de productos
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] saleData = line.split(";");
                if (saleData.length >= 2) {
                    try {
                        String productId = saleData[0].trim();
                        int quantity = Integer.parseInt(saleData[1].trim());

                        ProductItem product = findProductById(productId);
                        if (product != null && quantity > 0) {
                            product.quantitySold += quantity;
                            salesman.totalMoney += (long) product.price * quantity;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar línea con formato de cantidad inválida
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando el archivo de ventas " + salesFile.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Busca un producto en la lista dinámica por su identificador.
     * 
     * @param id identificador del producto (ej: P1)
     * @return objeto ProductItem o null si no existe
     */
    public static ProductItem findProductById(String id) {
        for (ProductItem p : products) {
            if (p.id.equals(id)) return p;
        }
        return null;
    }

    /**
     * Busca un vendedor en la lista dinámica por su número de documento.
     * 
     * @param id número de documento del vendedor
     * @return objeto SalesmanItem o null si no existe
     */
    public static SalesmanItem findSalesmanById(long id) {
        for (SalesmanItem s : salesmen) {
            if (s.documentNumber == id) return s;
        }
        return null;
    }

    /**
     * Ordena la lista de vendedores de mayor a menor recaudo acumulado.
     */
    public static void sortSalesmen() {
        Collections.sort(salesmen, new Comparator<SalesmanItem>() {
            @Override
            public int compare(SalesmanItem s1, SalesmanItem s2) {
                return Long.compare(s2.totalMoney, s1.totalMoney);
            }
        });
    }

    /**
     * Ordena la lista de productos de mayor a menor cantidad total vendida.
     */
    public static void sortProducts() {
        Collections.sort(products, new Comparator<ProductItem>() {
            @Override
            public int compare(ProductItem p1, ProductItem p2) {
                return Integer.compare(p2.quantitySold, p1.quantitySold);
            }
        });
    }

    /**
     * Genera el archivo 'reporte_vendedores.csv' ordenado descendentemente.
     * 
     * @throws FileNotFoundException si no se puede crear el archivo
     */
    public static void createSalesmenReport() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("reporte_vendedores.csv"))) {
            for (SalesmanItem s : salesmen) {
                writer.println(s.name + " " + s.lastName + ";" + s.totalMoney);
            }
        }
    }

    /**
     * Genera el archivo 'reporte_productos.csv' ordenado descendentemente.
     * 
     * @throws FileNotFoundException si no se puede crear el archivo
     */
    public static void createProductsReport() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("reporte_productos.csv"))) {
            for (ProductItem p : products) {
                if (p.quantitySold > 0) {
                    writer.println(p.name + ";" + p.price);
                }
            }
        }
    }
}
