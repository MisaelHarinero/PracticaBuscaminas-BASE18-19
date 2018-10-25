
/**
 * @author misaelHarinero
 * Clase Enumerada en la que tenemos los diferentes modos de Juegos de nuestra App
 *
 */
enum TypesGame{
	LOW (10,20),
	MIDIUM(20,80),
	HARD(50,500)
	;
	private  int ladoTablero;
	private int numMinas;
	private TypesGame(int ladoTablero,int minas) {
		this.ladoTablero = ladoTablero;
		this.numMinas = minas;
	}
	public int getLadoTablero() {
		return ladoTablero;
	}
	public void setLadoTablero(int ladoTablero) {
		this.ladoTablero = ladoTablero;
	}
	public int getNumMinas() {
		return numMinas;
	}
	public void setNumMinas(int numMinas) {
		this.numMinas = numMinas;
	}
	
	
	
	
	
	
	
};
