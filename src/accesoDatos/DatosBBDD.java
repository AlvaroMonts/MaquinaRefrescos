package accesoDatos;

import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class DatosBBDD implements Datos {
	public DatosBBDD(){
		
	}

	public HashMap<Integer, Deposito> obtenerDepositos() {
		return null;
	}

	public HashMap<String, Dispensador> obtenerDispensadores() {
		return null;
	}

	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		return false;
	}

	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		return false;
	}
}
