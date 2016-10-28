package accesoDatos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
			String sql = "SELECT * from bbddmaquinarefrescos.depositos;";
			Statement stmt = conection.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			rset.last();
			int b = rset.getRow();
			rset.beforeFirst();
			for (int i = 0; i <= b; i++) {
				if (rset.next()) {
					Deposito dep = new Deposito();
					dep.setValor(rset.getInt((1)));
					dep.setCantidad(rset.getInt((2)));
					dep.setNombreMoneda(rset.getString((3)));
					datosMonedas.put(i, dep);
				}
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datosMonedas;
	}

	public HashMap<String, Dispensador> obtenerDispensadores() {
		try {
			String sql = "SELECT * from bbddmaquinarefrescos.dispensadores;";
			Statement stmt = conection.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			rset.last();
			int b = rset.getRow();
			rset.beforeFirst();
			for (int i = 0; i <= b; i++) {
				if (rset.next()) {
					Dispensador disp = new Dispensador();
					disp.setClave(rset.getString((2)));
					disp.setNombreProducto(rset.getString((3)));
					disp.setCantidad(rset.getInt((4)));
					disp.setPrecio(rset.getInt((5)));
					datosProductos.put(rset.getString((1)), disp);
				}
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datosProductos;
	}

	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		try {
			ArrayList<Integer> cantidad = new ArrayList<Integer>();
			int valor[] = new int[6];
			valor[0] = 200;
			valor[1] = 100;
			valor[2] = 50;
			valor[3] = 20;
			valor[4] = 10;
			valor[5] = 5;

			for (int i : depositos.keySet()) {
				Deposito dep = new Deposito();
				dep = depositos.get(i);
				cantidad.add(dep.getCantidad());
			}
			for (int i = 0; i < valor.length; i++) {
				String sql = "UPDATE `bbddmaquinarefrescos`.`depositos` SET cantidad = " + cantidad.get(i)
						+ " WHERE valor = " + valor[i] + ";";
System.out.println(valor[i]);
				PreparedStatement stmt = conection.prepareStatement(sql);
				stmt.executeUpdate();
				stmt.close();
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		try {
			ArrayList<Integer> cantidad = new ArrayList<Integer>();
			String clave[] = new String[dispensadores.size()];
			int j=0;
			for (String i : dispensadores.keySet()) {
				Dispensador disp = new Dispensador();
				disp = dispensadores.get(i);
				clave[j] = disp.getClave();
				cantidad.add(disp.getCantidad());
				j++;
			}

			for (int i = 0; i < clave.length; i++) {
				String sql = "UPDATE `bbddmaquinarefrescos`.`dispensadores` SET cantidad = " + cantidad.get(i)
						+ " WHERE clave = '" + clave[i] + "';";
				System.out.println(clave[i]);
				PreparedStatement stmt = conection.prepareStatement(sql);
				stmt.executeUpdate();
				stmt.close();
			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
