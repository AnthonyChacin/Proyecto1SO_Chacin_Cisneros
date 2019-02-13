
package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */

//Se gusrdarán los parametros iniciales provenientes del archivo de texto
public class Parametros {
    private int unaHoraEnSegs,
                horasParaElCierre,
                maxMesonEntradas,
                maxMesonPlatosFuertes,
                maxMesonPostres,
                numIniCocE,
                numMaxCocE,
                numIniCocPF,
                numMaxCocPF,
                numIniCocP,
                numMaxCocP,
                numIniMesone,
                numMaxMesone;
    
    private final int DEFAULT_UNA_HORA_EN_SEGS = 10,
                      DEFAULT_HORAS_PARA_CIERRE = 12,
                      DEFAULT_MAX_MESON_ENTRADAS = 20,
                      DEFAULT_MAX_MESON_PLATOS_FUERTES = 30,
                      DEFAULT_MAX_MESON_POSTRES = 10,
                      DEFAULT_NUM_INI_COCINEROS_E = 1,
                      DEFAULT_NUM_MAX_COCINEROS_E = 3,
                      DEFAULT_NUM_INI_COCINEROS_PF = 2,
                      DEFAULT_NUM_MAX_COCINEROS_PF = 4,
                      DEFAULT_NUM_INI_COCINEROS_P = 0,
                      DEFAULT_NUM_MAX_COCINEROS_P = 2,
                      DEFAULT_NUM_INI_MESONEROS = 1,
                      DEFAULT_NUM_MAX_MESONEROS = 6;
    
    public Parametros(String archivo){
        try{
            FileReader fr=new FileReader(archivo);
            BufferedReader br=new BufferedReader(fr);
            
            String line;
            //Leemos hasta llegar a la 5ta línea, que es donde se encuentran los parametros.
            line=br.readLine();
            line=br.readLine();
            line=br.readLine();
            line=br.readLine();
            line=br.readLine();
            
            //Comienza a guardar en el vector sólo en la 5ta línea.
            //Los datos están separados por 2 \t
            String str[] = line.split("\t\t");
            
            // Validacion
            if(esIntPositivo(str[0])){
                this.unaHoraEnSegs = Integer.parseInt(str[0]);
            } else {
                this.unaHoraEnSegs = DEFAULT_UNA_HORA_EN_SEGS;
            }
            
            if(esIntPositivo(str[1])){
                this.horasParaElCierre = Integer.parseInt(str[1]);
            } else {
                this.horasParaElCierre = DEFAULT_HORAS_PARA_CIERRE;
            }
            
            if(esIntPositivo(str[2])){
                this.maxMesonEntradas = Integer.parseInt(str[2]);
            } else {
                this.maxMesonEntradas = DEFAULT_MAX_MESON_ENTRADAS;
            }
            
            if(esIntPositivo(str[3])){
                this.maxMesonPlatosFuertes = Integer.parseInt(str[3]);
            } else {
                this.maxMesonPlatosFuertes = DEFAULT_MAX_MESON_PLATOS_FUERTES;
            }
            
            if(esIntPositivo(str[4])){
                this.maxMesonPostres = Integer.parseInt(str[4]);
            } else {
                this.maxMesonPostres = DEFAULT_MAX_MESON_POSTRES;
            }
            
            if(esIntNoNegativo(str[5])){
                this.numIniCocE = Integer.parseInt(str[5]);
            } else {
                this.numIniCocE = DEFAULT_NUM_INI_COCINEROS_E;
            }
            
            if(esIntPositivo(str[6])){
                // Validar que el inicial no sea mayor que el máximo
                if(this.numIniCocE <= Integer.parseInt(str[6])){
                    this.numMaxCocE = Integer.parseInt(str[6]);
                } else {
                    this.numMaxCocE = this.numIniCocE;
                }
            } else {
                // Intentar asignar el default (comprobar que no sea menor que el mínimo)
                if(this.numIniCocE <= DEFAULT_NUM_MAX_COCINEROS_E){
                    this.numMaxCocE = DEFAULT_NUM_MAX_COCINEROS_E;
                } else {
                    this.numMaxCocE = this.numIniCocE;
                }
            }
            
            if(esIntNoNegativo(str[7])){
                this.numIniCocPF = Integer.parseInt(str[7]);
            } else {
                this.numIniCocPF = DEFAULT_NUM_INI_COCINEROS_PF;
            }
            
            if(esIntPositivo(str[8])){
                // Validar que el inicial no sea mayor que el máximo
                if(this.numIniCocPF <= Integer.parseInt(str[8])){
                    this.numMaxCocPF = Integer.parseInt(str[8]);
                } else {
                    this.numMaxCocPF = this.numIniCocPF;
                }
            } else {
                // Intentar asignar el default (comprobar que no sea menor que el mínimo)
                if(this.numIniCocPF <= DEFAULT_NUM_MAX_COCINEROS_PF){
                    this.numMaxCocPF = DEFAULT_NUM_MAX_COCINEROS_PF;
                } else {
                    this.numMaxCocPF = this.numIniCocPF;
                }
            }
            
            if(esIntNoNegativo(str[9])){
                this.numIniCocP = Integer.parseInt(str[9]);
            } else {
                this.numIniCocP = DEFAULT_NUM_INI_COCINEROS_P;
            }
            
            if(esIntPositivo(str[10])){
                // Validar que el inicial no sea mayor que el máximo
                if(this.numIniCocP <= Integer.parseInt(str[10])){
                    this.numMaxCocP = Integer.parseInt(str[10]);
                } else {
                    this.numMaxCocP = this.numIniCocP;
                }
            } else {
                // Intentar asignar el default (comprobar que no sea menor que el mínimo)
                if(this.numIniCocP <= DEFAULT_NUM_MAX_COCINEROS_P){
                    this.numMaxCocP = DEFAULT_NUM_MAX_COCINEROS_P;
                } else {
                    this.numMaxCocP = this.numIniCocP;
                }
            }
            
            if(esIntNoNegativo(str[11])){
                this.numIniMesone = Integer.parseInt(str[11]);
            } else {
                this.numIniMesone = DEFAULT_NUM_INI_MESONEROS;
            }
            
            if(esIntPositivo(str[12])){
                // Validar que el inicial no sea mayor que el máximo
                if(this.numIniMesone <= Integer.parseInt(str[12])){
                    this.numMaxMesone = Integer.parseInt(str[12]);
                } else {
                    this.numMaxMesone = this.numIniMesone;
                }
            } else {
                // Intentar asignar el default (comprobar que no sea menor que el mínimo)
                if(this.numIniMesone <= DEFAULT_NUM_MAX_MESONEROS){
                    this.numMaxMesone = DEFAULT_NUM_MAX_MESONEROS;
                } else {
                    this.numMaxMesone = this.numIniMesone;
                }
            }
            
        }catch(IOException e){
            System.out.println("Archivo no encontrado");
        }
    }
    
