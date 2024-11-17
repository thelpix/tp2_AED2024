package aed;

public class ComparatorGanancia extends ComparatorInteger implements Comparador<Ciudad>{
    @Override
    public int comparar(Ciudad c1, Ciudad c2){
        return compare(c1.superavit(), c2.superavit());
    }
}
