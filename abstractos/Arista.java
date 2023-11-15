package abstractos;
public class Arista<T> {
  private T origen;
  private T destino;
  private T peso;

  public Arista(T origen, T destino, T peso) {
    this.origen = origen;
    this.destino = destino;
    this.peso = peso;
  }

  public T getOrigen() {
    return origen;
  }

  public T getDestino() {
    return destino;
  }

  public T getPeso() {
    return peso;
  }
}