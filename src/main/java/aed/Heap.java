package aed;

//podria tener los 3 comparadores posibles, y en public heap, el 2do parametro me diria cual comparador usar.
public class Heap<C>{
    private C[] array; //puede contener traslados o ciudades
    private Comparador<C> comparadorReferencia;
    private int arrayTam;
    private int elegirComparador;
    private ComparatorGanancia comparatorGanancia;
    private ComparatorAntiguedad comparatorAntiguedad;
    private ComparatorRedituabilidad comparatorRedituabilidad;

    public Heap(C[] array, int elegirComparador){
        this.arrayTam = array.length;
        this.array = array; //O(1)
        this.elegirComparador = elegirComparador;
        activarComparador(elegirComparador); //O(1)
        int ultimoPadre = (array.length-1)/2; //O(1)

        //hacer reversa (siftDown)
        //desde el ultimo padre hasta el inicio
        for(int i = ultimoPadre; i >= 0; i--){ //O(N)
            heapify(array, array.length, ultimoPadre);
        }
    }

    //tengo que saber de alguna manera si el array es de traslados o ciudades??
    //obtener el tipo de clase que es, y segun el caso, ejecutar ciertas operaciones o no (modularizar)

    //el mismo array, el tamaño del array y la posicion
    private void heapify(C[] array, int arrayTam, int i){
        int mayor = i; //O(1)
        int izq = 2*i+1; //posicion izq O(1)
        int der = 2*i+2; //posicion der O(1)
        int elMayorHijo;

        //cuando solo existe un hijo y es el izq
        if(der >= arrayTam) { //O(1)
            elMayorHijo = izq;
        }
        //cuando existen dos hijos
        else{
            if(comparadorReferencia.comparar(array[izq], array[der]) >= 0){ //O(1)
                elMayorHijo = izq;
            }
            else{
                elMayorHijo = der;
            }
        }

        //Swap si el hijo es mayor al padre
        //Llamar recursivamente a heapify en la posicion del hijo mayor
        if(comparadorReferencia.comparar(array[elMayorHijo], array[mayor]) > 0){
            swap(array, mayor, elMayorHijo); //O(1)

            heapify(array, arrayTam, elMayorHijo);
        }
        
    }

    private void swap(C[] array, int mayor, int elMayorHijo){ //O(1)
        C c = array[mayor];
        array[mayor] = array[elMayorHijo];
        array[mayor] = c;
    }

    private void activarComparador(int elegirComparador){
        switch (elegirComparador) { //Como actuan como if, elseif, seria O(1)
            case 0:
                comparadorReferencia = (Comparador<C>) new ComparatorGanancia();
                break;
            case 1:
                comparadorReferencia = (Comparador<C>) new ComparatorAntiguedad();
                break;
            case 2:
                comparadorReferencia = (Comparador<C>) new ComparatorRedituabilidad();
                break;
        }
    }
    
    public C desencolarMax(){ //O(log n)
        C res;
        res = array[0]; //O(1)
        array[0] = array[array.length - 1]; //reemplaza por ultimo elemento, O(1)
        heapify(array, array.length, 0); //ordenar el reemplazado
        arrayTam--;
        return res;
    }

    public void encolar(C objeto){ //O(log n)
        //redimensionar el array antes de encolar si esta lleno

        //colocar el objeto al final

        //incrementar tamaño

        //hacer heapify para ordenar

    }

    public void borrarPos(int posicion){
        //borra elemento del array
        //heapify para ordenar
    }

    public C consultarMax(){ //O(1)
        C res;
        res = array[0];
        return res;
    }
}


