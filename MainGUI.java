/**
 * Clase principal que inicia la interfaz gráfica (GUI) de la Biblioteca.
 */
public class MainGUI {

    public static void main(String[] args) {
        // 1. Creamos la ÚNICA ventana
        VentanaPrincipal ventana = new VentanaPrincipal();
        
        // 2. Hacemos visible la ventana
        ventana.setVisible(true); 
    }
}