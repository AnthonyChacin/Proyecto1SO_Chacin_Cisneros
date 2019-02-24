
package logica;

import java.util.concurrent.Semaphore;

/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */
public abstract class Cocinero extends Thread {
    
    protected Parametros param;
    protected Meson meson;
    protected Semaphore sCapacidadMeson;
    protected Semaphore sHayPlatos;
    protected Semaphore sMutex;
    protected int proximoProducir;
    protected int valor;
    protected boolean contratado = true;
    
    public Cocinero(Parametros param, Meson meson, Semaphore sCapacidadMeson, Semaphore sHayPlatos, Semaphore sMutex, int proximoProducir, int valor){
        this.param = param;
        this.meson = meson;
        this.sCapacidadMeson = sCapacidadMeson;
        this.sHayPlatos = sHayPlatos;
        this.sMutex = sMutex;
        this.proximoProducir = proximoProducir;
        this.valor = valor;
    }
    
    @Override
    public abstract void run();
    
    public void producirPlato(){
        this.meson.setVectorMeson(this.proximoProducir, this.valor);
        this.meson.cantUnidades++;
        this.proximoProducir = (this.proximoProducir + 1) % this.meson.getCapacidadMeson();
    }

    public void setContratado(boolean contratado) {
        this.contratado = contratado;
    } 
}
