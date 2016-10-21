package logicaRefrescos;

/*
 * Clase Deposito
 * Existira un objeto de esta clase por cada tipo de moneda
 * (1, 2, 5, 10, 20 y 50 centimos y 1 y 2 euros)
 */
public class Deposito {

	/*
	 * Atributos de la clase double valor - Valor de la moneda double cantidad -
	 * numero de monedas de este tipo que hay en un momento dado
	 */
	private int id; // Para hibernate
	private String nombreMoneda;
	private int valor; // En centimos
	private int cantidad;

	/*
	 * Constructor vacio (para Hibernate)
	 */

	public Deposito() {
	}

	/*
	 * Constructor
	 * 
	 * @param v - valor de la moneda en cuestion
	 * 
	 * @param inicial - cantidad de monedas de este tipo con las que se inicia
	 * la ejecucion de la maquina de refrescos
	 */
	public Deposito(String n, int v, int inicial) {
		id = v;
		nombreMoneda = n;
		valor = v;
		cantidad = inicial;
	}

	/*
	 * Aumenta la cantidad de monedas Se llamara a este metodo cuando el usuario
	 * inserte una moneda
	 */
	public void anadir() {
		cantidad++;
	}

	/*
	 * Disminuye la cantidad de monedas Se llamara a este metodo cuando se
	 * retornen monedas al usuario (bien cuando pulse el botin de retorno, bien
	 * cuando haya que devolver cambio)
	 */
	public void restar() {
		cantidad--;
	}

	/*
	 * Sobrecargamos metodos anadir y restar pasando por parametro el numero que
	 * hay que sumar o restar
	 */

	public void anadir(int num) {
		cantidad += num;
	}

	public void restar(int num) {
		cantidad -= num;
	}

	/*
	 * Getters y setters (para hibernate)
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreMoneda() {
		return nombreMoneda;
	}

	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/*
	 * toString para comprobar el estado del deposito
	 * 
	 * @return retorna un string con los datos del deposito
	 */
	public String toString() {
		String s = "";
		s += "El deposito de la moneda " + nombreMoneda + " (" + valor + " centimos) contiene " + cantidad
				+ " monedas\n";
		return s;
	}
}