package accesoDatos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class DatosBBDD implements Datos {
	private Connection conection;
	private HashMap<Integer, Deposito> datosMonedas; // depositos
	private HashMap<String, Dispensador> datosProductos; // dispensadores

	public DatosBBDD() {
		datosMonedas = new HashMap<Integer, Deposito>();
		datosProductos = new HashMap<String, Dispensador>();

		try {
			HashMap<String, String> hmret = loadFichero("bbdd.ini");
			Class.forName("com.mysql.jdbc.Driver");
			conection = DriverManager.getConnection(hmret.get("url"), hmret.get("login"), hmret.get("pwd"));
			System.out.println("Conectado a bbdd");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no cargado");
		} catch (SQLException e) {
			System.out.println("Error de Conexión con MySQL");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, String> loadFichero(String nomFichero) throws IOException {
		HashMap<String, String> hmDatos = new HashMap<String, String>();
		FileReader fr = new FileReader(nomFichero);
		BufferedReader bf = new BufferedReader(fr);
		String lineaLeida = bf.readLine();
		while (lineaLeida != null) {
			String sar[] = lineaLeida.split("=");
			if (sar[0].equals("pwd"))
				hmDatos.put(sar[0], "");
			// porque la conexion que tengo no tiene contraseña (y no se
			// ponerla)
			else
				hmDatos.put(sar[0], sar[1]);
			lineaLeida = bf.readLine();
		}
		bf.close();
		fr.close();
		return hmDatos;
	}

	public HashMap<Integer, Deposito> obtenerDepositos() {

		try {
			String texto = new String();
			String texto2 = new String();
			String texto3 = new String();
			String sql = "INSERT INTO `bbddmaquinarefrescos`.`depositos` (`valor`, `cantidad`, `nombre`) VALUES (?,?,?);";
			PreparedStatement stmt = (PreparedStatement) conection.prepareStatement(sql);
			stmt.setString(1, texto);
			stmt.setString(1, texto2);
			stmt.setString(1, texto3);
			
			stmt.executeUpdate();
			
			
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datosMonedas;
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
