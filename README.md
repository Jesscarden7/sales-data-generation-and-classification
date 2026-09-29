# Generación y Clasificación de Datos - Entrega 2 (Semana 5)

Materia: Conceptos Fundamentales de Programación.
Profesor: Edwin Andrés Niño Velásquez

## Integrantes
* Cristian Leonardo Bejarano Vasquez - clebejarano@poligran.edu.co
* Jessica Julliet Cardeño Saldarriaga - jjucardenos@poligran.edu.co
* Valentina Gómez Parra - vgomezp@poligran.edu.co	

## Descripción del Proyecto
En esta segunda entrega dejamos lista la versión preliminar del sistema con dos programas principales:

1. **`GenerateInfoFiles`**: Crea los archivos de prueba en la raíz del proyecto (`productos.csv`, `vendedores.csv` y las ventas individuales de cada vendedor).
2. **`main`**: Lee todos esos archivos desde la raíz, suma las ventas de cada vendedor y producto, los ordena de mayor a menor y genera los reportes finales (`reporte_vendedores.csv` y `reporte_productos.csv`).

## Entorno de desarrollo
* **Lenguaje:** Java 8 (JavaSE-1.8)
* **IDE:** Eclipse IDE

## Cómo ejecutar en Eclipse
1. Abrir el proyecto en Eclipse.
2. Correr la clase `GenerateInfoFiles.java` (*Run As > Java Application*). Esto creará los archivos `.csv` en la raíz.
3. Correr la clase `main.java` (*Run As > Java Application*). Esto leerá los datos y creará `reporte_vendedores.csv` y `reporte_productos.csv`.

---

## Elementos Faltantes (Entrega Final)
Para la última entrega del proyecto nos queda pendiente desarrollar los siguientes puntos:

* **Archivo `conslusion.txt`**: Redactar el documento final sobre lo que aprendimos en el proyecto, las dificultades que tuvimos como equipo y cómo aplicamos esto a nivel profesional.
* **Manejo de errores y validaciones**: Hacer el código más robusto agregando controles por si algún archivo viene con mal formato, precios en negativo o IDs que no existen.
* **Usar listas dinámicas (`ArrayList`)**: Reemplazar los arreglos fijos de 100 posiciones por listas dinámicas para poder procesar cualquier cantidad de datos sin tener un límite predefinido.