import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author misaelHarinero
 **
 */
public class ActionBoton implements ActionListener {
	private int i;
	private int j;
	private VentanaPrincipal ventana;

	public ActionBoton(int i, int j, VentanaPrincipal ventana) {
		this.i = i;
		this.j = j;
		this.ventana = ventana;
		// TODO
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.ventana.getJuego().abrirCasilla(i, j)) {
			abrirCasillasEnExplosion(i, j);
			this.ventana.actualizarPuntuacion();

			if (this.ventana.getJuego().esFinJuego()) {
				this.ventana.mostrarFinJuego(false);
			}

		} else {
			this.ventana.mostrarFinJuego(true);
		}
	}

	/**
	 * @param i
	 *            posicion x
	 * @param j
	 *            posicion y Dicho metodo se encarga de en caso de que sea la
	 *            casilla 0 generar una explosion que active las casillas de los
	 *            lados que no sea mina, en el caso de encontrar otra casilla con 0
	 *            volvera a explotar llamandose recursivamente, para que no se llame
	 *            otra vez al mismo he generado una variable estatica que sea
	 *            visitado, que en el caso de haber visitado la casilla le pone
	 *            dicho valor a la casilla
	 */
	public void abrirCasillasEnExplosion(int i, int j) {
		if (this.ventana.getJuego().getMinasAlrededor(i, j) == 0) {
			this.ventana.mostrarNumMinasAlrededor(i, j);
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					if ((((i + x) >= 0) && ((i + x) < this.ventana.getJuego().LADO_TABLERO)) && (((j + y) >= 0) && ((j + y) <this.ventana.getJuego().LADO_TABLERO))) {
						if (this.ventana.getJuego().abrirCasilla((i + x), (j + y))
								&& (!this.ventana.getJuego().isVisited(i + x, j + y))) {
							abrirCasillasEnExplosion((i + x), (j + y));
							this.ventana.refrescarPantalla();

						}

					}

				}
			}
		} else {
			this.ventana.mostrarNumMinasAlrededor(i, j);
			this.ventana.refrescarPantalla();

		}

	}

}
