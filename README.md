# Generación y Clasificación de Datos - Entrega Final

Materia: Conceptos Fundamentales de Programación.
Profesor: Edwin Andrés Niño Velásquez

## Integrantes
* Cristian Leonardo Bejarano Vasquez - clebejarano@poligran.edu.co
* Jessica Julliet Cardeño Saldarriaga - jjucardenos@poligran.edu.co
* Valentina Gómez Parra - vgomezp@poligran.edu.co	

## Descripción del Proyecto
Esta tercera entrega constituye la **versión final y completa** del proyecto integrador, cumpliendo con todos los requerimientos académicos:

1. **`GenerateInfoFiles.java`**: Programa generador que crea los archivos planos de prueba en la raíz del proyecto (`productos.csv`, `vendedores.csv` y archivos de ventas individuales `ventas_*.csv`).
2. **`main.java`**: Programa procesador que lee los archivos desde la raíz mediante colecciones dinámicas (`ArrayList`), calcula los montos acumulados por vendedor y las cantidades por producto, aplica ordenamiento descendente y genera los reportes finales (`reporte_vendedores.csv` y `reporte_productos.csv`).
3. **`Salesman.java`**: Clase modelo auxiliar para la transferencia de datos de vendedores.
4. **`conslusion.txt`**: Archivo plano en la raíz del proyecto que contiene el informe de conclusiones sobre aprendizajes, aplicaciones profesionales y dificultades superadas.

## Entorno y Requisitos
* **Lenguaje:** Java 8 (JavaSE-1.8)
* **IDE:** Eclipse IDE for Java Developers

## Modo de Ejecución en Eclipse
1. Importar el proyecto en Eclipse (`File > Open Projects from File System...`).
2. **Paso 1 (Generación):** Ejecutar `GenerateInfoFiles.java` (*Run As > Java Application*). Se crearán los archivos de entrada `.csv` en la raíz.
3. **Paso 2 (Procesamiento):** Ejecutar `main.java` (*Run As > Java Application*). Se leerán los datos, se realizarán los cálculos y se crearán `reporte_vendedores.csv` y `reporte_productos.csv` en la raíz.