package test;
import datos.QuickSortSecuencial;

public class TestSecuencial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Medidas de tiempo
		double tiempoInicial, tiempoFinal;
		int dividirTiempo=1000000;
		String medidaTiempo=" Segundos\n";
		//Valores para los arrays
		int tam=1000000;
		int min=0;
		int max=100;
		//Genero array pa el Secuencial
		int[] valor= new int[tam];
		valor=QuickSortSecuencial.generarArrayAleatorio(tam, min, max);
		
		System.out.println("-----------SECUENCIAL--------");
        tiempoInicial = System.nanoTime(); //Qué hora es?
        //QuckSort Secuencial
        QuickSortSecuencial.quickSort(valor, min, max);
        //Muestro el resultado
        tiempoFinal = System.nanoTime()-tiempoInicial;
        System.out.print("El programa Secuencial, demoró: "+ tiempoFinal/dividirTiempo +medidaTiempo);
        //QuickSortSecuencial.mostrarArray(valor);

	}

}
