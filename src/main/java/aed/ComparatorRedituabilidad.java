package aed;

    //t1.ganancia > t2.ganancia
    //t1.ganancia == t2.ganancia ∧ t1.id < t2.id
public class ComparatorRedituabilidad implements Comparador<Traslado>{
    @Override
    public int comparar(Traslado t1, Traslado t2){
        if(t1.gananciaNeta == t2.gananciaNeta){
            return (t2.timestamp).compareTo(t1.timestamp);
        }
        else{
            return (t1.gananciaNeta).compareTo(t2.gananciaNeta);
        }
    }
}

