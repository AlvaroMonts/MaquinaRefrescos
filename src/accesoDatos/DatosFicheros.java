package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class DatosFicheros {
	private File archivoMonedas = new File(
			"C:/Users/alvaro.montes/git/adat-ud1-a03-maquina-refrescos-ficheros-AlvaroMonts/datosFicheros/datosMonedas");
	private File archivoProductos = new File(
			"C:/Users/alvaro.montes/git/adat-ud1-a03-maquina-refrescos-ficheros-AlvaroMonts/datosFicheros/datosProductos");
	private HashMap<Integer, Deposito> datosMonedas;
	private HashMap<String, Dispensador> datosProductos;

	public HashMap<Integer, Deposito> obtenerDepositos() {
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(archivoMonedas);
			br = new BufferedReader(fr);
			Deposito depositos[] = new Deposito[6];
			String linea = new String();
			for (int j = 0; (linea = br.readLine()) != null; j++) {
				String sar[] = linea.split(",");
				Deposito dep = new Deposito(sar[3], Integer.parseInt(sar[0]), Integer.parseInt(sar[1]));
				depositos[j] = dep;
			}

			for (int i = 0; i < datosMonedas.size(); i++) {
				datosMonedas.put(i, depositos[i]);
			}
/*
			for (int i = 0; i < datosMonedas.size(); i++) {
				datosMonedas.get(i);
			}
			*/
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datosMonedas;
	}

	public HashMap<String, Dispensador> obtenerDispensadores() {
		return datosProductos;
	}

}
