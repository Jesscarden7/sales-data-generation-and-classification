package com.proyecto.datos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase encargada de la generación pseudoaleatoria de archivos planos de entrada.
 */
public class GenerateInfoFiles {

	public static void main(String[] args) {
		try {
			System.out.println("Iniciando la generación de archivos de prueba...");

			// 1. Generar archivo de productos (ej. 10 productos)
			createProductsFile(10);
			System.out.println("Archivo 'products.txt' creado exitosamente.");

			// 2. Generar archivo con información general de vendedores y capturar la lista generada
			List<Salesman> salesmen = createSalesManInfoFile(5);
			System.out.println("Archivo 'salesmen_info.txt' creado exitosamente.");

			// 3. Generar un archivo de ventas por cada vendedor de la lista
			Random random = new Random();
			for (Salesman salesman : salesmen) {
				int randomSalesCount = 5 + random.nextInt(16); // entre 5 y 20 ventas
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
		String[] productNames = { "Café Especial Andino", "Café Tradición Colombiana", "Cacao Premium", "Azúcar Orgánica", "Galletas de Café" };
		Random random = new Random();

		try (FileWriter writer = new FileWriter("products.txt")) {
			for (int i = 0; i < productsCount; i++) {
				int id = i + 1;
				String name = productNames[random.nextInt(productNames.length)];
				int price = 15000 + random.nextInt(35000);

				writer.write(id + ";" + name + ";" + price + "\n");
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
	 * @return la lista de vendedores generados, para que main() pueda usarlos luego
	 */
	public static List<Salesman> createSalesManInfoFile(int salesmanCount) {
		String[] documentTypes = { "CC" };
		String[] firstNames = { "Juan", "Maria", "Carlos", "Ana", "Luis", "Laura", "Andres", "Sofia" };
		String[] lastNames = { "Gomez", "Rodriguez", "Perez", "Garcia", "Martinez", "Lopez", "Hernandez" };
		Random random = new Random();

		List<Salesman> salesmen = new ArrayList<>();

		try (FileWriter writer = new FileWriter("salesmen_info.txt")) {
			for (int i = 0; i < salesmanCount; i++) {
				String documentType = documentTypes[random.nextInt(documentTypes.length)];
				long documentNumber = 10000000L + random.nextInt(90000000);
				String firstName = firstNames[random.nextInt(firstNames.length)];
				String lastName = lastNames[random.nextInt(lastNames.length)];

				writer.write(documentType + ";" + documentNumber + ";" + firstName + ";" + lastName + "\n");

				salesmen.add(new Salesman(documentType, documentNumber, firstName, lastName));
			}
		} catch (IOException e) {
			System.out.println("Error al crear el archivo de información de vendedores: " + e.getMessage());
		}

		return salesmen;
	}

	/**
	 * Crea un archivo pseudoaleatorio de ventas de un vendedor específico.
	 * Formato:
	 * TipoDocumentoVendedor;NúmeroDocumentoVendedor
	 * IDProducto1;CantidadProducto1Vendido;
	 * IDProducto2;CantidadProducto2Vendido;
	 *
	 * @param randomSalesCount cantidad de ventas pseudoaleatorias a generar para el vendedor
	 * @param name             nombre del vendedor
	 * @param id               identificador del vendedor
	 */
	public static void createSalesMenFile(int randomSalesCount, String name, long id) {
		Random random = new Random();
		String fileName = "Sales_" + name + "_" + id + ".txt";

		try (FileWriter writer = new FileWriter(fileName)) {
			// Primera línea: tipo de documento y número de documento
			writer.write("CC;" + id + "\n");

			// Líneas siguientes: cada venta de un producto aleatorio
			for (int i = 0; i < randomSalesCount; i++) {
				int productId = 1 + random.nextInt(10); // ID de producto entre 1 y 10
				int quantity = 1 + random.nextInt(5);    // cantidad vendida entre 1 y 5

				writer.write(productId + ";" + quantity + ";\n");
			}
		} catch (IOException e) {
			System.out.println("Error al crear el archivo de ventas de " + name + ": " + e.getMessage());
		}
	}

}