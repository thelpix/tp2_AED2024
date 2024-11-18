package aed;

public class Ciudad {
    private int id;
    int posicion;
    private int superavit = 0;

    public Ciudad(int id){
        this.id = id;
    }

    public int superavit(){
        return this.superavit;
    }
}
