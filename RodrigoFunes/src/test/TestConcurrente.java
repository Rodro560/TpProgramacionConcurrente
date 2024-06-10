package test;
import datos.QuickSortConcurrente;

public class TestConcurrente {

	public static void main(String[] args) {
		 
		//Medidas de tiempo
		double tiempoInicial, tiempoFinal;
		int dividirTiempo=1000000;
		String medidaTiempo=" Segundos\n";
		//Valores para los arrays
		int tam=1000;
		int min=0;
		int max=100;
		//Genero array para el Concurrente
		Integer[] values = new Integer[tam];
		values=QuickSortConcurrente.generarArrayAleatorio(tam, min, max);
		
		System.out.println("-----------CONCURRENTE--------");
		tiempoInicial = System.nanoTime(); //Qué hora es?
        // Multi-Threaded Quick Sort
        QuickSortConcurrente.quicksort(values);
        //Muestro el resultado
        tiempoFinal = System.nanoTime()-tiempoInicial;
        System.out.print("El programa Concurrente, demoró: "+ tiempoFinal/dividirTiempo +medidaTiempo);
        //QuickSortConcurrente.mostrarArray(values);
        
        
	}

}
