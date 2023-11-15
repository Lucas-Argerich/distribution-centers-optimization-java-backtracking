package abstractos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo<T> {
  private Map<Vertice<T>, List<Arista<T>>> grafo;

  public Grafo() {
    this.grafo = new HashMap<Vertice<T>, List<Arista<T>>>();
  }

  public Vertice<T> getVertice(T valor) {
    for (Vertice<T> vertice : grafo.keySet()) {
      if (vertice.valor == valor) {
        return vertice;
      }
    }
    return null;
  }

  public void addVertice(Vertice<T> vertice) {
    grafo.put(vertice, new ArrayList<>());
  }

  public void addArista(Arista<T> arista) {
    Vertice<T> origen = this.getVertice(arista.origen);
    Vertice<T> destino = this.getVertice(arista.destino);
    if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
      throw new IllegalArgumentException("Vertice no encontrado en el grafo");
    }

    List<Arista<T>> aristas = grafo.get(origen);
    aristas.add(arista);
  }

  public List<Arista<T>> getAristas(T vertice) {
    Vertice<T> origen = this.getVertice(vertice);
    if (!grafo.containsKey(origen)) {
      throw new IllegalArgumentException("Vertice no encontrado en el grafo");
    }

    return grafo.get(origen);
  }

  public void print() {
    for (Vertice<T> vertice : grafo.keySet()) {
      List<Arista<T>> aristas = grafo.get(vertice);
      System.out.print("Vertex " + vertice.valor + ": ");
      for (Arista<T> arista : aristas) {
        System.out.print("(" + arista.destino + ", " + arista.peso + ") ");
      }
      System.out.println();
    }
  }
}
