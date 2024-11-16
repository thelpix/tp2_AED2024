package aed;

import java.util.ArrayList;

public class BestEffort {
    private int cantCiudades;
    private Traslado[] traslados;
    private ArrayList<Integer> ciudadesMayorGanancia; //donde acumulo para retornar
    private ArrayList<Integer> ciudadesMayorPerdida; //x2
    private int MayorGanancia; //variable que comparará la ciudad mas rentable
    private int MayorPerdida; //lo mismo pero en perdidas
    private ComparatorAntiguedad comparatorAntiguedad;
    private ComparatorRedituabilidad comparatorRedituabilidad;
    //usar un array y arraylist para superavit?
    
    public BestEffort(int cantCiudades, Traslado[] traslados){
        int[] ganancias = new int[cantCiudades]; //O(|C|),
        int[] perdidas = new int[cantCiudades]; //O(|C|), arrays de tamaño fijos
        
        //esto seria O(|C| + |C|) = O(|C|)
        
        //heapRedituabilidad = buildHeapFloyd(traslados, comparatorRedituabilidad); //O(|T|) asumiendo que use Algoritmo de Floyd, Max-Heap
        //heapAntiguedad = buildHeapFloyd(traslados, comparatorAntiguedad); //O(|T|), es como un Min-Heap
        
        //esto seria O(|T| + |T|) = O(|T|)
        //complejidad final: O(|C| + |T|)
    }
    
    public void registrarTraslados(Traslado[] traslados){
        int i = 0; //O(1)
        while(i < traslados.length){ //O(|traslados|)
            //heapRedituabilidad.encolar(traslados[i]); //O(log(|T|)), el constructor se encarga de asignarle posicion en c/u InfoTraslados
            //heapAntiguedad.encolar(traslados[i]); //O(log(|T|))
            i++; //O(1)
        }
        //complejidad = O(|traslados|*log(|T|))
    }
    
    public int[] despacharMasRedituables(int n){ //O(n(log|T|) + log(|C|))
        int i = 0; //O(1)
        int[] res = new int[n];
        while(i < n){ //O(n)
            //Desencolar n veces
            /*
            Object traslado = heapRedituabilidad.desencolarMax(); //O(log(|T|))
            res[i] = traslado; //cuidado con el aliasing upsi
            
            heapAntiguedad.borrarPos(traslado.posicionHeapAntiguedad)
            //al borrar un traslado, debo modificar heapAntiguedad, pero como se la posicion a borrar, no la tengo que encontrar
            //pasaria de O(|T|log(|T|)) -> O(log(|T|))
            
            //Parte de Ciudades (DIFICIL), podria modularizarlo en otra funcion privada
            
            perdidas[traslado.origen] =+ traslado.gananciaNeta; //O(1) no?
            ganacia[traslado.destino] =+ traslado.gananciaNeta; //O(1) no?
            //usare dos variables temporales para comparar
            int perdidaAux = perdidas[traslados.origen]; //O(1)
            int gananciaAux = ganancia[traslado.destino]; //O(1)
            
            if(perdidaAux > MayorPerdida){ //creo que el if es O(1)
                MayorPerdida = perdidaAux; //O(1)
                ciudadesMayorPerdida = [traslado.origen]; //O(1)
            }
            else if(perdidaAux == MayorPerdida){ //creo que sigue siendo O(1) ???
                ciudadesMayorPerdida.add(traslado.origen);
                //O(1) amortizado, en https://stackoverflow.com/questions/45220908/why-arraylist-add-and-addint-index-e-complexity-is-amortized-constant-time
                //lo explican el por qué (y en el TP tambien)
            }
            
            if(gananciaAux > MayorGanancia){ //creo que el if es O(1)
                MayorGanancia = gananciaAux; //O(1)
                ciudadesMayorGanancia = [traslado.destino]; //O(1)
            }
            else if(gananciaAux == MayorGanancia){ //creo que sigue siendo O(1) ???
                ciudadesMayorGanancia.add(traslado.destino); //O(1) amortizado
            }
            */
            //para el superAvit podria usar algun otro array y otra variable me imagino, y seguiria siendo O(1)
        }
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
                /*
                Object traslado = heapAntiguedad.desencolarMax(); //O(log(|T|))
                res[i] = traslado; //cuidado con el aliasing upsi
                
                heapRedituabilidad.borrarPos(traslado.posicionHeapRedituabilidad)
                //al borrar un traslado, debo modificar heapAntiguedad, pero como se la posicion a borrar, no la tengo que encontrar
                //pasaria de O(|T|log(|T|)) -> O(log(|T|))
                
                //Parte de Ciudades (DIFICIL), podria modularizarlo en otra funcion privada
                
                perdidas[traslado.origen] =+ traslado.gananciaNeta; //O(1) no?
                ganacia[traslado.destino] =+ traslado.gananciaNeta; //O(1) no?
                //usare dos variables temporales para comparar
                int perdidaAux = perdidas[traslados.origen]; //O(1)
                int gananciaAux = ganancia[traslado.destino]; //O(1)
                
                if(perdidaAux > MayorPerdida){ //creo que el if es O(1)
                    MayorPerdida = perdidaAux; //O(1)
                    ciudadesMayorPerdida = [traslado.origen]; //O(1)
                }
                else if(perdidaAux == MayorPerdida){ //creo que sigue siendo O(1) ???
                    ciudadesMayorPerdida.add(traslado.origen);
                    //O(1) amortizado, en https://stackoverflow.com/questions/45220908/why-arraylist-add-and-addint-index-e-complexity-is-amortized-constant-time
                    //lo explican el por qué (y en el TP tambien)
                }
                
                if(gananciaAux > MayorGanancia){ //creo que el if es O(1)
                    MayorGanancia = gananciaAux; //O(1)
                    ciudadesMayorGanancia = [traslado.destino]; //O(1)
                }
                else if(gananciaAux == MayorGanancia){ //creo que sigue siendo O(1) ???
                    ciudadesMayorGanancia.add(traslado.destino); //O(1) amortizado
                }
                */
                //para el superAvit podria usar algun otro array y otra variable me imagino, y seguiria siendo O(1)
            }
                //como hay muchos O(1), en complejidad asintotica no se cuentan por constantes
                //complejidad final = O(n(log|T| + log|T|)) -> O(n(log|T|))
                //capaz cuando haga el superAvit o las gananciasPromedio ahi si me de log(|C|)
            return res;    
    }
    
    public int ciudadConMayorSuperavit(){
        //aca tendria que ver, pero podria como dije arriba capaz usar otro array y variables auxiliares para sacar el mayor superAvit?
        return 0;
    }
    
    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesMayorPerdida;
    }
    
    public int gananciaPromedioPorTraslado(){
        //me falta este, supongo que tengo que usar mas variables y aprovechar en hacerlo en los despachar, de forma O(1) ah
        return 0;
    }
}
