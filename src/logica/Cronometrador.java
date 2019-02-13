
package logica;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */

//Registra el paso de las horas 
public class Cronometrador extends Thread {
    
    private int contador;
    private double horasEscribiendo;
    private double horasDescansando;
    private Semaphore sContador;
    private String status;
    private Parametros param;
    
    public Cronometrador(Semaphore sContador, Parametros param){
        this.sContador = sContador;
        this.contador = param.getHorasParaElCierre();
        this.horasEscribiendo = (param.getUnaHoraEnSegs()*50); // 1000*0.05 = 50
        this.horasDescansando = (param.getUnaHoraEnSegs()*1000) - this.horasEscribiendo;
        this.status = "Descansando";
        this.param = param;
    }
    
    @Override
    public void run(){
        
        int horasE = (int)this.horasEscribiendo;
        int horasD = (int)this.horasDescansando;
        String status1 = "Modificando contador";
        String status2 = "Descansando";
        
        while(true){
            try{
                Thread.sleep(horasD);
                this.sContador.acquire();
                this.status = status1;
                Thread.sleep(horasE);
                this.contador--;
                
                if(this.contador < 0){
                    this.contador = this.param.getHorasParaElCierre();
                }
               
                this.sContador.release();
                this.status = status2;
                
            }catch(InterruptedException ex){
                Logger.getLogger(Cronometrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getContador() {
        return contador;
    }

    public String getStatus() {
        return status;
    }
}
