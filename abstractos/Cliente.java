package abstractos;
public class Cliente<T> extends Vertice<T> {
  T volumen;

  public Cliente(T valor, T volumen) {
    super(valor);
    this.volumen = volumen;
  }
}
