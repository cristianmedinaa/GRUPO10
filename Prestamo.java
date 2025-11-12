import java.util.*;

/**
 * Prestamo Permite saber si un determinado libro esta en prestamo, su fecha de retiro
 * y su fecha de devolucion. Junto con el socio que lo solicito.
 * 
 * @author Pannunzio Nicolas.
 * @version 06/11/2025
 */
public class Prestamo{
    //Variables de instancia
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Libro libro;
    private Socio socio;
    
    /**
     * Constructor que recibe todos los atributos por parametro, menos la fecha de devolucion.
     * fechaDevolucion se inicializa en null
     */
    public Prestamo(Calendar p_fechaRetiro, Libro p_libro, Socio p_socio){
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(null);
        this.setLibro(p_libro);
        this.setSocio(p_socio);
    }
    
    
    //Setters ---------------------------------------------------------------------------------------------------
    private void setFechaRetiro(Calendar p_fechaRetiro){
        this.fechaRetiro = p_fechaRetiro;
    }
    private void setFechaDevolucion(Calendar p_fechaDevolucion){
        this.fechaDevolucion = p_fechaDevolucion;
    }
    private void setLibro(Libro p_libro){
        this.libro = p_libro;
    }
    private void setSocio(Socio p_socio){
        this.socio = p_socio;
    }
    
    
    //Getters ---------------------------------------------------------------------------------------------------
    public Calendar getFechaRetiro(){
        return this.fechaRetiro;
    }
    public Calendar getFechaDevolucion(){
        return this.fechaDevolucion;
    }
    public Libro getLibro(){
        return this.libro;
    }
    public Socio getSocio(){
        return this.socio;
    }
    
    
    //Otros Metodos ----------------------------------------------------------------------------------------------
    /**
     * Permite asignarle la fecha en que a sido devuelto el libro
     * @param Calendar, fecha en que fue devuelto el libro.
     */
    public void registrarFechaDevolucion(Calendar p_fecha){
        this.setFechaDevolucion(p_fecha);
    }
    /**
     * Devuelve true si la fecha pasada como parámetro es mayor que la fecha de vencimiento
     * (fecha de retiro más los días de préstamos asignados).
     * Utiliza el metodo .add() de Calendar para sumar los diasPrestamo a la fechaRetiro.
     * Utiliza el metodo .after() de Calendar para comparar las fechas y determinar cual es mayor.
     * 
     * @param la fecha que comparara para saber si el prestamo esta vencido.
     * @return true si la fecha pasada como parámetro es mayor que la fecha de vencimiento.
     */
    public boolean vencido(Calendar p_fecha){
        Calendar fechaVencimiento = this.getFechaRetiro();
        fechaVencimiento.add(Calendar.DAY_OF_MONTH, this.getSocio().getDiasPrestamo());
        if(p_fecha.after(fechaVencimiento)){
            return true;
        }
        return false;
    }
    /**
     * Devuelve el siguiente String:
     *                       Retiro: <<fecha de retiro>> - Devolución: <<fecha de devolución>>
     *                       Libro: <<título del libro>>
     *                       Socio: <<nombre del socio>>
     * *En caso de no haber sido devuelto informa: ** Todavia no a sido devuelto!! **
     * @return string con fechaRetiro, fechaDevolucion, TituloLibro y NombreSocio.
     */
    public String toString(){
        String retiro = "Retiro: "+ this.getFechaRetiro().get(Calendar.YEAR) +"/"
                         + (this.getFechaRetiro().get(Calendar.MONTH) + 1) +"/"
                         + this.getFechaRetiro().get(Calendar.DATE);
                         
        String devolucion = " ";
        if(this.getFechaDevolucion() == null){
            devolucion = "Devolución: ** Todavia no a sido devuelto!! **";
        }else{
            devolucion = "Devolución: "+ this.getFechaDevolucion().get(Calendar.YEAR) +"/"
                         + (this.getFechaDevolucion().get(Calendar.MONTH) + 1) +"/"
                         + this.getFechaDevolucion().get(Calendar.DATE);
        }
                  
        String libro = "Libro: "+ this.getLibro().getTitulo();
        String socio = "Socio: "+ this.getSocio().getNombre();
        
        return retiro +" - "+ devolucion +"\n"+ libro +"\n"+ socio;
    }
}
