package aed;

public class Heap<T, C>{
    private int arrayTam;
    private T Comparador;
    private C[] array;

    public Heap(C[] array, T Comparador){
        this.array = array;
        this.Comparador = Comparador;
        //manden a Dios
        //Aca tengo que hacer heapify
        //y luego de forma recursiva hacer lo mismo en los padres de las ultimas hojas, de derecha a izquierda, qué
        //o iterativa jeje
    }
}
//constructor copia? nah
//encolar
//desencolar
//sacar maximo sin borrarlo :3

