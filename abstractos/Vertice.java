package abstractos;
import java.util.Objects;

public abstract class Vertice {
  int valor;

  public Vertice(int valor) {
    this.valor = valor;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Vertice vertice = (Vertice) obj;
    return valor == vertice.valor;
  }

  @Override
  public int hashCode() {
    return Objects.hash(valor);
  }
}