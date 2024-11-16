package aed;

//comparador generico de enteros
public class ComparatorInteger {
    public int compare(int x, int y){
        if (x > y) {return 1;}
        else if(x == y) {return 0;}
        else {return -1;}
    }

}
