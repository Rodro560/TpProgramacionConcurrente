//@author Kenneth Powers

package datos;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class QuickSortConcurrente {
	
	//El numero de hilos que vamos a utilizar para ordenar
    private static final int N_THREADS = Runtime.getRuntime().availableProcessors();

   
    //Determinamos un mutiplo para cuando necesitemos retroceder
    private static final int FALLBACK = 2;

    //El grupo de hilos que vamos a utilizar para ejecutar los Runnables de QuickSort
    private static Executor pool = Executors.newFixedThreadPool(N_THREADS);

    //Este seria el metodo principal que se llamara para usar el QuickSort. 
    //"input" es el array a ordenar
    //<T> seria el tipo de objetos que se estan ordenando, el mismo debe de extender desde "Comparable"
    public static <T extends Comparable<T>> void quicksort(T[] input) {
        final AtomicInteger count = new AtomicInteger(1);//El tiempo que un hilo esta utilizando los parametros 
        pool.execute(new QuicksortRunnable<T>(input, 0, input.length - 1, count));//ejecucion de los hilos para el Runnable
        try {
            synchronized (count) {
                count.wait();//funcio paracida al "sleep" perteneciente a la clase "Thread"
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    //Ordena una sección de un array utilizando el algoritmo de quicksort
    //Pese a que se llama quickSort el mismo no aplica recursividad. en su lugar genera hilos para simular recursividad
    private static class QuicksortRunnable<T extends Comparable<T>> implements Runnable {
       
    	//El array que va a ser ordenado
        private final T[] values;
        //El indice de inicio para el ordenamiento
        private final int left;
        //El indice del final para QucikSort 
        private final int right;
        //El numero de hilos que estan en ejecucion
        private final AtomicInteger count;

       //El constructor
        public QuicksortRunnable(T[] values, int left, int right, AtomicInteger count) {
            this.values = values;
            this.left = left;
            this.right = right;
            this.count = count;
        }

        //Este procedimineto sive para cuando un hilo haya terminado su tarea, comprueba si los demás hilos también lo han hecho
        // si es el caso, le notificamos a "count" para que deje de estar bloqueado 
        @Override
        public void run() {
            quicksort(left, right);
            synchronized (count) {
                // AtomicInteger.getAndDecrement() devuelve el valor antiguo. Si el valor antiguo es 1, entonces sabemos que el valor actual es 0
                if (count.getAndDecrement() == 1)
                    count.notify();
            }
        }

        //El metodo que realiza el ordenamiento/ se utiliza a "count" para evitar que se pierdan datos (no usa semaforos)
        //pLeft es el indice izquierdo del array a ordenar
      //pRight es el indice derecho del array a ordenar
        private void quicksort(int pLeft, int pRight) {
            if (pLeft < pRight) {
                int storeIndex = partition(pLeft, pRight);
                if (count.get() >= FALLBACK * N_THREADS) {
                    quicksort(pLeft, storeIndex - 1);
                    quicksort(storeIndex + 1, pRight);
                } else {
                    count.getAndAdd(2);
                    pool.execute(new QuicksortRunnable<T>(values, pLeft, storeIndex - 1, count));
                    pool.execute(new QuicksortRunnable<T>(values, storeIndex + 1, pRight, count));
                }
            }
        }

        //Particiona la porción del array entre los índices izquierdo y derecho,
        //moviendo todos los elementos menores que values[pivotIndex] antes del pivote,
        //y los elementos iguales o mayores después de él.
        private int partition(int pLeft, int pRight) {
            T pivotValue = values[pRight];
            int storeIndex = pLeft;
            for (int i = pLeft; i < pRight; i++) {
                if (values[i].compareTo(pivotValue) < 0) {
                    swap(i, storeIndex);
                    storeIndex++;
                }
            }
            swap(storeIndex, pRight);
            return storeIndex;
        }

       //Metodo para intercambiar las posiciones de las variables
        private void swap(int left, int right) {
            T temp = values[left];
            values[left] = values[right];
            values[right] = temp;
        }
    }
    
    public static void mostrarArray(Integer[] arr) {
        System.out.println("\n-----------------------:\n");
        for (int num : arr) {
            System.out.print(num +" - ");
        }
    }
    
    public static Integer[] generarArrayAleatorio(int n, int min, int max) {
        // Declaración del array
        Integer[] arr = new Integer[n];
        
        // Generación de números aleatorios
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        
        return arr;
    }
    
}
