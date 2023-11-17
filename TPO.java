import abstractos.Arista;
import abstractos.Centro;
import abstractos.Cliente;
import abstractos.Grafo;
import abstractos.Vertice;

public class TPO {
  public static void main(String[] args) {
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

    // Array solucion para cargar la mejor combinacion de centros en backtracking.
    Centro[] solucion = new Centro[Datos.CENTROS];

    // Costo minimo resultado del backtracking
    int menorCosto = backtracking(new Centro[Datos.CENTROS], getCentros(grafo), resultados, 0, Integer.MAX_VALUE,
        solucion);

    // Print solucion.
    printResultados(solucion, menorCosto);
  }

  private static int backtracking(
      Centro[] parcial, 
      Centro[] centros, 
      Dijkstra[] rutasClientes, 
      int etapa,
      int menorCosto,
      Centro[] solucion) {

    // Fin de las etapas.
    if (etapa == centros.length) {
      return menorCosto;
    }

    for (int i = etapa; i < centros.length; i++) {
      // Agregamos los centros de cada etapa.
      parcial[i] = centros[i];

      // Calculamos el costo total con el conjunto acutal.
      int candidatoMinCosto = calcularCostoTotal(parcial, rutasClientes);
      // Evaluamos si el conjunto actual es mejor.
      if (candidatoMinCosto < menorCosto) {
        // Actualizamos el menorCosto
        menorCosto = candidatoMinCosto;
        // Hacemos una copia del conjunto parcial en solucion.
        System.arraycopy(parcial, 0, solucion, 0, parcial.length);
      }

      int minCosto = backtracking(parcial, centros, rutasClientes, i + 1, menorCosto, solucion);
      // Actualizamos si se encontro un nuevo mejor costo.
      if (minCosto < menorCosto) menorCosto = minCosto;

      // Eliminamos el centro del parcial para el siguiente i.
      parcial[i] = null;
    }

    // Devolvemos el menor costo acutal.
    //  ya que al no ser por referencia no se cambia en ramas anteriores.
    return menorCosto;
  }

  private static int calcularCostoTotal(Centro[] centros, Dijkstra[] rutasClientes) {
    int costosVariables = 0;
    int costosFijos = 0;

    // Costos Variables (Costos de transporte de los clientes).
    for (Dijkstra rutasCliente : rutasClientes) {
      int costoCliente = calcularCostoCliente(rutasCliente, centros);
      costosVariables += costoCliente;
    }

    // Costos Fijos (Costos fijos de los puertos).
    for (Centro centro : centros) {
      if (centro == null)
        continue;
      costosFijos += centro.costoFijo;
    }

    return costosVariables + costosFijos;
  }

  private static int calcularCostoCliente(Dijkstra dijkstra, Centro[] centrosElegidos) {
    Arista camino = dijkstra.mejorCentro(centrosElegidos);
    
    if (camino == null) return Integer.MAX_VALUE; // No hay camino / Centro[] vacio.
    
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
