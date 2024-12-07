package aed;

    //t1.ganancia > t2.ganancia
    //t1.ganancia == t2.ganancia ∧ t1.id < t2.id
public class ComparatorRedituabilidad implements Comparador<Traslado>{
    @Override
    public int comparar(Traslado t1, Traslado t2){
        if(t1.gananciaNeta == t2.gananciaNeta){
            return (Integer.valueOf(t2.id)).compareTo(Integer.valueOf(t1.id));
        }
        else{
            return (t1.gananciaNeta).compareTo(t2.gananciaNeta);
        }
    }
}

