import java.util.ArrayList;
import java.util.Random;

import javax.print.attribute.standard.RequestingUserName;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posici贸n guarda el numero {@value #MINA}
 * Si la casilla ya se ha abierto guarda el numero {@value #VISITADO}
 * Si no hay una mina, se guarda cuantas minas hay alrededor.
 * Almacena la puntuacion de la partida
 * @author misaelHarinero
 *
 */
public class ControlJuego {
	
	private final static int MINA = -1;
	private final static int VISITADO = -2;
	 static  int MINAS_INICIALES = 20;
	 static  int LADO_TABLERO = 10;

	private int [][] tablero;
	private int puntuacion;
	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//Inicializamos una nueva partida
		inicializarPartida();
		depurarTablero();
	}
	
	
	/**Metodo para generar un nuevo tablero de partida:
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habr谩 inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuantas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		//TODO: Repartir minas e inicializar puntaci髇. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
			generarMinas();
			this.puntuacion = 0;
		
		//Al final del m閠odo hay que guardar el n鷐ero de minas para las casillas que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
				}
			}
		}
	}
	
	/**Calculo de las minas adjuntas: 
	 * Para calcular el n煤mero de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdr谩n LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdr谩n 0.
	 * @param i: posici贸n vertical de la casilla a rellenar
	 * @param j: posici贸n horizontal de la casilla a rellenar
	 * @return : El n煤mero de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		int numMinas = 0 ;
		int posx;
		int  posy;
		for (int k = -1; k < 2; k++) {
			for (int x = -1; x < 2; x++) {
				posx = k;
				posy = x;
				if (((i+posx)>=0&&i+posx<(LADO_TABLERO))&&((j+posy>=0)&&(j+posy<LADO_TABLERO))) {
					if (this.tablero[i+posx][j+posy] == MINA) {
						numMinas++;
					}
				}
			}
		}
		
		return numMinas;
	}

	
	
	/**
	 * Metodo que nos permite 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posici贸n verticalmente de la casilla a abrir
	 * @param j: posici贸n horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		return (this.tablero[i][j]==MINA ? false : true);
	}
	
	
	
	/**
	 * Metodo que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		return(puntuacion == ((LADO_TABLERO*LADO_TABLERO)-MINAS_INICIALES) ? true : false);
	}
	
	
	/**
	 * Metodo que pinta por pantalla toda la informaci贸n del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuaci贸n: "+puntuacion);
	}

	/**
	 * Metodo que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, s铆mplemente consultarlo
	 * @param i : posici贸n vertical de la celda.
	 * @param j : posici贸n horizontal de la cela.
	 * @return Un entero que representa el n煤mero de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Metodo que devuelve la puntuaci贸n actual
	 * @return Un entero con la puntuaci贸n actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}
	/**
	 * Metodo en el que generamos el numero de  minas de manera Aleatoria respecto al valor que contenga MINAS_INICIALES
	 */
	public void generarMinas() {
		int contadorMinas = 0;
		int i,j;
		
		do {
			i = (int)Math.floor(Math.random()*LADO_TABLERO);
			j = (int)Math.floor(Math.random()*LADO_TABLERO);
			if (abrirCasilla(i,j)) {
				tablero[i][j] = MINA;
				contadorMinas++;
			}
			
		} while (contadorMinas<MINAS_INICIALES);
		
	}


	public int[][] getTablero() {
		return tablero;
	}


	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}
	/**
	 * @param i posicion x
	 * @param j posicion y 
	 * @return boolean true en el caso de que se haya visitado false en el caso de que no
	 */
	public boolean isVisited(int i,int j) {
		return (this.tablero [i][j] == VISITADO ? true: false);
	}
	/**
	 * @param i posicion x 
	 * @param j posicion y
	 * Nos permite indicar que dicha casilla ha sido visitada asignandole el valor
	 */
	public void visitarCasilla(int i,int j) {
		this.tablero[i][j] = VISITADO;
	}
	/**
	 * Metodo en el que suma un punto a la Puntuacion
	 */
	public void sumarPunto() {
		this.puntuacion++;
	}


	public static int getMINAS_INICIALES() {
		return MINAS_INICIALES;
	}


	public static void setMINAS_INICIALES(int mINAS_INICIALES) {
		MINAS_INICIALES = mINAS_INICIALES;
	}


	public static int getLADO_TABLERO() {
		return LADO_TABLERO;
	}


	public static void setLADO_TABLERO(int lADO_TABLERO) {
		LADO_TABLERO = lADO_TABLERO;
	}
	

	
	
}
