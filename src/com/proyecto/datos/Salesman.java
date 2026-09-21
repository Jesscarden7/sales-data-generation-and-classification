package com.proyecto.datos;

/**
 * Representa la información básica de un vendedor generado.
 */
public class Salesman {
	private String documentType;
	private long documentNumber;
	private String firstName;
	private String lastName;

	/**
	 * Crea una nueva instancia de Salesman con la información dada.
	 *
	 * @param documentType   tipo de documento del vendedor
	 * @param documentNumber número de documento del vendedor
	 * @param firstName      nombre del vendedor
	 * @param lastName       apellido del vendedor
	 */
	public Salesman(String documentType, long documentNumber, String firstName, String lastName) {
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return el tipo de documento del vendedor
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @return el número de documento del vendedor
	 */
	public long getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @return el nombre del vendedor
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return el apellido del vendedor
	 */
	public String getLastName() {
		return lastName;
	}
}