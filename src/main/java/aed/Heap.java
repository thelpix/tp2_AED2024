package aed;

import java.util.ArrayList;


//Un Heap generico, donde <C,H>, C son las posibles clases que puede almacenar y H es un comparador para C
public class Heap<C, H extends Comparador<C>>{
    ArrayList<C> array = new ArrayList<C>(); //puede contener traslados o ciudades
    H comparador; //Es alguna clase que tiene la interfaz Comparador para usar .comparar()
    ArrayList<Integer> handles; //los handles contienen las posiciones de sus respectivos elementos, ordenados usando los ids que empiezan en 0 en ciudades, o en traslados que lo paso con un -1

    public Heap(C[] array, H comparador){ //O(n) + O(n) + O(n) = O(n)
        this.comparador = comparador; //O(1)
        handles = new ArrayList<Integer>(array.length); //O(1)

        //primero, añade todos los handles necesarios
        for (int i = 0; i < array.length; i++){ //O(n)
            handles.add(0); //O(1) amortizado, inicializa los handles
        }

        for (int i = 0; i < array.length; i++){ //O(n)
            //copia los elementos del array a un arrayList<C>
            this.array.add(array[i]); //O(1) amortizado

            //actualiza los handles por primera vez
            actualizarHandle(i); //O(1)
    
        }
        
        int ultimoPadre = (this.array.size()-2)/2; //O(1)

        /*hacer reversa (siftDown): desde el ultimo padre hasta la raiz
        Como funciona desde un approach bottom-up, la altura (h) del subarbol va incrementando por cada iteracion del for(),
            incrementando el tiempo de ejecucion, quiero decir, que mientras más grande es el indice donde estes en el array, menos va a tardar
        Si un heap lo represento en un array, el ultimo padre aproximadamente podria estar en la mitad del array, y entonces,
            si hago siftDown estoy ordenando ya desde la mitad hasta el inicio del array, haciendo que tarde O(n) en vez de O(n log n)
        */
        //recorremos los nodos desde el ultimo padre hasta la raiz
        for(int i = ultimoPadre; i >= 0; i--){ //O(n)
            siftDown(i); //O(log n)
        }
    }


    //asigna handle segun si es Traslado o Ciudad el objeto
    private void actualizarHandle(int nuevaPosicion){  //O(1)
        //pregunta segun cual comparador usar para setear su posicion
        if(comparador instanceof ComparatorAntiguedad || comparador instanceof ComparatorRedituabilidad){ //O(1)
            handles.set(((Traslado) array.get(nuevaPosicion)).id -1, Integer.valueOf(nuevaPosicion)); //O(1)
        }
        else if(comparador instanceof ComparatorGanancia){
            //actualizar handlesCiudades[]
            handles.set(((Ciudad) array.get(nuevaPosicion)).id, Integer.valueOf(nuevaPosicion)); //O(1)
        }
    }

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


        //Swap si un hijo es mayor al padre
        //Llamar recursivamente en la posicion del hijo mayor
        //si no hay mas hijos entonces no sigue mas
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
        res = array.get(0); //O(1)
        if (array.size() > 1){
            C ultimoElemento = array.remove(array.size()-1); //O(1)

            array.set(0, ultimoElemento); //reemplaza por ultimo elemento, O(1)
            actualizarHandle(0); //O(1)
            siftDown(0); //O(log n)
        }
        else{
            array.remove(0); //O(1)
        }

        return res;
    }

    public void encolar(C objeto){ //O(log n)
        //colocar el objeto al final e insertar su handle
        array.add(objeto); //O(1)
        handles.add(array.size()); //O(1) amortizado
        actualizarHandle(array.size()-1);

        //hacer siftUp para ordenar, al ser el ultimo elemento, solo puede subir
        siftUp(array.size()-1);; //O(log n) 
    }

    public void borrarPos(int posicion){ //O(log n)
        int ultimo = array.size() - 1;
        if (posicion == ultimo) {
            array.remove(ultimo); //O(1)
        } 
        else {
            C ultimoElemento = array.remove(ultimo); //O(1)
            array.set(posicion, ultimoElemento); //O(1)
            actualizarHandle(posicion); //O(1)
            //el ultimo elemento podria ser tanto mayor o menor a sus hijos o padre, asi que uso ambos sift
            siftDown(posicion); //O(log n)
            siftUp(posicion); //O(log n)
        }
    }

    private void siftUp(int i) { //O(log n)
        while (i > 0) { //O(log n)
            int padre = (i - 1) / 2; //O(1)
    
            // Si el elemento actual es mas grande que el padre, swapea
            if (comparador.comparar(array.get(i), array.get(padre)) >= 0) { //O(1)
                swap(i, padre); //O(1)
            }
            i = padre;
        }
    }

    public C consultarMax(){ //O(1)
        return array.get(0); //O(1)
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


