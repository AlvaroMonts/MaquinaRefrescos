package accesoDatos;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import java.util.HashMap;
import java.util.Scanner;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class JSON implements Datos {

	ApiRequests encargadoPeticiones;

	Scanner teclado;

	public JSON() {
		teclado = new Scanner(System.in);
		encargadoPeticiones = new ApiRequests();
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositos = new HashMap<Integer, Deposito>();
		try {
			String url = "http://localhost/PHPEjercicios/MaquinaBackend/JsonJavascript/requestDep.php";
			String response = encargadoPeticiones.getRequest(url);
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(response);
			JSONObject obj;
			for (int i = 0; i < array.size(); i++) {
				obj = (JSONObject) array.get(i);
				Deposito dep = new Deposito();
				int cantidad = Integer.parseInt(obj.get("cantidad") + "");
				int valor = Integer.parseInt(obj.get("valor") + "");
				String nombre = obj.get("nombre") + "";

				dep.setCantidad(cantidad);
				dep.setNombreMoneda(nombre);
				dep.setValor(valor);
				dep.setId(valor);
				depositos.put(valor, dep);
				System.out.println("DEPOSITO " + (i+1) + ": Nombre: " + nombre + ", Valor: " + valor
						+ ", Cantidad: " + cantidad);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return depositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadores = new HashMap<String, Dispensador>();
		try {
			String url = "http://localhost/PHPEjercicios/MaquinaBackend/JsonJavascript/requestDisp.php";
			String response = encargadoPeticiones.getRequest(url);
			JSONParser parser = new JSONParser();
			JSONArray array;
			array = (JSONArray) parser.parse(response);
			JSONObject obj;
			// System.out.println(array);
			for (int i = 0; i < array.size(); i++) {
				obj = (JSONObject) array.get(i);
				Dispensador disp = new Dispensador();
				int cantidad = Integer.parseInt(obj.get("cantidad")+"");
				int precio = Integer.parseInt(obj.get("precio")+"");
				String clave = obj.get("clave") + "";
				String nombre = obj.get("nombre") + "";
				disp.setCantidad(cantidad);
				disp.setNombreProducto(nombre);
				disp.setClave(clave);
				disp.setPrecio(precio);
				dispensadores.put(clave, disp);
				System.out.println("DISPENSADOR " + (i+1) + ": Nombre: " + nombre + ", Clave: "
						+ clave + ", Cantidad: " +cantidad + ", Precio: " + precio);
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en el envio de datos");
			System.exit(-1);
		}
		return dispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		try {
			String url = "http://localhost/PHPEjercicios/MaquinaBackend/JsonJavascript/updateDepJSON.php";
			for (int i : depositos.keySet()) {
				JSONObject json = new JSONObject();
				json.put("valor", depositos.get(i).getValor());
				json.put("cantidad", depositos.get(i).getCantidad());
				String jsonstring = json.toJSONString();
				System.out.println(jsonstring);
				String response = encargadoPeticiones.postRequestWithParams(url, jsonstring);
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en el envio de datos");
			System.exit(-1);
			return false;
		}
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		try {
			String url = "http://localhost/PHPEjercicios/MaquinaBackend/JsonJavascript/updateDispJSON.php";
			
			for (String i : dispensadores.keySet()) {
				JSONObject json = new JSONObject();
				json.put("clave", dispensadores.get(i).getClave());
				json.put("cantidad", dispensadores.get(i).getCantidad());
				String jsonstring = json.toJSONString();
				System.out.println(jsonstring);
				String response = encargadoPeticiones.postRequestWithParams(url, jsonstring);
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en el envio de datos");
			System.exit(-1);
			return false;
		}
		
	}

}
