package abstractos;

public class Arista implements Comparable<Arista> {
  public Vertice origen;
  public Vertice destino;
  public int peso;

  public Arista(Vertice origen, Vertice destino, int peso) {
    super();
    this.origen = origen;
    this.destino = destino;
    this.peso = peso;
  }

  @Override
  public int compareTo(Arista otro) {
    return Integer.compare(this.peso, otro.peso);
  }
}