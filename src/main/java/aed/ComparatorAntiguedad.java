package aed;


//t1.timestamp < t2.timestamp
public class ComparatorAntiguedad implements Comparador<Traslado>{
    @Override
    public int comparar(Traslado t1, Traslado t2){
        return t2.timestamp.compareTo(t1.timestamp);
    }
}
