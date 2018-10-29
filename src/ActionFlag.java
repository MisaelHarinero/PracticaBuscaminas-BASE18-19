import java.awt.event.MouseAdapter;

/**
 * @author misaelHarinero
 * Clase que nos permite poner una Bandera en las casillas
 *
 */
public class ActionFlag  extends MouseAdapter{
	private int i;
	private int j;
	private VentanaPrincipal ventana;
	
	
	public ActionFlag(int i, int j, VentanaPrincipal ventana) {
		this.i = i;
		this.j = j;
		this.ventana = ventana;
	}


	/**
	 * Metodo que al hacer click derecho nos llama al metodo de la interfaz generar Bandera
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 * @see VentanaPrincipal#generarBandera(int, int)
	 */
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			this.ventana.generarBandera(i, j);
			
		}
	}
	
}
