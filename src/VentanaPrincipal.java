import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;
	// MENU DE DIFICULTAD
	JPanel panelDificultad;
	JRadioButton radios[];
	JButton buttonDificultad;
	JButton buttonAceptar;
	ButtonGroup grupo;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 1000, 700);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelImagen.setLayout(new GridLayout());
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(ControlJuego.LADO_TABLERO, ControlJuego.LADO_TABLERO));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[ControlJuego.LADO_TABLERO][ControlJuego.LADO_TABLERO];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[ControlJuego.LADO_TABLERO][ControlJuego.LADO_TABLERO];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);
		// Boton Dificultad
		generarPanelDeDificultad();

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		inicializarListenerBotones();
		listenerDificultad();
		this.botonEmpezar.addActionListener((e) -> {
			reiniciarPartida();
		});
		this.buttonDificultad.addActionListener((e) -> {
			JOptionPane.showMessageDialog(null, this.panelDificultad);
		});

	}

	/**
	 * Inicializa solo los listener de los botones para aprovechar dicho metodo al
	 * reiniciar partida
	 */
	public void inicializarListenerBotones() {
		for (int i = 0; i < this.botonesJuego.length; i++) {
			for (int j = 0; j < this.botonesJuego[i].length; j++) {
				this.botonesJuego[i][j].addActionListener(new ActionBoton(i, j, this));
			}
		}
		listenerBanderas();
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca
	 * el botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto
	 * según la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó
	 * más : rojo
	 * 
	 * @param i:
	 *            posición vertical de la celda.
	 * @param j:
	 *            posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		int cantidad = this.juego.getTablero()[i][j];
		if (this.panelesJuego[i][j].getComponent(0).getClass().equals(JButton.class)
				&& !((JButton) this.panelesJuego[i][j].getComponent(0)).getText().equals("Flag")) {
			this.panelesJuego[i][j].remove(this.botonesJuego[i][j]);
			this.juego.visitarCasilla(i, j);
			JTextField text = new JTextField(Integer.toString(cantidad));
			text.setEditable(false);
			text.setForeground(correspondenciaColores[cantidad]);
			text.setHorizontalAlignment(JTextField.CENTER);
			this.panelesJuego[i][j].add(text);
			this.juego.sumarPunto();
		}
	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion
	 *            : Un booleano que indica si es final del juego porque ha explotado
	 *            una mina (true) o bien porque hemos desactivado todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		if (porExplosion) {
			bloquearBotones();
			refrescarPantalla();
			JOptionPane.showMessageDialog(null, "Te exploto una bomba en la cara", "Losser", 0);

		} else {
			bloquearBotones();
			refrescarPantalla();
			JOptionPane.showMessageDialog(null, "Has Ganado la Partida", "Winner", 1);

		}
		// TODO
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		this.pantallaPuntuacion.setText(Integer.toString(this.juego.getPuntuacion()));
		// TODO
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

	/**
	 * Metodo que se encarga de bloquear los botones en caso de que explote una
	 * bomba
	 */
	public void bloquearBotones() {
		for (int i = 0; i < this.botonesJuego.length; i++) {
			for (int j = 0; j < this.botonesJuego[i].length; j++) {
				this.botonesJuego[i][j].setEnabled(false);
			}
		}
		mostrarBombas();
	}

	/**
	 * Metodo que se encarga de reiniciar Partida
	 */

	public void reiniciarPartida() {
		panelJuego.setLayout(new GridLayout(ControlJuego.LADO_TABLERO, ControlJuego.LADO_TABLERO));
		juego.inicializarPartida();
		panelJuego.removeAll();
		panelJuego.setLayout(new GridLayout(ControlJuego.LADO_TABLERO, ControlJuego.LADO_TABLERO));
		// Paneles
		panelesJuego = new JPanel[ControlJuego.LADO_TABLERO][ControlJuego.LADO_TABLERO];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[ControlJuego.LADO_TABLERO][ControlJuego.LADO_TABLERO];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}
		this.pantallaPuntuacion.setText(Integer.toString(this.juego.getPuntuacion()));
		inicializarListenerBotones();
		refrescarPantalla();

	}

	/**
	 * Metodo en el que generamos un panel de Dificultad
	 */
	public void generarPanelDeDificultad() {
		String difText[] = { "LOW", "MEDIUM", "HARD" };
		this.buttonDificultad = new JButton("Dificultad");
		this.panelImagen.add(buttonDificultad);
		this.panelDificultad = new JPanel();
		this.panelDificultad.setLayout(new GridLayout(5, 1));
		JTextField texto = new JTextField("Seleccione la dificultad");
		this.panelDificultad.add(texto);
		this.buttonAceptar = new JButton("Aceptar");
		texto.setEditable(false);
		this.grupo = new ButtonGroup();
		this.radios = new JRadioButton[3];
		for (int i = 0; i < 3; i++) {
			this.radios[i] = new JRadioButton(difText[i]);
			this.grupo.add(this.radios[i]);
			this.panelDificultad.add(this.radios[i]);

		}
		this.panelDificultad.add(this.buttonAceptar);

	}

	/**
	 * A�adimos un listener al boton de Dentro del panel de Dificultad en el que
	 * vemos que radioButton a seleccionado y generamos el tablero con esa
	 * dificultad
	 */
	public void listenerDificultad() {
		this.buttonAceptar.addActionListener((e) -> {
			TypesGame tipos[] = { TypesGame.LOW, TypesGame.MIDIUM, TypesGame.HARD };
			for (int i = 0; i < radios.length; i++) {
				if (radios[i].isSelected()) {
					ControlJuego.setLADO_TABLERO(tipos[i].getLadoTablero());
					ControlJuego.setMINAS_INICIALES(tipos[i].getNumMinas());
					reiniciarPartida();
				}
			}

		});

	}

	/**
	 * Metodo que al explotar una bomba nos ense�a las bombas que habia en el
	 * tablero
	 */
	public void mostrarBombas() {
		for (int i = 0; i < this.botonesJuego.length; i++) {
			for (int j = 0; j < this.botonesJuego[i].length; j++) {
				if (!this.juego.abrirCasilla(i, j)) {
					this.panelesJuego[i][j].removeAll();
					JTextField textAux = new JTextField("X");
					textAux.setEditable(false);
					textAux.setBackground(Color.getHSBColor(50, 360, 100));
					textAux.setHorizontalAlignment(JTextField.CENTER);
					this.panelesJuego[i][j].add(textAux);
				}
			}
		}
		refrescarPantalla();

	}

	/**
	 * Le ponemos un listener a los paneles que si se clica con el click derecho del
	 * raton nos ponga una bandera
	 */
	public void listenerBanderas() {
		for (int i = 0; i < this.botonesJuego.length; i++) {
			for (int j = 0; j < this.botonesJuego[i].length; j++) {
				this.botonesJuego[i][j].addMouseListener(new ActionFlag(i, j, this));
			}
		}

	}

	/**
	 * @param i
	 *            :int posicion x
	 * 
	 * @param j
	 *            :int posicion y No pone una bandera en la casilla o nos la quita y
	 *            nos vuelve a poner el boton que estaba anteriormente
	 */
	public void generarBandera(int i, int j) {
		if (!((JButton) this.panelesJuego[i][j].getComponent(0)).getText().equals("Flag")) {
			JButton botonBandera = new JButton("Flag");
			botonBandera.addMouseListener(new ActionFlag(i, j, this));
			this.panelesJuego[i][j].removeAll();
			this.panelesJuego[i][j].add(botonBandera);
		} else {
			this.panelesJuego[i][j].removeAll();
			this.panelesJuego[i][j].add(this.botonesJuego[i][j]);
		}
		refrescarPantalla();
	}

}
