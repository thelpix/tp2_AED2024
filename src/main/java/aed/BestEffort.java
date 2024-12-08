package aed;

import java.util.ArrayList;

public class BestEffort {
    private ArrayList<Integer> ciudadesMayorGanancia = new ArrayList<Integer>(); //donde acumulo para retornar
    private ArrayList<Integer> ciudadesMayorPerdida = new ArrayList<Integer>(); //x2
    private int MayorGanancia; //variable que comparará la ciudad mas rentable
    private int MayorPerdida; //lo mismo pero en perdidas
    private int totalTrasladosDespachados;
    private int gananciaTotalPorTraslado;
    private int[] ganancias;
    private int[] perdidas;
    private Heap<Traslado, ComparatorRedituabilidad> heapRedituabilidad;
    private Heap<Traslado, ComparatorAntiguedad> heapAntiguedad;
    private Heap<Ciudad, ComparatorGanancia> heapSuperavits;
    
    //esperemos que le guste :) saludos
    
    public BestEffort(int cantCiudades, Traslado[] traslados){

        ganancias = new int[cantCiudades]; //O(|C|),
        perdidas = new int[cantCiudades]; //O(|C|), arrays de tamaño fijos
        Ciudad[] idCiudades = new Ciudad[cantCiudades]; //O(|C|), almacenara las clases ciudades en sus respectivas posiciones (id's) para traerlo al heapSuperavit

        totalTrasladosDespachados = 0;
        gananciaTotalPorTraslado = 0;
        //inicializar las ganancias y perdidas en 0 y las ciudades con su id
        for(int i=0; i < cantCiudades; i++){ //O(|C|)
            idCiudades[i] = new Ciudad(i);
            ganancias[i] = 0;
            perdidas[i] = 0;
        }
        
        heapRedituabilidad = new Heap<>(traslados, new ComparatorRedituabilidad()); //O(|T|) Max-Heap
        heapAntiguedad = new Heap<>(traslados, new ComparatorAntiguedad()); //O(|T|), Min-Heap
        heapSuperavits = new Heap<>(idCiudades, new ComparatorGanancia()); //O(|C|)
        
        //complejidad final: O(|C| + |T|)
    }
    
    public void registrarTraslados(Traslado[] traslados){
        int i = 0; //O(1)
        while(i < traslados.length){ //O(|traslados|)
            heapRedituabilidad.encolar(traslados[i]); //O(log(|T|))
            heapAntiguedad.encolar(traslados[i]); //O(log(|T|))
            i++; //O(1)
        }
        //complejidad = O(|traslados|*log(|T|))
    }

    public int[] despacharMasRedituables(int n){ //O(n(log|T|) + log(|C|)))
        int i = 0; //O(1)
        int[] res = new int[n];
        //Desencolar n veces
        while(i < n && heapRedituabilidad.array.size() > 0){ //O(n)
            Traslado traslado = heapRedituabilidad.desencolarMax(); //O(log(|T|))
            res[i] = traslado.id;
            
            //al borrar un traslado, debo modificar heapAntiguedad, pero como se la posicion a borrar, no la tengo que buscar
            //pasaria de O(|T|log(|T|)) -> O(log(|T|))
            heapAntiguedad.borrarPos(heapAntiguedad.handles.get(traslado.id -1)); //O(log n)
            
            //Parte de Ciudades, lo modularizo en 2 funciones privadas
            actualizarCiudades(traslado.destino, traslado.origen, traslado.gananciaNeta);
            actualizarSuperavits(traslado.destino, traslado.origen, traslado.gananciaNeta);

            i++;
            totalTrasladosDespachados++;
        }
            //como hay O(1), en complejidad asintotica no se cuentan por constantes
        return res;
    }
    
    public int[] despacharMasAntiguos(int n){ //O(n(log |T|) + log |C|))
        //despachar mas antiguos es muy copia y pega, al ser heapAntiguedad un Min-Heap, sacara de forma creciente,
        //teniendo que alterar heapRedituabilidad y la parte de actualizar ciudades es exactamente la misma
            int i = 0; //O(1)
            int[] res = new int[n];
            while(i < n && heapAntiguedad.array.size() > 0){ //O(n)
                //Desencolar n veces
                Traslado traslado = heapAntiguedad.desencolarMax(); //O(log(|T|))
                res[i] = traslado.id; //O(1)
                
                heapRedituabilidad.borrarPos(heapRedituabilidad.handles.get(traslado.id -1));
                //al borrar un traslado, debo modificar heapRedituabilidad, pero como se la posicion a borrar, no la tengo que encontrar
                //pasaria de O(|T|log(|T|)) -> O(log(|T|))
                
                //Parte de Ciudades
                actualizarCiudades(traslado.destino, traslado.origen, traslado.gananciaNeta); //O(1)
                actualizarSuperavits(traslado.destino, traslado.origen, traslado.gananciaNeta); //O(log |C|)
                i++;
                totalTrasladosDespachados++;
            }
            return res;    
    }
    
    private void actualizarCiudades(int destino, int origen, int gananciaNeta){ //O(1)
        perdidas[destino] += gananciaNeta; //O(1)
        ganancias[origen] += gananciaNeta; //O(1)

        //usare dos variables temporales para comparar las ciudades
        int perdidaAux = perdidas[destino]; //O(1) 
        int gananciaAux = ganancias[origen]; //O(1)

        gananciaTotalPorTraslado += gananciaNeta;
        
        if(perdidaAux > MayorPerdida){ //O(1)
            MayorPerdida = perdidaAux; //O(1)
            ciudadesMayorPerdida.clear(); //O(1)
            ciudadesMayorPerdida.add(destino); //O(1) amortizado
        }
        else if(perdidaAux == MayorPerdida){ //O(1)
            MayorPerdida = gananciaNeta; //O(1)
            ciudadesMayorPerdida.add(destino); //O(1) amortizado
        }
        
        if(gananciaAux > MayorGanancia){ //O(1)
            MayorGanancia = gananciaAux; //O(1)
            ciudadesMayorGanancia.clear(); //O(1)
            ciudadesMayorGanancia.add(origen); //O(1) amortizado
        }
        else if(gananciaAux == MayorGanancia){ //O(1)
            MayorGanancia = gananciaNeta; //O(1)
            ciudadesMayorGanancia.add(origen); //O(1) amortizado
        }
    }

    private void actualizarSuperavits(int destino, int origen, int ganancia){ //O(log |C|)
        //tiene que actualizar las variables superavits de las ciudades modificadas
        //una vez actualizadas, debe ordenadarlas para respetar la propiedad del heap

        //la posicion la obtendre a traves de un array fijo que contiene los handles por indice
        heapSuperavits.modValorCiudad(heapSuperavits.handles.get(destino), -ganancia); //O(log |C|) ya que solo hay cantCiudades de handles
        heapSuperavits.modValorCiudad(heapSuperavits.handles.get(origen), ganancia); //O(log |C|)

    }

    public int ciudadConMayorSuperavit(){ //O(1)
        //para que esto funcione, tengo que actualizar los elementos del heap constantemenete
        return heapSuperavits.consultarMax().id; //O(1)
    }
    
    public ArrayList<Integer> ciudadesConMayorGanancia(){ //O(1)
        return ciudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){ //O(1)
        return ciudadesMayorPerdida;
    }
    
    public int gananciaPromedioPorTraslado(){ //O(1)
        return (gananciaTotalPorTraslado / totalTrasladosDespachados);
    }
}