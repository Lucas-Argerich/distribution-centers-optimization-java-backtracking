import abstractos.Arista;
import abstractos.Centro;
import abstractos.Cliente;
import abstractos.Grafo;
import abstractos.Vertice;

public class TPO {
  public static void main(String[] args) {
    Grafo grafo = new Grafo();

    Datos.cargarVertices(grafo);
    Datos.cargarRutas(grafo);

    // grafo.print();

    // Aplicar algoritmo de Dijkstra desde cada cliente a cada vertice.
    Dijkstra[] resultados = new Dijkstra[Datos.CLIENTES];
    int d = 0;

    for (Cliente cliente : getClientes(grafo)) {
      Dijkstra dijkstra = new Dijkstra(grafo, cliente);
      resultados[d++] = dijkstra;
      // Arista mejorRuta = dijkstra.mejorCentro(getCentros(grafo));
      // System.out.println("Cliente " + mejorRuta.origen.valor + " -> Centro " +
      // mejorRuta.destino.valor + ". Distancia: "
      // + mejorRuta.peso);
    }

    Centro[] solucion = new Centro[Datos.CENTROS];
    int menorCosto = backtracking(new Centro[Datos.CENTROS], getCentros(grafo), resultados, 0, Integer.MAX_VALUE, solucion);

    System.out.print("Mejores candidatos: [");
    for (Centro centro : solucion) {
      if (centro == null) continue;
      System.out.print(" Centro-" + centro.valor);
    }
    System.out.println(" ] \nCosto Anual: " + menorCosto);
  }

  private static int backtracking(Centro[] parcial, Centro[] centros, Dijkstra[] rutasClientes, int etapa,
      int menorCosto,
      Centro[] solucion) {

    if (etapa == centros.length) {
      return menorCosto;
    }

    for (int i = etapa; i < centros.length; i++) {
      parcial[i] = centros[i];

      int candidatoMinCosto = calcularCostoTotal(parcial, rutasClientes);
      
      if (candidatoMinCosto < menorCosto) {
        menorCosto = candidatoMinCosto;
        System.arraycopy(parcial, 0, solucion, 0, parcial.length);
      }
      
      int minCosto = backtracking(parcial, centros, rutasClientes, i + 1, menorCosto, solucion);
      if (minCosto < menorCosto) menorCosto = minCosto; //  el parcial se copio en el backtracking

      parcial[i] = null;
    }

    return menorCosto;
  }

  // mejor = 13390 (null, 51, 52, null, null, null, null 57)
  private static int calcularCostoTotal(Centro[] centros, Dijkstra[] rutasClientes) {
    int costosVariables = 0;
    int costosFijos = 0;

    // Costos Variables
    for (Dijkstra rutasCliente : rutasClientes) {
      int costoCliente = calcularCostoCliente(rutasCliente, centros);
      costosVariables += costoCliente;
    }

    // Costos Fijos
    for (Centro centro : centros) {
      if (centro == null)
        continue;
      costosFijos += centro.costoFijo;
    }

    return costosVariables + costosFijos;
  }

  // Costo transporte a centro = cliente.volumen * dijkstra(Cliente).peso <- peso
  // del recorrido
  // Costo transporte a puerto = cliente.volumen * centro.costoUnitario
  // Costo cliente = costo transporte a centro + costo transporte a puerto
  // Costo total (en el backtrack) = costo cliente + costo fijo centro
  private static int calcularCostoCliente(Dijkstra dijkstra, Centro[] centrosElegidos) {
    Arista camino = dijkstra.mejorCentro(centrosElegidos);
    Cliente cliente = (Cliente) camino.origen;
    Centro centro = (Centro) camino.destino;

    if (centro == null) return Integer.MAX_VALUE; // si todos los centros son null

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
}
