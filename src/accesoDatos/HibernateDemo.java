package accesoDatos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

import java.util.List;

public class HibernateDemo implements Datos {
	HashMap<Integer,Deposito> depositos;
	HashMap<String,Dispensador> dispensadores;
	
	public HibernateDemo() {
		depositos = new HashMap<Integer,Deposito>();
		dispensadores = new HashMap<String,Dispensador>();
	}
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		System.out.println("LISTA DEPOSITOS");
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Query q= session.createQuery("from Deposito");
        List results = q.list();
        Iterator deps = results.iterator();
        while (deps.hasNext()){
            Deposito dep = (Deposito) deps.next();
            depositos.put(dep.getValor(), dep);
            System.out.println (dep.toString());
        }
        session.close();
		return depositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		System.out.println("LISTA DISPENSADORES");
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Query q= session.createQuery("from Dispensador");
        List results = q.list();
        Iterator disps = results.iterator();
        while (disps.hasNext()){
            Dispensador disp = (Dispensador) disps.next();
            dispensadores.put(disp.getClave(), disp);
            System.out.println (disp.toString());
        }
        session.close();
		return dispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		try {
			for (int valores : depositos.keySet()) {
				Deposito dep = depositos.get(valores);
				SessionFactory sf3 = new Configuration().configure().buildSessionFactory();
				Session session2 = sf3.openSession();
				session2.getTransaction().begin();
				Query query2 = session2.createQuery("update Deposito set nombre = :name, cantidad = :quantity where valor = :value");
				query2.setParameter("name", dep.getNombreMoneda());
				query2.setParameter("value", dep.getValor());
				query2.setParameter("quantity", dep.getCantidad());
				int result = query2.executeUpdate();
				session2.getTransaction().commit();
			}
			return true;
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en el envio de datos de depositos");
			System.exit(-1);
			return false;
		}
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		try {
			for (String claves : dispensadores.keySet()) {
				Dispensador disp = dispensadores.get(claves);
				SessionFactory sf = new Configuration().configure().buildSessionFactory();
				Session session = sf.openSession();
				session.getTransaction().begin();
				Query query = session.createQuery("update Dispensador set nombre = :name, cantidad = :quantity, precio = :prize where clave = :key");
				query.setParameter("key", disp.getClave());
				query.setParameter("name", disp.getNombreProducto());
				query.setParameter("quantity", disp.getCantidad());
				query.setParameter("prize", disp.getPrecio());
				int result = query.executeUpdate();
				session.getTransaction().commit();
			}
			return true;
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en el envio de datos de dispensadores");
			System.exit(-1);
			return false;
		}
	}
}
