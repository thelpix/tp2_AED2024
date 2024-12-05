package aed;

import java.util.ArrayList;


//podria tener los 3 comparadores posibles, y en public heap, el 2do parametro me diria cual comparador usar.
public class Heap<C, H extends Comparador<C>>{
    ArrayList<C> array = new ArrayList<C>(); //puede contener traslados o ciudades
    H comparador; //Es alguna clase que tiene la interfaz Comparador para usar .comparar()
    ArrayList<Integer> handles; //los handles contienen las posiciones de sus respectivos elementos, ordenados usando los ids que empiezan en 0 en ciudades, o en traslados que lo paso con un -1

    public Heap(C[] array, H comparador){ //O(n)
        this.comparador = comparador; //O(1)
        handles = new ArrayList<Integer>(array.length); //O(1)

        for (int i = 0; i < array.length; i++){ //O(n)
            //copia los elementos del array a un arrayList<C>
            this.array.add(array[i]); //O(1)
            handles.add(0); //O(1) amortizado, inicializar los handles

            //asignar handles de los elementos primera vez
            actualizarHandle(i); //O(1)
    
        }

        int ultimoPadre = (this.array.size()-2)/2; //O(1)

        /*hacer reversa (siftDown): desde el ultimo padre hasta la raiz
        Como estoy haciendo desde un approach bottom-up, la altura del subarbol (h) del heapify va incrementando por cada heapify,
            incrementando el tiempo de ejecucion.
        Esto es porque empieza una sumatoria que origina en una altura h = 0 (o sea que el padre del heapify es la ultimo hoja) hasta h aproximandose a log n (que el padre del heapify sea la raiz),
            y que posee dentro de la sumatoria {h / 2^(h+1)}, que es la altura dividida por la cantidad maxima de nodos, haciendo que mientras mayor sea h, converge a 0, ya que h <= 2^h por ende daria:
        */
        for(int i = ultimoPadre; i >= 0; i--){ //O(n)
            siftDown(i); 
        }
    }


    //asigna handle segun si es Traslado o Ciudad el objeto
    private void actualizarHandle(int nuevaPosicion){  //O(1)
        //pregunta segun cual comparador usar para obtener su posicion
        if(comparador instanceof ComparatorAntiguedad || comparador instanceof ComparatorRedituabilidad){ //O(1)
            handles.set(((Traslado) array.get(nuevaPosicion)).id -1, Integer.valueOf(nuevaPosicion));
        }
        else if(comparador instanceof ComparatorGanancia){
            //es de tipo Ciudad[]
            ((Ciudad) array.get(nuevaPosicion)).posicion = nuevaPosicion; //O(1)
            //actualizar handlesCiudades[]
            handles.set(((Ciudad) array.get(nuevaPosicion)).id, Integer.valueOf(nuevaPosicion)); //O(1)
        }
    }
    //tengo que saber de alguna manera si el array es de traslados o ciudades??
    //obtener el tipo de clase que es, y segun el caso, ejecutar ciertas operaciones o no (modularizar)

    //el mismo array, el tamaño del array y la posicion
    private void siftDown(int i){ //O(log n)
        int arrayTam = array.size();
        int padre = i; //O(1)
        int izq = 2*i+1; //posicion izq O(1)
        int der = 2*i+2; //posicion der O(1)
        int elMayorHijo = -1; //O(1), lo uso para referenciar
 

        if(izq < arrayTam && (der >= arrayTam || comparador.comparar(array.get(izq), array.get(der)) >= 0)){ //O(1)
            elMayorHijo = izq; //O(1)
        }
        else if(der < arrayTam){
            elMayorHijo = der; //O(1)
        }


        //Swap si el hijo es mayor al padre
        //Llamar recursivamente a heapify en la posicion del hijo mayor
        if(elMayorHijo != -1 && comparador.comparar(array.get(elMayorHijo), array.get(padre)) > 0){ //O(1)
            swap(padre, elMayorHijo); //O(1)
            siftDown(elMayorHijo); //O(log n) al seleccionar uno de los 2 hijos posibles
        }
        
    }

    private void swap(int padre, int elMayorHijo){ //O(1)
        C c = array.get(padre); //O(1)

        //cuando hago swap, intercambio las posiciones del padre al hijoMayor, y el hijoMayor al padre
        array.set(padre, array.get(elMayorHijo)); //O(1)
        actualizarHandle(padre); //O(1), ahora actualizo el handle del objeto que ahora este en el index padre
        array.set(elMayorHijo, c); //O(1)
        actualizarHandle(elMayorHijo); //O(1), ahora actualizo el handle del objeto que ahora este en el index elMayorHijo
    }
    
    public C desencolarMax(){ //O(log n)
        //se reemplaza el ultimo elemento si hay mas de 2 nodos
        C res;
        res = array.get(0); //O(1
        if (array.size() > 1){
            C ultimoElemento = array.remove(array.size()-1); //O(1)

            array.set(0, ultimoElemento); //reemplaza por ultimo elemento, O(1)
            actualizarHandle(0);
            siftDown(0); //O(log n)
        }
        else{
            array.remove(0);
        }

        return res;
    }

    public void encolar(C objeto){ //O(log n)
        //colocar el objeto al final
        array.add(objeto); //O(1)
        actualizarHandle(array.size()-1);

        //hacer siftUp para ordenar
        siftUp(array.size()-1);; //O(log n) 
    }

    public void borrarPos(int posicion){ //O(log n)
        int ultimoIndex = array.size() - 1;
        if (posicion == ultimoIndex) {
            array.remove(ultimoIndex);
        } else {
            C ultimo = array.remove(ultimoIndex);
            array.set(posicion, ultimo);
            actualizarHandle(posicion);
            siftDown(posicion);
            siftUp(posicion);
        }
    }

    private void siftUp(int i) { //O(log n) //usado en encolar por ej.
        while (i > 0) { //O(log n)
            int padre = (i - 1) / 2; //O(1)
    
            // Si el elemento actual no rompe la propiedad del heap, termina
            if (comparador.comparar(array.get(i), array.get(padre)) >= 0) { //O(1)
                swap(i, padre); //O(1)
            }
            i = padre;
        }
    }

    public C consultarMax(){ //O(1)
        return array.isEmpty() ? null : array.get(0);
    }

    public void modValorCiudad(int posicion, int valor){ //O(log n)
        //agarro el valor .superavit de un elemento de ciudad y luego hago siftdown si fue resta o siftup si fue suma
        Ciudad ciudad = (Ciudad) array.get(posicion); //O(1)
        ciudad.superavit += valor; //O(1)

        //si valor == 0, no cambio ya que no estoy alterando nada
        if(valor < 0){
            siftDown(posicion); //O(log n)
        }
        else if(valor > 0){
            siftUp(posicion); //O(log n)
        }

    }
}


