package abstractos;

public class Arista {
  public Vertice origen;
  public Vertice destino;
  public int peso;

  public Arista(Vertice origen, Vertice destino, int peso) {
    super();
    this.origen = origen;
    this.destino = destino;
    this.peso = peso;
  }
}