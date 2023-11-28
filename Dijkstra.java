import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import abstractos.Arista;
import abstractos.Centro;
import abstractos.Vertice;

public class Dijkstra {
  public Vertice origen;
  private Map<Vertice, Integer> distancias;
  private PriorityQueue<Vertice> colaPrioridad;

  Dijkstra(Grafo grafo, Vertice origen) {
    this.origen = origen;
    distancias = new HashMap<Vertice, Integer>();
    colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

    // Inicialización de distancias
    for (Vertice v : grafo.getVertices()) {
      distancias.put(v, Integer.MAX_VALUE);
    }

    distancias.put(origen, 0);

    colaPrioridad.add(origen);

    while (!colaPrioridad.isEmpty()) {
      Vertice actual = colaPrioridad.poll();

      for (Arista arista : grafo.getAristas(actual)) {
        Vertice vecino = arista.destino;
        int nuevaDistancia = distancias.get(actual) + arista.peso;
        if (nuevaDistancia < distancias.get(vecino)) {
          distancias.put(vecino, nuevaDistancia);
          colaPrioridad.add(vecino);
        }
      }
    }
  }

  public int getDistancia(Vertice vertice) {
    return distancias.get(vertice);
  }

  public Arista mejorCentro(Centro[] centros) {
    int mejorDistancia = Integer.MAX_VALUE;
    Centro mejor = null;

    for (Centro centro : centros) {
      if (centro == null) continue;

      int distancia = this.getDistancia(centro);

      if (distancia < mejorDistancia) {
        mejorDistancia = distancia;
        mejor = centro;
      }
    }

    if (mejor == null) return null;
    return new Arista(origen, mejor, mejorDistancia);
  }

  public void printMejorCentro(Centro[] centros) {
    Arista mejorRuta = this.mejorCentro(centros);
    System.out.println("Cliente " + mejorRuta.origen.valor + " -> Centro " +
        mejorRuta.destino.valor + ". Distancia: "
        + mejorRuta.peso);
  }
}
