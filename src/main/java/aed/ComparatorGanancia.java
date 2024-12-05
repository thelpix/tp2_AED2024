package aed;

public class ComparatorGanancia implements Comparador<Ciudad>{
    @Override
    public int comparar(Ciudad c1, Ciudad c2){
        if (c1.superavit == c2.superavit){
            return (c2.id).compareTo(c1.id);
        }
        else{
            return (c1.superavit).compareTo(c2.superavit);
        }
    }
}
