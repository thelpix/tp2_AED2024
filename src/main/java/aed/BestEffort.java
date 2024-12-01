package aed;

import java.util.ArrayList;

public class BestEffort {
    private int cantCiudades;
    private Traslado[] traslados;
    private ArrayList<Integer> ciudadesMayorGanancia; //donde acumulo para retornar
    private ArrayList<Integer> ciudadesMayorPerdida; //x2
    private int MayorGanancia; //variable que comparará la ciudad mas rentable
    private int MayorPerdida; //lo mismo pero en perdidas
    private int totalTrasladosDespachados;
    private int gananciaPromedioPorTraslado;
    private int[] ganancias;
    private int[] perdidas;
    private Heap<Traslado, ComparatorRedituabilidad> heapRedituabilidad;
    private Heap<Traslado, ComparatorAntiguedad> heapAntiguedad;
    private Heap<Ciudad, ComparatorGanancia> heapSuperavits;
    //usar un array y arraylist para superavit?
    
    public BestEffort(int cantCiudades, Traslado[] traslados){
        int[] ganancias = new int[cantCiudades]; //O(|C|),
        int[] perdidas = new int[cantCiudades]; //O(|C|), arrays de tamaño fijos
        Ciudad[] idCiudades = new Ciudad[cantCiudades]; //O(|C|), almacenara las clases ciudades en sus respectivas posiciones (id's) para traerlo al heapSuperavit

        totalTrasladosDespachados = 0;
        
        for(int i=0; i < idCiudades.length; i++){ //O(|C|)
            idCiudades[i] = new Ciudad(i);
        }
        //esto seria O(|C| + |C|) = O(|C|)
        
        heapRedituabilidad = new Heap<>(traslados); //O(|T|) asumiendo que use Algoritmo de Floyd, Max-Heap
        heapAntiguedad = new Heap<>(traslados); //O(|T|), es como un Min-Heap
        heapSuperavits = new Heap<>(idCiudades); //O(|T|)

        ///¿Cómo logro crear Superavits en un heap, pudiendo actualizar 
        
        //esto seria O(|T| + |T|) = O(|T|)
        //complejidad final: O(|C| + |T|)
    }
    
    public void registrarTraslados(Traslado[] traslados){
        int i = 0; //O(1)
        while(i < traslados.length){ //O(|traslados|)
            heapRedituabilidad.encolar(traslados[i]); //O(log(|T|)), el constructor se encarga de asignarle posicion en c/u InfoTraslados
            heapAntiguedad.encolar(traslados[i]); //O(log(|T|))
            i++; //O(1)
        }
        //complejidad = O(|traslados|*log(|T|))
    }
    
    public int[] despacharMasRedituables(int n){ //O(n(log|T|) + log(|C|))
        int i = 0; //O(1)
        int[] res = new int[n];
        while(i < n){ //O(n)
            //Desencolar n veces
            Traslado traslado = heapRedituabilidad.desencolarMax(); //O(log(|T|))
            res[i] = traslado.id;
            
            heapAntiguedad.borrarPos(traslado.posicionHeapAntiguedad)
            //al borrar un traslado, debo modificar heapAntiguedad, pero como se la posicion a borrar, no la tengo que encontrar
            //pasaria de O(|T|log(|T|)) -> O(log(|T|))
            
            //Parte de Ciudades, podria modularizarlo en otra funcion privada
            actualizarCiudades(traslado.destino, traslado.origen, traslado.gananciaNeta);
        }
        totalTrasladosDespachados++;
            //como hay muchos O(1), en complejidad asintotica no se cuentan por constantes
            //complejidad final = O(n(log|T| + log|T|)) -> O(n(log|T|))
            //capaz cuando haga el superAvit o las gananciasPromedio ahi si me de log(|C|)
        return res;
    }
    
    public int[] despacharMasAntiguos(int n){
        //despachar mas antiguos es muy copia y pega, al ser heapAntiguedad un Min-Heap, sacara de forma creciente,
        //teniendo que alterar heapRedituabilidad y la parte de actualizar ciudades es exactamente la misma
            int i = 0; //O(1)
            int[] res = new int[n];
            while(i < n){ //O(n)
                //Desencolar n veces
                Traslado traslado = heapAntiguedad.desencolarMax(); //O(log(|T|))
                res[i] = traslado.id; //cuidado con el aliasing upsi
                
                heapRedituabilidad.borrarPos(traslado.posicionHeapRedituabilidad)
                //al borrar un traslado, debo modificar heapAntiguedad, pero como se la posicion a borrar, no la tengo que encontrar
                //pasaria de O(|T|log(|T|)) -> O(log(|T|))
                
                //Parte de Ciudades
                actualizarCiudades(traslado.destino, traslado.origen, traslado.gananciaNeta);
            }
                //como hay muchos O(1), en complejidad asintotica no se cuentan por constantes
                //complejidad final = O(n(log|T| + log|T|)) -> O(n(log|T|))
                //capaz cuando haga el superAvit o las gananciasPromedio ahi si me de log(|C|)
            totalTrasladosDespachados++;
            return res;    
    }
    
    private void actualizarCiudades(int destino, int origen, int gananciaNeta){
        int perdidaAux = perdidas[destino]; //O(1)
        int gananciaAux = ganancias[origen]; //O(1)

        perdidas[destino] =+ gananciaNeta; //O(1)
        ganancias[origen] =+ gananciaNeta; //O(1)
        gananciaPromedioPorTraslado =+ gananciaNeta;
        //usare dos variables temporales para comparar
        
        if(perdidaAux > MayorPerdida){ //creo que el if es O(1)
            MayorPerdida = perdidaAux; //O(1)
            ciudadesMayorPerdida.clear();
            ciudadesMayorPerdida.add(destino); //O(1) amortizado
        }
        else if(perdidaAux == MayorPerdida){ //creo que sigue siendo O(1)
            ciudadesMayorPerdida.add(destino); //O(1) amortizado
        }
        
        if(gananciaAux > MayorGanancia){ //creo que el if es O(1)
            MayorGanancia = gananciaAux; //O(1)
            ciudadesMayorGanancia.clear();
            ciudadesMayorGanancia.add(origen);
        }
        else if(gananciaAux == MayorGanancia){ //creo que sigue siendo O(1)
            ciudadesMayorGanancia.add(origen); //O(1) amortizado
        }
    }

    public int ciudadConMayorSuperavit(){
        return heapSuperavits.consultarMax().posicion;
    }
    
    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesMayorPerdida;
    }
    
    public int gananciaPromedioPorTraslado(){
        return (gananciaPromedioPorTraslado / totalTrasladosDespachados);
    }
}