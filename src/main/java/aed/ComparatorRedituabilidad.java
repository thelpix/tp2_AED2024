package aed;

    //t1.ganancia > t2.ganancia
    //t1.ganancia == t2.ganancia ∧ t1.id < t2.id
public class ComparatorRedituabilidad extends ComparatorInteger{

    public int comparar(Traslado t1, Traslado t2){
        if(t1.gananciaNeta == t2.gananciaNeta){
            return compare(t2.timestamp, t1.timestamp);
        }
        else{
            return compare(t1.gananciaNeta, t2.gananciaNeta);
        }
    }
}

