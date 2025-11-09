import java.util.*;
/**
 * La clase Docente se extiende de la clase abstracta Socio, definiendo
 * atributos y comportamientos especificos de la clase.
 *
 * @author Quintana Antonio
 * @version 01/11/2025
 */
public class Docente extends Socio{
    private String area;
    
    /**
     * Constructor de la clase Docente que no recibe ningun prestamo por parametro
     * 
     * @param p_dniSocio es el dni del socio
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de dias de prestamo asignadas
     * @param p_area area a la que pertenece el docente
     */
    public Docente(int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_area){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setArea(p_area);
    }
    
    /**
     * Constructor de la clase Docente que recibe un prestamo por parametro
     * 
     * @param p_dniSocio es el dni del socio
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de dias de prestamo asignadas
     * @param p_prestamo es un prestamo registrado al docente
     * @param p_area area a la que pertenece el docente
     */
    public Docente(int p_dniSocio, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo, String p_area){
        super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamo);
        this.setArea(p_area);
    }
    
    /**
     * Constructor de la clase Docente que recibe un ArrayList de prestamos por parametro
     * 
     * @param p_dniSocio es el dni del socio
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de dias de prestamo asignadas
     * @param p_prestamos es un ArrayList de prestamos registrados al Docente
     * @param p_area area a la que pertenece el docente
     */
    public Docente(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos, String p_area){
        super(p_dniSocio, p_nombre, p_diasPrestamo, p_prestamos);
        this.setArea(p_area);
    }
    //Setter -----------------------------------------------
    private void setArea(String p_area){
        this.area = p_area;
    }
    //Getter -----------------------------------------------
    public String getArea(){
        return this.area;
    }
    
    //otros metodos -----------------------------------------
    /**
     * esResponsable primero analiza si no tiene nigun prestamo vencido reutilizando el metodo puedePedir
     * de la clase padre, luego recorre el ArrayList de prestamos del docente y analiza si algun prestamo
     * devolvió fuera de término.
     * 
     * @return true o false dependiendo si nunca devolvió un libro luego del vencimiento
     */
    public boolean esResponsable(){
        boolean flag = true;
        if( ! this.puedePedir() ){
            return false;
        }        
        
        for(Prestamo unPre : this.getPrestamos()){
            if( unPre.getFechaDevolucion() != null && unPre.vencido(unPre.getFechaDevolucion())){
                flag = false;
            }
            //cambia la bandera si entregó algun libro luego del vencimiento.
        }
        return flag;
    }
    
    /**
     * Cambiar dias de prestamo, permite asignarle más dias de prestamos al docente dependiendo si es resposable
     * o no, agregando los dias que recibe por parametro a la cantidad de dias que ya posee que por defecto son 5
     * 
     * @param p_dias cantidad de dias de prestamos a agregar
     */
    public void cambiarDiasDePrestamo(int p_dias){
        if(this.esResponsable()){
            this.setDiasPrestamo( this.getDiasPrestamo() + p_dias);
            System.out.println("Se agregaron " + p_dias + " de prestamo por ser Responsable");
        }else{
            System.out.println("No se pueden agregar días porque no es responsable");
        }
        
    }
    
    /**
     * Retorna un String con el nombre de la clase o tipo de socio
     * 
     * @return Tipo de socio
     */
    public String soyDeLaClase(){
        return "Docente";
    }
    
       
}