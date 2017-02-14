package accesoDatos;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoMongo implements Datos {

	MongoClient mongoClient;
	MongoCollection<Document> collection;
	MongoDatabase db;

	public AccesoMongo() {
		try {
			// PASO 1: Conexi�n al Server de MongoDB Pasandole el host y el
			// puerto
			mongoClient = new MongoClient("localhost", 27017);

			// PASO 2: Conexi�n a la base de datos
			db = mongoClient.getDatabase("maquina");
			System.out.println("Conectado a BD MONGO");

		} catch (Exception e) {
			System.out.println("Error leyendo la BD MONGO: " + e.getMessage());
			System.out.println("Fin de la ejecucion del programa");
			System.exit(1);
		}

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		Deposito nuevoDep;
		String nombreMoneda;
		int valor;
		int cantidad;

		try {

			// PASO 3: Obtenemos una coleccion para trabajar con ella
			collection = db.getCollection("depositos");

			// PASO 4.2.1: "READ" -> Leemos todos los documentos de la base de
			// datos
			int numDocumentos = (int) collection.count();
			System.out.println("N�mero de documentos (registros) en la colecci�n depositos: " + numDocumentos + "\n");

			// Recorro todos los documentos de la coleccion (tabla), creo el
			// objeto
			// deposito y lo almaceno en el hashmap
			MongoCursor<Document> cursor = collection.find().iterator();

			while (cursor.hasNext()) {
				Document rs = cursor.next();
				nombreMoneda = rs.getString("nombre");
				valor = rs.getInteger("valor");
				cantidad = rs.getInteger("cantidad");
				nuevoDep = new Deposito(nombreMoneda, valor, cantidad);
				// Una vez creado el deposito con valor de la moneda y cantidad
				// lo metemos en el hashmap
				depositosCreados.put(valor, nuevoDep);

				// System.out.println(cursor.next().toString());
			}
		} catch (Exception ex) {
			System.out.println("Error leyendo la coleccion: no se ha podido acceder a los datos");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.out.println("Fin de la ejecucion del programa");
			System.exit(1);
		}

		System.out.println("Leidos datos de la coleccion de Depositos");
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();

		Dispensador nuevoDis;
		String nombre;
		String clave;
		int precio;
		int cantidad;

		try {

			// PASO 3: Obtenemos una coleccion para trabajar con ella
			collection = db.getCollection("dispensadores");

			// PASO 4.2.1: "READ" -> Leemos todos los documentos de la base de
			// datos
			int numDocumentos = (int) collection.count();
			System.out
					.println("N�mero de documentos (registros) en la colecci�n dispensadores: " + numDocumentos + "\n");

			// Busco todos los documentos de la colecci�n, creo el objeto
			// deposito y lo almaceno en el hashmap
			MongoCursor<Document> cursor = collection.find().iterator();

			while (cursor.hasNext()) {
				Document rs = cursor.next();
				nombre = rs.getString("nombre");
				clave = rs.getString("clave");
				precio = rs.getInteger("precio");
				cantidad = rs.getInteger("cantidad");
				nuevoDis = new Dispensador(clave, nombre, precio, cantidad);
				// Una vez creado el dispensador lo metemos en el hashmap
				dispensadoresCreados.put(clave, nuevoDis);

				// System.out.println(cursor.next().toString());
			}
		} catch (Exception ex) {
			System.out.println("Error leyendo la coleccion: no se ha podido acceder a los datos");
			System.out.println(ex.getMessage());
			System.out.println("Fin de la ejecucion del programa");
			System.exit(1);
		}

		System.out.println("Leidos datos de la coleccion de Dispensadores");
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;

		todoOK = this.guardarDepv1(depositos);

		return todoOK;

	}

	// Actualizamos borrando la colleccion y volviendo a escribir
	private boolean guardarDepv1(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;

		try {
			Deposito auxDep;
			this.collection = db.getCollection("depositos");
			for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
				auxDep = (Deposito) entry.getValue();
				collection.updateOne(new Document("valor", auxDep.getValor()), new Document("$set", depToDocument(auxDep)));
			}
		} catch (Exception e) {
			todoOK = false;
			System.out.println("Opcion guardar datos de Depositos no disponible por el momento");
			e.printStackTrace();
		}

		return todoOK;

	}

	private Document depToDocument(Deposito auxDep) {
		// Creamos una instancia Documento
		Document dbObjectDeposito = new Document();

		dbObjectDeposito.append("id", auxDep.getId());
		dbObjectDeposito.append("nombre",auxDep.getNombreMoneda());
		dbObjectDeposito.append("valor", auxDep.getValor());
		dbObjectDeposito.append("cantidad", auxDep.getCantidad());
		return dbObjectDeposito;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = false;

		todoOK = this.guardarDisv1(dispensadores);

		return todoOK;
	}

	// Actualizamos borrando la colleccion y volviendo a escribir
	private boolean guardarDisv1(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;

		try {
			Dispensador auxDis;
			this.collection = db.getCollection("dispensadores");
			for (HashMap.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
				auxDis = (Dispensador) entry.getValue();

				collection.updateOne(new Document("clave", auxDis.getClave()), new Document("$set", disToDocument(auxDis)));
			}
		} catch (Exception e) {
			todoOK = false;
			System.out.println("Opcion guardar datos de Depositos no disponible por el momento");
			e.printStackTrace();
		}

		return todoOK;

	}

	private Document disToDocument(Dispensador auxDis) {
		// Creamos una instancia Documento
		Document dbObjectDispensador = new Document();

		dbObjectDispensador.append("id", auxDis.getId());
		dbObjectDispensador.append("clave", auxDis.getClave());
		dbObjectDispensador.append("nombre", auxDis.getNombreProducto());
		dbObjectDispensador.append("precio", auxDis.getPrecio());
		dbObjectDispensador.append("cantidad", auxDis.getCantidad());

		return dbObjectDispensador;
	}

}
