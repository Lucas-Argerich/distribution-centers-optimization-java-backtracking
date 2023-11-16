package abstractos;

public class Arista implements Comparable<Arista> {
  int origen;
  int destino;
  int peso;

  public Arista(int origen, int destino, int peso) {
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