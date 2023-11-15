package abstractos;
public class Arista<T> {
  T origen;
  T destino;
  T peso;

  public Arista(T origen, T destino, T peso) {
    this.origen = origen;
    this.destino = destino;
    this.peso = peso;
  }
}