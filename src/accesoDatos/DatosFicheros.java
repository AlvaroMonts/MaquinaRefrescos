package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class DatosFicheros implements Datos {
	private File archivoMonedas = new File(
			"C:/Users/alvaro.montes/git/adat-ud1-a03-maquina-refrescos-ficheros-AlvaroMonts/datosFicheros/datosMonedas.txt");
	private File archivoProductos = new File(
			"C:/Users/alvaro.montes/git/adat-ud1-a03-maquina-refrescos-ficheros-AlvaroMonts/datosFicheros/datosProductos.txt");

	private HashMap<Integer, Deposito> datosMonedas = new HashMap<Integer, Deposito>(); // depositos
	private HashMap<String, Dispensador> datosProductos = new HashMap<String, Dispensador>(); // dispensadores

	public HashMap<Integer, Deposito> obtenerDepositos() {
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(archivoMonedas);
			br = new BufferedReader(fr);

			String linea = new String();
			for (int j = 0; (linea = br.readLine()) != null; j++) {
				String sar[] = linea.split(",");
				Deposito dep = new Deposito(sar[0], Integer.parseInt(sar[1]), Integer.parseInt(sar[2]));
				datosMonedas.put(j, dep);
			}

			/*
			 * for (int i = 0; i < datosMonedas.size(); i++) {
			 * datosMonedas.get(i); }
			 */

			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha sido encontrado");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Hay depositos que no estan creados correctamente");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datosMonedas;
	}

	public HashMap<String, Dispensador> obtenerDispensadores() {
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(archivoProductos);
			br = new BufferedReader(fr);

			String linea = new String();
			for (int j = 0; (linea = br.readLine()) != null; j++) {
				String sar[] = linea.split(",");
				Dispensador disp = new Dispensador(sar[0], sar[3], Integer.parseInt(sar[2]), Integer.parseInt(sar[1]));
				datosProductos.put(j + "", disp);
			}

			/*
			 * for (int i = 0; i < datosProductos.size(); i++) {
			 * datosProductos.get(i); }
			 */

			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha sido encontrado");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Hay dispensadores que no estan creados correctamente");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datosProductos;
	}

	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		datosMonedas = depositos;
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(archivoMonedas);
			bw = new BufferedWriter(fw);

			for (int i = 0; i < depositos.size(); i++) {
				bw.write(datosMonedas.get(i).getNombreMoneda() + "," + datosMonedas.get(i).getValor() + ","
						+ datosMonedas.get(i).getCantidad());
				bw.newLine();
			}
			bw.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha sido encontrado");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		datosProductos = dispensadores;
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(archivoProductos);
			bw = new BufferedWriter(fw);
			// String clave, String nombre, int precio, int inicial
			// clave0, uds3, precio2, nombre1

			for (String clv : dispensadores.keySet()) {
				bw.write(dispensadores.get(clv).getClave() + "," + dispensadores.get(clv).getCantidad() + ","
						+ dispensadores.get(clv).getPrecio() + "," + dispensadores.get(clv).getNombreProducto());
				bw.newLine();
			}

			bw.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha sido encontrado");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
