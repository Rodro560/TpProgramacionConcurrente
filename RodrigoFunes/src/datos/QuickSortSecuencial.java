package datos;

import java.util.Random;

public class QuickSortSecuencial {

	 public static int[] generarArrayAleatorio(int n, int min, int max) {
	        // Declaración del array
	        int[] arr = new int[n];
	        
	        // Generación de números aleatorios
	        Random random = new Random();
	        for (int i = 0; i < n; i++) {
	            arr[i] = random.nextInt(max - min + 1) + min;
	        }
	        
	        return arr;
	    }
	 
	 
	 public static void mostrarArray(int[] arr) {
	        System.out.println("\n-----------------------:\n");
	        for (int num : arr) {
	            System.out.print(num +" - ");
	        }
	    }
	 
	 public static void quickSort(int[] arr, int low, int high) {
			if (low < high) {
				int pi = partition(arr, low, high);
				quickSort(arr, low, pi - 1);
				quickSort(arr, pi + 1, high);
			}
		}

		private static int partition(int[] arr, int low, int high) {
			int pivot = arr[high];
			int i = low;
			for (int j = low; j < high; j++) {
				if (arr[j] <= pivot) {
					swap(arr, j, i);
					i++;
				}
			}
			swap(arr, i, high);
			return i;
		}
		
		static void swap(int arr[], int x, int y) {
			int temp= arr[x];
			arr[x]= arr[y];
			arr[y]=temp;
		}
		
}