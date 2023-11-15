import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import abstractos.Arista;
import abstractos.Centro;
import abstractos.Cliente;
import abstractos.Grafo;

public class Datos {
  public static int CLIENTES;
  public static int CENTROS;
  public static int VERTICES;
  public static int RUTAS;

  public static void cargarVertices(Grafo<Integer> grafo) {
    File archivoVertices = new File("archivos\\clientesycentros.txt");

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(archivoVertices));

      CLIENTES = Integer.parseInt(br.readLine().split("#")[0].trim());
      CENTROS = Integer.parseInt(br.readLine().split("#")[0].trim());
      VERTICES = CLIENTES * CENTROS;

      String str = br.readLine();

      while (str != null) {
        String[] params = str.split("#")[0].trim().split(",");
        int valor = Integer.parseInt(params[0]);

        if (params.length == 3) { // es un centro de distribucion
          int costoUnitario = Integer.parseInt(params[1]);
          int costoFijo = Integer.parseInt(params[2]);

          Centro<Integer> centro = new Centro<Integer>(CLIENTES + valor, costoUnitario, costoFijo);
          grafo.addVertice(centro);
        } else { // es un cliente
          int volumen = Integer.parseInt(params[1]);

          Cliente<Integer> cliente = new Cliente<Integer>(valor, volumen);
          grafo.addVertice(cliente);
        }

        str = br.readLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Vertices cargados.");
  }

  public static void cargarRutas(Grafo<Integer> grafo) {
    File archivoRutas = new File("archivos\\rutas.txt");

    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(archivoRutas));

      RUTAS = Integer.parseInt(br.readLine().split("#")[0].trim());

      String str = br.readLine();

      while (str != null) {
        String[] params = str.split("#")[0].trim().split(",");

        int nodoOrigen = Integer.parseInt(params[0]);
        int nodoDestino = Integer.parseInt(params[1]);
        int coste = Integer.parseInt(params[2]);

        Arista<Integer> arista = new Arista<Integer>(nodoOrigen, nodoDestino, coste);
        grafo.addArista(arista);

        str = br.readLine();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Rutas cargadas.");
  }
}
