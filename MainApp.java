package yestellefuncion;

import javax.swing.SwingUtilities;

/**
 * Clase principal para iniciar la aplicación
 * Autor: Manuel B
 */
public class MainApp {

    public static void main(String[] args) {
        // Ejecuta la interfaz gráfica de usuario en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Abre la ventana de Login al iniciar la aplicación
                new LoginFrame().setVisible(true);
            }
        });
    }
}