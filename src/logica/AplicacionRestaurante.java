package logica;

import interfaz.Interfaz;

/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */
public class AplicacionRestaurante {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

}
