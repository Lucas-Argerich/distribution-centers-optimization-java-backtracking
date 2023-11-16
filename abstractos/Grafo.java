package abstractos;
import java.util.HashMap;
import java.util.Map;

public class Grafo {
  private Map<Vertice, MinHeap> grafo;
  // HashMap de (vertice, aristas)

  public Grafo() {
    this.grafo = new HashMap<Vertice, MinHeap>();
  }
 
  public void addVertice(Vertice vertice) {
    grafo.put(vertice, new MinHeap());
  }

  public Vertice getVertice(int valor) {
    for (Vertice vertice : grafo.keySet()) {
      if (vertice.valor == valor) {
        return vertice;
      }
    }
    return null;
  }

  public Vertice[] getVertices() {
    return (Vertice[])grafo.keySet().toArray();
  }
  
  public void addArista(Arista arista) {
    Vertice origen = this.getVertice(arista.origen);
    Vertice destino = this.getVertice(arista.destino);
    if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
      throw new IllegalArgumentException("Vertice no encontrado en el grafo");
    }

    MinHeap aristas = grafo.get(origen);
    aristas.insert(arista);
  }
  
  public MinHeap getAristas(int vertice) {
    Vertice origen = this.getVertice(vertice);
    if (!grafo.containsKey(origen)) {
      throw new IllegalArgumentException("Vertice no encontrado en el grafo");
    }

    return grafo.get(origen);
  }

  public int size() {
    return grafo.size();
  }

  public void print() {
    for (Vertice vertice : grafo.keySet()) {
      MinHeap aristas = grafo.get(vertice);
      System.out.print("Vertex " + vertice.valor + ": ");
      for (Arista arista : aristas) {
        System.out.print("(" + arista.destino + ", " + arista.peso + ") ");
      }
      System.out.println();
    }
  }
}