    public boolean esIntNoNegativo(String valor){
        try{
            int x = Integer.parseInt(valor);
            if (x<0) {
                return false;
            } else {
                return true;
            }
        } catch(NumberFormatException e) {
            return false;
        }
    }
    
    public boolean esIntPositivo(String valor){
        if(esIntNoNegativo(valor)){
            if(Integer.parseInt(valor) > 0){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int getUnaHoraEnSegs() {
        return unaHoraEnSegs;
    }

    public void setUnaHoraEnSegs(int unaHoraEnSegs) {
        this.unaHoraEnSegs = unaHoraEnSegs;
    }

    public int getHorasParaElCierre() {
        return horasParaElCierre;
    }

    public void setHorasParaElCierre(int horasParaElCierre) {
        this.horasParaElCierre = horasParaElCierre;
    }

    public int getMaxMesonEntradas() {
        return maxMesonEntradas;
    }

    public void setMaxMesonEntradas(int maxMesonEntradas) {
        this.maxMesonEntradas = maxMesonEntradas;
    }

    public int getMaxMesonPlatosFuertes() {
        return maxMesonPlatosFuertes;
    }

    public void setMaxMesonPlatosFuertes(int maxMesonPlatosFuertes) {
        this.maxMesonPlatosFuertes = maxMesonPlatosFuertes;
    }

    public int getMaxMesonPostres() {
        return maxMesonPostres;
    }

    public void setMaxMesonPostres(int maxMesonPostres) {
        this.maxMesonPostres = maxMesonPostres;
    }

    public int getNumIniCocE() {
        return numIniCocE;
    }

    public void setNumIniCocE(int numIniCocE) {
        this.numIniCocE = numIniCocE;
    }

    public int getNumMaxCocE() {
        return numMaxCocE;
    }

    public void setNumMaxCocE(int numMaxCocE) {
        this.numMaxCocE = numMaxCocE;
    }

    public int getNumIniCocPF() {
        return numIniCocPF;
    }

    public void setNumIniCocPF(int numIniCocPF) {
        this.numIniCocPF = numIniCocPF;
    }

    public int getNumMaxCocPF() {
        return numMaxCocPF;
    }

    public void setNumMaxCocPF(int numMaxCocPF) {
        this.numMaxCocPF = numMaxCocPF;
    }

    public int getNumIniCocP() {
        return numIniCocP;
    }

    public void setNumIniCocP(int numIniCocP) {
        this.numIniCocP = numIniCocP;
    }

    public int getNumMaxCocP() {
        return numMaxCocP;
    }

    public void setNumMaxCocP(int numMaxCocP) {
        this.numMaxCocP = numMaxCocP;
    }

    public int getNumIniMesone() {
        return numIniMesone;
    }

    public void setNumIniMesone(int numIniMesone) {
        this.numIniMesone = numIniMesone;
    }

    public int getNumMaxMesone() {
        return numMaxMesone;
    }

    public void setNumMaxMesone(int numMaxMesone) {
        this.numMaxMesone = numMaxMesone;
    }
    
    
}
