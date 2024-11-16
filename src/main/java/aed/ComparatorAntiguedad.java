package aed;


//t1.timestamp < t2.timestamp
public class ComparatorAntiguedad extends ComparatorInteger{
    Traslado t1;
    Traslado t2;

    public ComparatorAntiguedad(Traslado t1, Traslado t2){
        this.t1 = t1;
        this.t2 = t2;
    }

    public int comparar(Traslado t1, Traslado t2){
        return compare(t2.timestamp, t1.timestamp);
    }
}
