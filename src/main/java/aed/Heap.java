package aed;

import java.util.ArrayList;

/*
 * CHUSMEAR COMPLEJIDAD DE HEAPIFY PORQUE ROMPE EL HEAP() (O(n^2)) o desencolarMax(nlogn)
 */

//podria tener los 3 comparadores posibles, y en public heap, el 2do parametro me diria cual comparador usar.
public class Heap<C, H extends Comparador<C>>{
    private ArrayList<C> array = new ArrayList<C>(); //puede contener traslados o ciudades
    private H comparador; //Es alguna clase que tiene la interfaz Comparador para usar .comparar()

    public Heap(C[] array){ //O(n)
        for (int i = 0; i < array.length; i++){ //O(n) //copia los elementos del array a un arrayList<C>
            this.array.add(array[i]); //O(1) Amortizado
        }

        int ultimoPadre = (array.length-1)/2; //O(1)

        /*hacer reversa (siftDown): desde el ultimo padre hasta la raiz
        Como estoy haciendo desde un approach bottom-up, la altura del subarbol (h) del heapify va incrementando por cada heapify,
            incrementando el tiempo de ejecucion.
        Esto es porque empieza una sumatoria que origina en una altura h = 0 (o sea que el padre del heapify es la ultimo hoja) hasta h aproximandose a log n (que el padre del heapify sea la raiz),
            y que posee dentro de la sumatoria {h / 2^(h+1)}, que es la altura dividida por la cantidad maxima de nodos, haciendo que mientras mayor sea h, converge a 0, ya que h <= 2^h por ende daria:
        */
        for(int i = ultimoPadre; i > 0; i--){ //O(n)
            heapify(this.array, this.array.size(), ultimoPadre); 
        }
    }

    //tengo que saber de alguna manera si el array es de traslados o ciudades??
    //obtener el tipo de clase que es, y segun el caso, ejecutar ciertas operaciones o no (modularizar)

    //el mismo array, el tamaño del array y la posicion
    private void heapify(ArrayList<C> array, int arrayTam, int i){ //O(n)
        int padre = i; //O(1)
        int izq = 2*i+1; //posicion izq O(1)
        int der = 2*i+2; //posicion der O(1)
        int elMayorHijo; //O(1)

        //cuando solo existe un hijo y es el izq
        if(der >= arrayTam) { //O(1)
            elMayorHijo = izq; //O(1)
        }
        //cuando existen dos hijos
        else{
            if(comparador.comparar(array.get(izq), array.get(der)) >= 0){ //O(1)
                elMayorHijo = izq; //O(1)
            }
            else{
                elMayorHijo = der; //O(1)
            }
        }

        //Swap si el hijo es mayor al padre
        //Llamar recursivamente a heapify en la posicion del hijo mayor
        if(comparador.comparar(array.get(elMayorHijo), array.get(padre)) > 0){ //O(1)
            swap(array, padre, elMayorHijo); //O(1)

            heapify(array, arrayTam, elMayorHijo); //O(log n) al seleccionar uno de los 2 hijos posibles
        }
        
    }

    private void swap(ArrayList<C> array, int padre, int elMayorHijo){ //O(1)
        C c = array.get(padre);
        array.set(padre, array.get(elMayorHijo));
        array.set(elMayorHijo, c);
    }
    
    public C desencolarMax(){ //O(log n)
        C res;
        res = array.get(0); //O(1)
        C ultimoElemento = array.remove(array.size()-1);; //O(1)
        array.set(0, ultimoElemento); //reemplaza por ultimo elemento, O(1)
        heapify(array, array.size(), 0); //O(log n)
        return res;
    }

    public void encolar(C objeto){ //O(log n)
        //colocar el objeto al final
        array.add(objeto); //O(1)

        //hacer heapify para ordenar
        heapify(array, array.size(), array.size()-1); //O(log n)
    }

    public void borrarPos(int posicion){//O(log n)
        //borra elemento del array
        //heapify para ordenar
    }

    public C consultarMax(){ //O(1)
        return array.get(0);
    }

}


