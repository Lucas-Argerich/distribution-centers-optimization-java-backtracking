package abstractos;
public class Centro<T> extends Vertice<T> {
  T costoUnitario;
  T costoFijo;

  public Centro(T valor, T costoUnitario, T costoFijo) {
    super(valor);
    this.costoUnitario = costoUnitario;
    this.costoFijo = costoFijo;
  }

  public T getCostoUnitario() {
    return costoUnitario;
  }

  public T getCostoFijo() {
    return costoFijo;
  }
}
