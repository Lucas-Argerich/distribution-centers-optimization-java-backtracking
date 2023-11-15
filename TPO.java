import abstractos.Grafo;

public class TPO {
  public static void main(String[] args) {

    Grafo<Integer> grafo = new Grafo<Integer>();

    Datos.cargarVertices(grafo);
    Datos.cargarRutas(grafo);

    grafo.print();
  }
}
