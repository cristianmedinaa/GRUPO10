import java.util.*;
/**
 * La clase Estudiante se extiende de la clase abstracta Socio, definiendo
 * atributos y comportamientos especificos de la clase.
 *
 * @author Quintana Antonio
 * @version 01/11/2025
 */
public class Estudiante extends Socio{
    private String carrera;
    
    /**
     * Constructor de la clase Estudiante que no recibe ningun prestamo por parametro
     * 
     * @param p_dniSocio es el dni del socio
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de dias de prestamo asignadas
     * @param p_carrera carrera a la que pertenece el estudiante
     */
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setCarrera(p_carrera);
    }
    /**
     * Constructor de la clase Estudiante que recibe un solo prestamo por paramatro
     * 
     * @param p_dniSocio es el dni del socio
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de dias de prestamo asignadas
     * @param p_prestamo es un prestamo registrado al estudiante
     * @param p_carrera carrera a la que pertenece el estudiante
     */
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamo);
        this.setCarrera(p_carrera);
    }
    /**
     * Constructor de la clase Estudiante que recibe un arrayList de prestamos
     * 
     * @param p_dniSocio es el dni del socio
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de dias de prestamo asignadas
     * @param p_prestamo es un arrayList de prestamos registrados al estudiante
     * @param p_carrera carrera a la que pertenece el estudiante
     */
    public Estudiante(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamos);
        this.setCarrera(p_carrera);
    }
    //Setter ------------------------------------------------
    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }
    //Getter ------------------------------------------------
    public String getCarrera(){
        return this.carrera;
    }
    //otros metodos -----------------------------------------
    /**
     * puedePerdir redefine y especializa el comportamiento para la clase estudiante, donde primero analiza si el socio no
     * posee prestamos vencidos y luego como es un estudiante se pregunta si no posee mas de 3 prestamos en su poder.
     * 
     * @return true o false dependiendo si cumple con la condiciones para pedir un libro
     */
    public boolean puedePedir(){
        int librosSinDevolver = 0;
        if(!super.puedePedir()) return false; //si poseee algun prestamo vencido ya retorna null y sale
        
        for(Prestamo unPre : this.getPrestamos()){
            if(unPre.getFechaDevolucion() == null) librosSinDevolver++;
        }//por cada prestamo pregunta si la fecha de devolucion es null, es decir que no lo devolvió aun, y lo cuenta como libro sin devolver
        
        if(librosSinDevolver <= 3){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Retorna un String con el nombre de la clase o tipo de socio
     * 
     * @return Tipo de socio
     */
    public String soyDeLaClase(){
        return "Estudiante";
    }
    
}