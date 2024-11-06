package aed;

import java.util.ArrayList;

public class BestEffort {
    private int cantCiudades;
    private Traslado[] traslados;

    //sacar dos comparadores, ganancia y perdida
    //gananciasComparator = Comparator.comparing(Ciudades::ganancias);
    //perdidasComparator = Comparator.comparing(Ciudades::perdidas);

    
    //sacar dos heaps sobre arrays, que usen un comparador cada uno
    //implementar heap :(


    //los nodos del heap serian (supongo no se) tuplas de tipo <ciudad.id, ciudad.ganancia> o <ciudad.id, ciudad.perdida> o capaz el objeto directamente


    //heap(gananciasComparator, array todasLasCiudades:Ciudad);
    //heap(perdidasComparator, array todasLasCiudades:Ciudad);

    public BestEffort(int cantCiudades, Traslado[] traslados){
        this.cantCiudades = cantCiudades;
        this.traslados = traslados;
    }

    //cuando ya se la cantCiudades, las genero
    int i = 0;
    /*
    while(i < cantCiudades) {
        generar las ciudades con su id que es i

        i++
    }
    */

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}
