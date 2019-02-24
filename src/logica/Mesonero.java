package logica;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authores Anthony Chacin José Cisneros
 */
//Mesonero que se encargará de armar una orden para que luego sea enviada a una mesa
public class Mesonero extends Thread {

    private Parametros param;

    //Todos los mesones
    private Meson mesonE, mesonPF, mesonP;

    //Semáforos para sincronizar el paso de acuerdo a la capacidad del mesón 
    private Semaphore sCapacidadMesonE, sCapacidadMesonPF, sCapacidadMesonP;

    //Semáforos para sincronizar el paso de acuerdo a: si hay algún plato producido o no 
    private Semaphore sHayPlatosE, sHayPlatosPF, sHayPlatosP;

    //Semáforos para garantizar la exclusividad
    //Sólo un cocinero o mesonero a la vez va a poder acceder a la sección crítica
    /*  En este caso, en la sección crítica se encuentra el vector "meson" en el cual se guardarán o se tomarán 
        los platos producidos.
     */
    private Semaphore sMutexE, sMutexPF, sMutexP;

    //Semaforo para controlar el acceso al contador de unidades finales.
    //Esto es para evitar que el mesonero y el gerente intenten modificar el número de ordenes armadas al mismo tiempo
    private Semaphore sUF; // UF: Unidades Finales

    private int proximoConsumirE, proximoConsumirPF, proximoConsumirP;

    private boolean contratado = true;

    public Mesonero(
            Parametros param,
            Meson mesonE, Meson mesonPF, Meson mesonP,
            Semaphore sCapacidadMesonE, Semaphore sCapacidadMesonPF, Semaphore sCapacidadMesonP,
            Semaphore sHayPlatosE, Semaphore sHayPlatosPF, Semaphore sHayPlatosP,
            Semaphore sMutexE, Semaphore sMutexPF, Semaphore sMutexP,
            Semaphore sUF,
            int proximoConsumirE, int proximoConsumirPF, int proximoConsumirP
    ) {
        this.param = param;
        this.mesonE = mesonE;
        this.mesonPF = mesonPF;
        this.mesonP = mesonP;
        this.sCapacidadMesonE = sCapacidadMesonE;
        this.sCapacidadMesonPF = sCapacidadMesonPF;
        this.sCapacidadMesonP = sCapacidadMesonP;
        this.sHayPlatosE = sHayPlatosE;
        this.sHayPlatosPF = sHayPlatosPF;
        this.sHayPlatosP = sHayPlatosP;
        this.sMutexE = sMutexE;
        this.sMutexPF = sMutexPF;
        this.sMutexP = sMutexP;
        this.sUF = sUF;
        this.proximoConsumirE = proximoConsumirE;
        this.proximoConsumirPF = proximoConsumirPF;
        this.proximoConsumirP = proximoConsumirP;
    }

    @Override
    public void run() {
        while (this.contratado) {
            try {
                this.sHayPlatosE.acquire(3);
                this.sHayPlatosPF.acquire(2);
                this.sHayPlatosP.acquire();

                this.sMutexE.acquire();
                this.armarOrden(this.mesonE, 1);
                this.sMutexE.release();

                this.sMutexPF.acquire();
                this.armarOrden(this.mesonPF, 2);
                this.sMutexPF.release();

                this.sMutexP.acquire();
                this.armarOrden(this.mesonP, 3);
                this.sMutexP.release();

                this.sCapacidadMesonP.release();
                this.sCapacidadMesonPF.release(2);
                this.sCapacidadMesonE.release(3);

                Thread.sleep((this.param.getUnaHoraEnSegs() * 150));

                sUF.acquire();
                AplicacionRestaurante.setOrdenesArmadas(AplicacionRestaurante.getOrdenesArmadas() + 1);
                sUF.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mesonero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void armarOrden(Meson meson, int tipo) {

        switch (tipo) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    meson.setVectorMeson(this.proximoConsumirE, 0);
                    this.proximoConsumirE = (this.proximoConsumirE + 1) % meson.getCapacidadMeson();
                    meson.cantUnidades--;
                }
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    meson.setVectorMeson(this.proximoConsumirPF, 0);
                    this.proximoConsumirPF = (this.proximoConsumirPF + 1) % meson.getCapacidadMeson();
                    meson.cantUnidades--;
                }
                break;
            case 3:
                meson.setVectorMeson(this.proximoConsumirP, 0);
                this.proximoConsumirP = (this.proximoConsumirP + 1) % meson.getCapacidadMeson();
                meson.cantUnidades--;
                break;
            default:
                break;
        }

    }

    public void setContratado(boolean contratado) {
        this.contratado = contratado;
    }
}
