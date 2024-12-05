package aed;

public class ComparatorGanancia extends ComparatorInteger implements Comparador<Ciudad>{
    @Override
    public int comparar(Ciudad c1, Ciudad c2){
        if (c1.superavit == c2.superavit){
            return compare(c2.posicion, c1.posicion);
        }
        else{
            return compare(c1.superavit, c2.superavit);
        }
    }
}
