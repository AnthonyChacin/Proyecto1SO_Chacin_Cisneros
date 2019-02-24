package logica;

import interfaz.Interfaz;
import java.util.concurrent.Semaphore;
/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */
public class AplicacionRestaurante {
    
    private Parametros param;
    private Meson mesonE, mesonPF, mesonP;
    private CocineroE[] cocinerosE;
    private CocineroPF[] cocinerosPF;
    private CocineroP[] cocinerosP;
    private Mesonero[] mesoneros;
    private Semaphore sCapacidadMesonE, sCapacidadMesonPF, sCapacidadMesonP;
    private Semaphore sHayPlatosE, sHayPlatosPF, sHayPlatosP;
    private Semaphore sMutexE, sMutexPF, sMutexP;
    private Semaphore sContador, sUnidadesFinales;
    private Cronometrador cronometrador;
    private Gerente gerente;
    private int proximoProducirE, proximoProducirPF, proximoProducirP;
    private int proximoConsumirE, proximoConsumirPF, proximoConsumirP;
    private static int ordenesArmadas;
    private int cantCocinerosE, cantCocinerosPF, cantCocinerosP, cantMesoneros;
    
    
    public AplicacionRestaurante(){
        this.inicializacion();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }
    
    public void inicializacion(){
        
        this.param = new Parametros("parametros_restaurante.txt");
        
        this.mesonE = new Meson(this.param.getMaxMesonEntradas());
        this.mesonPF = new Meson(this.param.getMaxMesonPlatosFuertes());
        this.mesonP = new Meson(this.param.getMaxMesonPostres());
        
        this.cocinerosE = new CocineroE[this.param.getNumMaxCocE()];
        this.cocinerosPF = new CocineroPF[this.param.getNumMaxCocPF()];
        this.cocinerosP = new CocineroP[this.param.getNumMaxCocP()];
        
        this.mesoneros = new Mesonero[this.param.getNumMaxMesone()];
        
        this.sCapacidadMesonE = new Semaphore(this.param.getMaxMesonEntradas());
        this.sHayPlatosE = new Semaphore(0);
        this.sMutexE = new Semaphore(1);
        
        this.sCapacidadMesonPF = new Semaphore(this.param.getMaxMesonPlatosFuertes());
        this.sHayPlatosPF = new Semaphore(0);
        this.sMutexPF = new Semaphore(1);
        
        this.sCapacidadMesonP = new Semaphore(this.param.getMaxMesonPostres());
        this.sHayPlatosP = new Semaphore(0);
        this.sMutexP = new Semaphore(1);
        
        this.sContador = new Semaphore(1);
        this.sUnidadesFinales = new Semaphore(1);
        
        this.proximoProducirE = 0;
        this.proximoProducirPF = 0;
        this.proximoProducirP = 0;
        this.proximoConsumirE = 0;
        this.proximoConsumirPF = 0;
        this.proximoConsumirP = 0;
        this.cantCocinerosE = 0;
        this.cantCocinerosPF = 0;
        this.cantCocinerosP = 0;
        this.cantMesoneros = 0;
        
        ordenesArmadas = 0;
        
        this.cronometrador = new Cronometrador(sContador, this.param);
        this.cronometrador.start();
        
        this.gerente = new Gerente(sContador, this.param, this.cronometrador, this.sUnidadesFinales);
        this.gerente.start();
        
        this.inicializarMesoneros();
        this.inicializarCocineros();
    }
    
    //Se crean los cocineros de cada tipo de acuerdo a la cantidad inicial establecida en los parámetros
    public void inicializarCocineros(){
        
        //Cocineros de Entradas
        for (int i = 0; i < this.param.getNumIniCocE(); i++) {
            this.contratarCocineroE();
        }
        
        //Cocineros de Platos Fuertes
        for (int i = 0; i < this.param.getNumIniCocPF(); i++) {
            this.contratarCocineroPF();
        }
        
        //Cocineros de Postres
        for (int i = 0; i < this.param.getNumIniCocP(); i++) {
            this.contratarCocineroP();
        }
    }
    
    //Se crean los mesoneros de acuerdo a la cantidad inicial establecida en los parámetros
    public void inicializarMesoneros(){
        
        //Se contrata a un Mesonero
        for (int i = 0; i < this.param.getNumIniMesone(); i++) {
            this.contratarMesonero();
        }
    }
    
    
    
//Sección de contratación de cocineros y mesoneros     
    
    //Se contrata a un cocinero que produce platos de entrada
    public void contratarCocineroE(){
        int cont = 0;
        for (int i = 0; i < this.cocinerosE.length; i++) {
            if (this.cocinerosE[i] == null) {
                this.cocinerosE[i] = new CocineroE(this.param, this.mesonE, this.sCapacidadMesonE, this.sHayPlatosE, this.sMutexE, this.proximoProducirE, 1);
                this.cocinerosE[i].start();
                this.cantCocinerosE++;
                break;
            }
            cont++;
        }
        if(cont == this.cocinerosE.length){
            System.out.println("Ha alcanzado el límite de cocineors de entradas (" + this.param.getNumMaxCocE() + ")");
        }else{
            System.out.println("Cocinero de entradas contratado con éxito");
        }
    }
    
