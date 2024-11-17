package aed;

//podria tener los 3 comparadores posibles, y en public heap, el 2do parametro me diria cual comparador usar.
public class Heap<C>{
    private int arrayTam;
    private C[] array; //puede contener traslados o ciudades
    private ComparatorGanancia comparatorGanancia;
    private ComparatorAntiguedad comparatorAntiguedad;
    private ComparatorRedituabilidad comparatorRedituabilidad;

    public Heap(C[] array, int elegirComparador){
        this.array = array;
        seleComparador(elegirComparador);
        //manden a Dios
        //Aca tengo que hacer heapify
        //y luego de forma recursiva hacer lo mismo en los padres de las ultimas hojas, de derecha a izquierda, qué
        //o iterativa jeje
    }

    //tengo que saber de alguna manera si el array es de traslados o ciudades??
    //obtener el tipo de clase que es, y segun el caso, ejecutar ciertas operaciones o no (modularizar)

    //para elegir cual comparador usara el heap
    private void seleComparador(int elegirComparador){
        switch (elegirComparador) {
            case 0:
                comparatorGanancia = new ComparatorGanancia();
                break;
            case 1:
                comparatorAntiguedad = new ComparatorAntiguedad();
                break;
            case 2:
                comparatorRedituabilidad = new ComparatorRedituabilidad();
                break;
        }
    }
}
//constructor copia? nah
//encolar
//desencolar
//sacar maximo sin borrarlo :3

