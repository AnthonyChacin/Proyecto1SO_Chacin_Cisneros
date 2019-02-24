
package logica;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */

//Se encarga de verificar las horas que faltan para cerrar el restaurante
public class Gerente extends Thread {
    
    Random r = new Random();
    private Semaphore sContador, sUF;
    private Cronometrador cronometrador;
    private double horaMenor, horaMayor;
    private int horasDescansando;
    private String status;
    private Parametros param;
    
    public Gerente(Semaphore sContador, Parametros param, Cronometrador cronometrador, Semaphore sUF){
        this.sContador = sContador;
        this.sUF = sUF;
        this.param = param;
        this.horaMayor = (this.param.getUnaHoraEnSegs()*450);
        this.horaMenor = (this.param.getUnaHoraEnSegs()*250);
        this.cronometrador = cronometrador;
    }
    
    @Override
    public void run(){
        int menor = (int)this.horaMenor;
        int mayor = (int)this.horaMayor;
        
        String status1 = "Descansando";
        String status2 = "Pagando Ordenes";
        
        while(true){
            this.horasDescansando = r.nextInt((mayor - menor)) + menor;
            
            try{
                this.status = status1;
                Thread.sleep(this.horasDescansando);
                
                this.sContador.acquire();
                
                this.status = "Leyendo";
                
                //Esta hora no está definida en las indicaciones del proyecto. Sin embargo, pensamos en que
                //el gerente debería tomarse un timepo para leer dicho contador tomando en cuenta que es una
                //simulación de un caso real...
                Thread.sleep(this.param.getUnaHoraEnSegs()*100); 
                
                if(this.cronometrador.getContador() == 0){
                    this.status = status2;
                    
                    this.sUF.acquire();
                    Thread.sleep(this.param.getUnaHoraEnSegs()*100); // 0.1 horas = param.getNumHorasSegs * 1000 * 0.1

                    AplicacionRestaurante.setOrdenesArmadas(0);
                    
                    this.sUF.release();
                    
                }
                this.sContador.release();

                
            }catch(InterruptedException ex){
                Logger.getLogger(Gerente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getStatus() {
        return status;
    }
}
