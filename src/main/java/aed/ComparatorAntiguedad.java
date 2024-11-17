package aed;


//t1.timestamp < t2.timestamp
public class ComparatorAntiguedad extends ComparatorInteger implements Comparador<Traslado>{
    @Override
    public int comparar(Traslado t1, Traslado t2){
        return compare(t2.timestamp, t1.timestamp);
    }
}