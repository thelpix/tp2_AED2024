package aed;

public class Traslado{
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    //handles
    int posicionHeapAntiguedad;
    int posicionHeapRedituabilidad;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
}
