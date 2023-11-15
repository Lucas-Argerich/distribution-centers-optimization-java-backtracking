package abstractos;
import java.util.Objects;

public class Vertice<T> {
  T valor;

  public Vertice(T valor) {
    this.valor = valor;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Vertice<T> vertice = (Vertice<T>) obj;
    return valor == vertice.valor;
  }

  @Override
  public int hashCode() {
    return Objects.hash(valor);
  }
}