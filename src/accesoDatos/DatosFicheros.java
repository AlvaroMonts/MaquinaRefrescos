package accesoDatos;

import java.io.File;
import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class DatosFicheros {
	private File archivoMonedas;
	private File archivoProductos;
	private HashMap<Integer, Deposito> datosMonedas;
	private HashMap<String, Dispensador> datosProductos;

	public HashMap<Integer, Deposito> obtenerDepositos() {
		Deposito dep2eu = new Deposito("2 euros", 200, 1000);
		Deposito dep1eu = new Deposito("1 euro", 100, 2000);
		Deposito dep50cent = new Deposito("50 cent", 50, 3000);
		Deposito dep20cent = new Deposito("20 cent", 20, 4000);
		Deposito dep10cent = new Deposito("10 cent", 10, 5000);
		Deposito dep5cent = new Deposito("5 cent", 5, 6000);
		
		datosMonedas.put(1, dep2eu);
		datosMonedas.put(2, dep1eu);
		datosMonedas.put(3, dep50cent);
		datosMonedas.put(4, dep20cent);
		datosMonedas.put(5, dep10cent);
		datosMonedas.put(6, dep5cent);
		
		return datosMonedas;
	}

	public HashMap<String, Dispensador> obtenerDispensadores() {
		return datosProductos;
	}

	

}
