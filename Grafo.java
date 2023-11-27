import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstractos.Arista;
import abstractos.Vertice;

public class Grafo {
  private Map<Vertice, ArrayList<Arista>> grafo;
  // HashMap de (vertice, aristas)

  public Grafo() {
    this.grafo = new HashMap<Vertice, ArrayList<Arista>>();
  }
 
  public void addVertice(Vertice vertice) {
    grafo.put(vertice, new ArrayList<Arista>());
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
    return grafo.keySet().toArray(new Vertice[grafo.size()]);
  }
  
  public void addArista(Arista arista) {
    Vertice origen = arista.origen;
    Vertice destino = arista.destino;
    if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
      throw new IllegalArgumentException("Vertice no encontrado en el grafo");
    }

    ArrayList<Arista> aristas = grafo.get(origen);
    aristas.add(arista);
  }
  
  public ArrayList<Arista> getAristas(Vertice vertice) {
    return grafo.get(vertice);
  }

  public int size() {
    return grafo.size();
  }

  public void print() {
    for (Vertice vertice : grafo.keySet()) {
      ArrayList<Arista> aristas = grafo.get(vertice);
      System.out.print("Vertex " + vertice.valor + ": ");
      for (Arista arista : aristas) {
        System.out.print("(" + arista.destino.valor + ", " + arista.peso + ") ");
      }
      System.out.println();
    }
  }
}
