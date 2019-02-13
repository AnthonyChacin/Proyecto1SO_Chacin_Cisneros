
package logica;

/**
 *
 * @authores
    * Anthony Chacin
    * José Cisneros 
 */

//Aquí los cocineros colocan los platos una vez que están listos
public class Meson {
    
    private int capacidadMeson;
    private int[] vectorMeson;
    int cantUnidades;
    
    //Se crea un mesón para un determinado tipo de plato con la capacidad máxima del mesón establecida 
    public Meson(int capacidadMeson){
        this.capacidadMeson = capacidadMeson;
        this.vectorMeson = new int[this.capacidadMeson];
        this.cantUnidades = 0;
    }
    
    //Se obtiene la cantidad de platos que hay en el mesón
    public int platosEnMeson(){
        int cont = 0;
        for (int i = 0; i < this.vectorMeson.length; i++) {
            if(this.vectorMeson[i] == 1){
                cont++;
            }
        }
        return cont;
    }

    public int getCantUnidades() {
        return cantUnidades;
    }

    public void setCantUnidades(int cantUnidades) {
        this.cantUnidades = cantUnidades;
    }
    
    public int getCapacidadMeson() {
        return capacidadMeson;
    }

    public void setCapacidadMeson(int capacidadMeson) {
        this.capacidadMeson = capacidadMeson;
    }

    public int[] getVectorMeson() {
        return vectorMeson;
    }

    public void setVectorMeson(int i, int valor) {
        this.vectorMeson[i] = valor;
    }
}
