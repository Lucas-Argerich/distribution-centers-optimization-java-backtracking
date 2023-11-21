import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map.Entry;

import abstractos.Arista;
import abstractos.Centro;
import abstractos.Cliente;
import abstractos.Vertice;

public class Pseudo {
  public static void main(String[] args) {
    // Crear el Grafo.
    Grafo grafo = new Grafo();

    Datos.cargarVertices(grafo); // O(n)
    Datos.cargarRutas(grafo); // O(n)

    // Aplicar algoritmo de Dijkstra para cada cliente.
    Map<Cliente, Map<Vertice, Integer>> resultados = new HashMap<Cliente, Map<Vertice, Integer>>();

    for (Cliente cliente : getClientes(grafo)) { // O(n^3)
      Map<Vertice, Integer> dijkstra = dijkstra(grafo, cliente); // O(n^2)
      resultados.put(cliente, dijkstra);
    }

        // Array de combinaciones para backtracking. (-1: no visto, 0: no, 1: si)
    int[] combinacion = new int[Datos.CENTROS];
    Arrays.fill(combinacion, -1);

    // Array solucion para cargar la mejor combinacion de centros en backtracking.
    Centro[] solucion = new Centro[Datos.CENTROS];

    // Costo minimo resultado del backtracking
    int menorCosto = backtracking(combinacion, getCentros(grafo), resultados, 0, Integer.MAX_VALUE,
        solucion); // O(n^n)

    // Print solucion.
    printResultados(solucion, menorCosto);
  }

  private static Map<Vertice, Integer> dijkstra(Grafo grafo, Vertice origen) { // O(n^2)
    Map<Vertice, Integer> distancias = new HashMap<>();
    Queue<Vertice> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

    // Inicialización de distancias
    for (Vertice v : grafo.getVertices()) { // O(n)
      distancias.put(v, Integer.MAX_VALUE);
    }

    distancias.put(origen, 0);

    colaPrioridad.add(origen);

    while (!colaPrioridad.isEmpty()) { // O(n^2)
      Vertice actual = colaPrioridad.poll();

      for (Arista arista : grafo.getAristas(actual.valor)) { // O(n)
        Vertice vecino = arista.destino;
        int nuevaDistancia = distancias.get(actual) + arista.peso;
        if (nuevaDistancia < distancias.get(vecino)) {
          distancias.put(vecino, nuevaDistancia);
          colaPrioridad.add(vecino);
        }
      }
    }

    return distancias;
  }

  private static int backtracking(
      int[] combinacion,
      Centro[] centros,
      Map<Cliente, Map<Vertice, Integer>> rutasClientes,
      int etapa,
      int menorCosto,
      Centro[] solucion) { // O(n^n)

    // Fin de las etapas.
    if (etapa == centros.length) {
      return calcularCostoTotal(combinacion, centros, rutasClientes);
    }

    int costoActual = calcularCostoTotal(combinacion, centros, rutasClientes);

    // Iteramos sobre los distintos estados de la nueva etapa.
    for (int i = 0; i <= 1; i++) {
      combinacion[etapa] = i;
      // Costo de la nueva combinacion.
      int costo = calcularCostoTotal(combinacion, centros, rutasClientes);
      // Poda si el costo es mayor al costo del padre.
      if (costo > costoActual)
        continue;
      // Buscamos el mejor candidato entre los hijos.
      int mejorCandidato = backtracking(combinacion, centros, rutasClientes, etapa + 1, menorCosto, solucion);
      // Guardamos el resultado.
      if (mejorCandidato < menorCosto) {
        menorCosto = mejorCandidato;
        if (i == 1) {
          solucion[etapa] = centros[etapa];
        } else {
          solucion[etapa] = null;
        }
      }
      combinacion[etapa] = -1;
    } 

    // Devolvemos el menor costo acutal.
    // ya que al no ser por referencia no se cambia en ramas anteriores.
    return menorCosto;
  }

  private static int calcularCostoTotal(int[] combinacion, Centro[] centros, Map<Cliente, Map<Vertice, Integer>> datosClientes) { // O(n^2)
    int costosVariables = 0;
    int costosFijos = 0;

    Centro[] parcial = new Centro[combinacion.length];
    for (int i = 0; i < combinacion.length; i++) {
      if (combinacion[i] != 1)
        continue;
      parcial[i] = centros[i];
    }

    // Costos Variables (Costos de transporte de los clientes).
    for (Entry<Cliente, Map<Vertice, Integer>> datosCliente : datosClientes.entrySet()) { // O(n^2)
      Cliente cliente = datosCliente.getKey();
      Map<Vertice, Integer> rutasCliente = datosCliente.getValue();

      int costoCliente = calcularCostoCliente(cliente, rutasCliente, parcial); // O(n)
      
      if (costoCliente == Integer.MAX_VALUE) {
        return Integer.MAX_VALUE;
      }
      
      costosVariables += costoCliente;
    }

    // Costos Fijos (Costos fijos de los puertos).
    for (Centro centro : parcial) { // O(n)
      if (centro == null)
        continue;
      costosFijos += centro.costoFijo;
    }

    return costosVariables + costosFijos;
  }

  private static int calcularCostoCliente(Cliente cliente, Map<Vertice, Integer> rutasCliente,
      Centro[] centrosElegidos) { // O(n)
    int mejorDistancia = Integer.MAX_VALUE;
    Centro mejorCentro = null;

    for (Centro centro : centrosElegidos) {
      if (centro == null)
        continue;

      int distancia = rutasCliente.get(centro);

      if (distancia < mejorDistancia) {
        mejorDistancia = distancia;
        mejorCentro = centro;
      }
    }
    if (mejorCentro == null)
      return Integer.MAX_VALUE;

    // Transporte a centro = cliente.volumen * (peso total del recorrido).
    // Transporte a puerto = cliente.volumen * centro.costoUnitario
    return cliente.volumen * (mejorDistancia + mejorCentro.costoUnitario);
  }

  private static Centro[] getCentros(Grafo grafo) { // O(n)
    Centro[] centros = new Centro[Datos.CENTROS];
    int i = 0;

    for (Vertice v : grafo.getVertices()) {
      if (v instanceof Centro) {
        centros[i++] = (Centro) v;
      }
    }

    return centros;
  }

  private static Cliente[] getClientes(Grafo grafo) { // O(n)
    Cliente[] clientes = new Cliente[Datos.CLIENTES];
    int i = 0;

    for (Vertice v : grafo.getVertices()) {
      if (v instanceof Cliente) {
        clientes[i++] = (Cliente) v;
      }
    }

    return clientes;
  }

  private static void printResultados(Centro[] centros, int costo) {
    System.out.print("Mejores candidatos: [");
    for (Centro centro : centros) {
      if (centro == null)
        continue;
      System.out.print(" Centro-" + centro.valor);
    }
    System.out.println(" ] \nCosto Anual: " + costo);
  }
}