    //Se contrata a un cocinero que produce platos fuertes
    public void contratarCocineroPF(){
        int cont = 0;
        for (int i = 0; i < this.cocinerosPF.length; i++) {
            if (this.cocinerosPF[i] == null) {
                this.cocinerosPF[i] = new CocineroPF(this.param, this.mesonPF, this.sCapacidadMesonPF, this.sHayPlatosPF, this.sMutexPF, this.proximoProducirPF, 1);
                this.cocinerosPF[i].start();
                this.cantCocinerosPF++;
                break;
            }
            cont++;
        }
        if(cont == this.cocinerosPF.length){
            System.out.println("Ha alcanzado el límite de cocineors de platos fuertes (" + this.param.getNumMaxCocPF() + ")");
        }else{
            System.out.println("Cocinero de platos fuertes contratado con éxito");
        }
    }
    
    //Se contrata a un cocinero que produce platos de postres
    public void contratarCocineroP(){
        int cont = 0;
        for (int i = 0; i < this.cocinerosP.length; i++) {
            if (this.cocinerosP[i] == null) {
                this.cocinerosP[i] = new CocineroP(this.param, this.mesonP, this.sCapacidadMesonP, this.sHayPlatosP, this.sMutexP, this.proximoProducirP, 1);
                this.cocinerosP[i].start();
                this.cantCocinerosP++;
                break;
            }
            cont++;
        }
        if(cont == this.cocinerosP.length){
            System.out.println("Ha alcanzado el límite de cocineors de postres (" + this.param.getNumMaxCocP() + ")");
        }else{
            System.out.println("Cocinero de postres contratado con éxito");
        }
    }
    
    //Se contrata a un mesonero que armará las ordenes de acuerdo a los platos que se hayan producido en cada mesón
    public void contratarMesonero(){
        int cont = 0;
        for (int i = 0; i < this.mesoneros.length; i++) {
            if(this.mesoneros[i] == null){
                this.mesoneros[i] = new Mesonero(
                    this.param,
                    this.mesonE, this.mesonPF, this.mesonP,
                    this.sCapacidadMesonE, this.sCapacidadMesonPF, this.sCapacidadMesonP,
                    this.sHayPlatosE, this.sHayPlatosPF, this.sHayPlatosP,
                    this.sMutexE, this.sMutexPF, this.sMutexP, this.sUnidadesFinales,
                    this.proximoConsumirE, this.proximoConsumirPF, this.proximoConsumirP
                );
                this.mesoneros[i].start();
                this.cantMesoneros++;
                break;
            }
            cont++;
        }
        if(cont == this.mesoneros.length){
            System.out.println("Ha alcanzado el límite de mesoneros ("+ this.param.getNumMaxMesone() +")");
        }else{
            System.out.println("Mesonero contratado con éxito");
        }
    }
    
//Sección de despido de cocineros y mesoneros
    
    //Se despide a un cocinero de entradas
    public void despedirCocineroE(){
        int i = this.cantCocinerosE;
        if(i > 0){
            this.cocinerosE[i-1].setContratado(false);
            this.cocinerosE[i-1] = null;
            this.cantCocinerosE--;
            System.out.println("Cocinero de Entradas despedido con éxito");
        }else{
            System.out.println("Ya ha despedido a todos los cocineros de entradas");
        }
    }
    
    //Se despide a un cocinero de platos fuertes
    public void despedirCocineroPF(){
        int i = this.cantCocinerosPF;
        if(i > 0){
            this.cocinerosPF[i-1].setContratado(false);
            this.cocinerosPF[i-1] = null;
            this.cantCocinerosPF--;
            System.out.println("Cocinero de Platos Fuertes despedido con éxito");
        }else{
            System.out.println("Ya ha despedido a todos los cocineros de Platos Fuertes");
        }
    }
    
    //Se despide a un cocinero de postres
    public void despedirCocineroP(){
        int i = this.cantCocinerosP;
        if(i > 0){
            this.cocinerosP[i-1].setContratado(false);
            this.cocinerosP[i-1] = null;
            this.cantCocinerosP--;
            System.out.println("Cocinero de Postres despedido con éxito");
        }else{
            System.out.println("Ya ha despedido a todos los cocineros de postres");
        }
    }
    
    //Se despide a un mesonero
    public void despedirMesonero(){
        int i = this.cantMesoneros;
        if(i > 0){
            this.mesoneros[i-1].setContratado(false);
            this.mesoneros[i-1] = null;
            this.cantMesoneros--;
            System.out.println("Mesonero despedido con éxito");
        }else{
            System.out.println("Ya ha despedido a todos los mesoneros");
        }
    }

    public static int getOrdenesArmadas() {
        return ordenesArmadas;
    }

    public static void setOrdenesArmadas(int ordenesArmadas) {
        AplicacionRestaurante.ordenesArmadas = ordenesArmadas;
    }

    public Cronometrador getCronometrador() {
        return cronometrador;
    }

    public Gerente getGerente() {
        return gerente;
    }
    
    public int getCantCocinerosE() {
        return cantCocinerosE;
    }

    public int getCantCocinerosPF() {
        return cantCocinerosPF;
    }

    public int getCantCocinerosP() {
        return cantCocinerosP;
    }

    public int getCantMesoneros() {
        return cantMesoneros;
    }

    public Parametros getParam() {
        return param;
    }

    public Meson getMesonE() {
        return mesonE;
    }

    public Meson getMesonPF() {
        return mesonPF;
    }

    public Meson getMesonP() {
        return mesonP;
    }
    
    
}
