package abstractos;
public class Centro extends Vertice {
  int costoUnitario;
  int costoFijo;

  public Centro(int valor, int costoUnitario, int costoFijo) {
    super(valor);
    this.costoUnitario = costoUnitario;
    this.costoFijo = costoFijo;
  }
}
