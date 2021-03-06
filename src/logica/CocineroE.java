package logica;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authores Anthony Chacin José Cisneros
 */
//Cocinero de entradas
public class CocineroE extends Cocinero {

    public CocineroE(Parametros param, Meson meson, Semaphore sCapacidadMeson, Semaphore sHayPlatos, Semaphore sMutex, int proximoProducir, int valor) {
        super(param, meson, sCapacidadMeson, sHayPlatos, sMutex, proximoProducir, valor);
    }

    @Override
    public void run() {
        while (this.contratado) {
            try {
                this.sCapacidadMeson.acquire();

                Thread.sleep((this.param.getUnaHoraEnSegs() * 250));

                this.sMutex.acquire();
                this.producirPlato();
                this.sMutex.release();

                this.sHayPlatos.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(CocineroE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
