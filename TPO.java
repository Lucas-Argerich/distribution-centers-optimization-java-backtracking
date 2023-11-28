import java.util.Arrays;

import abstractos.Arista;
import abstractos.Centro;
import abstractos.Cliente;
import abstractos.Vertice;

public class TPO {
  static int EVALUACIONES_BACKTRACKING = 0;
  static int PODAS = 0;

  public static void main(String[] args) {
    long tiempoInicio = System.currentTimeMillis();

    // Crear el Grafo.
    Grafo grafo = new Grafo();

    Datos.cargarVertices(grafo);
    Datos.cargarRutas(grafo);

    // grafo.print();

    // Aplicar algoritmo de Dijkstra para cada cliente.
    Dijkstra[] resultados = new Dijkstra[Datos.CLIENTES];
    int d = 0;

    for (Cliente cliente : getClientes(grafo)) {
      Dijkstra dijkstra = new Dijkstra(grafo, cliente);

      // dijkstra.printMejorCentro(getCentros(grafo));

      resultados[d++] = dijkstra;
    }

    // Array de combinaciones para backtracking. (-1: no visto, 0: no, 1: si)
    int[] combinacion = new int[Datos.CENTROS];
    Arrays.fill(combinacion, -1);

    // Array solucion para cargar la mejor combinacion de centros en backtracking.
    Centro[] solucion = new Centro[Datos.CENTROS];

    // Costo minimo resultado del backtracking.
    int menorCosto = backtracking(combinacion, getCentros(grafo), resultados, 0, Integer.MAX_VALUE,
        solucion);

    long tiempoFin = System.currentTimeMillis();
    long tiempoEjecucion = tiempoFin - tiempoInicio;

    // Print solucion.
    printResultados(solucion, menorCosto);

    System.out.println("Evaluaciones de Backtracking: " + EVALUACIONES_BACKTRACKING);
    System.out.println("Podas efectuadas: " + PODAS);
    System.out.println("Evaluaciones Esperadas sin Poda: " + (int)(Math.pow(2, Datos.CENTROS + 1) - 1));

    System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " ms.");
  }

  private static int backtracking(
      int[] combinacion,
      Centro[] centros,
      Dijkstra[] rutasClientes,
      int etapa,
      int menorCosto,
      Centro[] solucion) {

    EVALUACIONES_BACKTRACKING += 1;

    // Costo de la combinacion actual.
    int costoActual = calcularCostoTotal(combinacion, centros, rutasClientes);

    // Caso base.
    if (etapa == centros.length) {
      return costoActual;
    }

    // Iteramos sobre los distintos estados de la nueva etapa.
    for (int i = 0; i <= 1; i++) { // 0, 1
      combinacion[etapa] = i;
      // Costo de la nueva combinacion.
      int costo = calcularCostoTotal(combinacion, centros, rutasClientes);
      // Poda si el costo es mayor al costo del padre.
      if (costo <= costoActual) {
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
      } else {
        PODAS += 1;
      }
      combinacion[etapa] = -1;
    }

    // Devolvemos el menor costo acutal.
    // ya que al no ser por referencia no se cambia en ramas anteriores.
    return menorCosto;
  }

  private static int calcularCostoTotal(int[] combinacion, Centro[] centros, Dijkstra[] rutasClientes) {
    int costosVariables = 0;
    int costosFijos = 0;

    Centro[] parcial = new Centro[combinacion.length];
    for (int i = 0; i < combinacion.length; i++) {
      if (combinacion[i] != 1)
        continue;
      parcial[i] = centros[i];
    }

    // Costos Variables (Costos de transporte de los clientes).
    for (Dijkstra rutasCliente : rutasClientes) {
      int costoCliente = calcularCostoCliente(rutasCliente, parcial);

      if (costoCliente == Integer.MAX_VALUE) {
        return Integer.MAX_VALUE;
      }

      costosVariables += costoCliente;
    }

    // Costos Fijos (Costos fijos de los puertos).
    for (Centro centro : parcial) {
      if (centro == null)
        continue;
      costosFijos += centro.costoFijo;
    }

    return costosVariables + costosFijos;
  }

  private static int calcularCostoCliente(Dijkstra dijkstra, Centro[] centrosElegidos) {
    Arista camino = dijkstra.mejorCentro(centrosElegidos);

    if (camino == null)
      return Integer.MAX_VALUE; // No hay camino / Centro[] vacio.

    Cliente cliente = (Cliente) camino.origen;
    Centro centro = (Centro) camino.destino;

    // Transporte a centro = cliente.volumen * (peso total del recorrido).
    // Transporte a puerto = cliente.volumen * centro.costoUnitario
    return cliente.volumen * (camino.peso + centro.costoUnitario);
  }

  private static Centro[] getCentros(Grafo grafo) {
    Centro[] centros = new Centro[Datos.CENTROS];
    int i = 0;

    for (Vertice v : grafo.getVertices()) {
      if (v instanceof Centro) {
        centros[i++] = (Centro) v;
      }
    }

    return centros;
  }

  private static Cliente[] getClientes(Grafo grafo) {
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
