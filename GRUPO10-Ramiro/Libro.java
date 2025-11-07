import java.util.*;

/**
 * Libro reune los datos principales de cada libro, permite informar si esta prestado
 * y cuando fue su ultimo prestamo.
 * 
 * @author Obregon Adrian - Pannunzio Nicolas - Perone Tiziano - Quintana Antonio.
 * @version 31/10/2025
 */
public class Libro{
    //Variables de instancia
    private String titulo;
    private int edicion;
    private String editorial;
    private int anio;
    private ArrayList <Prestamo> prestamos;
    
    /**
     * Contrsuctor caso 0.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(new ArrayList <Prestamo> ());
    }
    /**
     * Constructor caso *.
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, ArrayList <Prestamo> p_prestamos){
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(p_prestamos);
    }
    
    
    //Setters -------------------------------------------------------------------------------------------------------------
    private void setTitulo(String p_titulo){
        this.titulo = p_titulo;
    }
    private void setEdicion(int p_edicion){
        this.edicion = p_edicion;
    }
    private void setEditorial(String p_editorial){
        this.editorial = p_editorial;
    }
    private void setAnio(int p_anio){
        this.anio = p_anio;
    }
    private void setPrestamos(ArrayList <Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    
    
    //Getters --------------------------------------------------------------------------------------------------------------
    public String getTitulo(){
        return this.titulo;
    }
    public int getEdicion(){
        return this.edicion;
    }
    public String getEditorial(){
        return this.editorial;
    }
    public int getAnio(){
        return this.anio;
    }
    public ArrayList <Prestamo> getPrestamos(){
        return this.prestamos;
    }
    
    
    //Metodos Agregar y Quitar del ArrayList --------------------------------------------------------------------------------
    /**
     * Permite agregar un Prestamo al ArrayList prestamos.
     * @param prestamo que sera agregado al ArrayList.
     * @return True si fue agregado, False si no.
     */
    public boolean agregarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    /**
     * Permite remover un Prestamo del ArrayList prestamos.
     * @param prestamo que sera quitado del ArrayList.
     * @return True si fue quitado, False si no.
     */
    public boolean quitarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().remove(p_prestamo);
    }
    
    
    //Otros Metodos ---------------------------------------------------------------------------------------------------------
    /**
     * Si la fechaDevolucion del ultimo prestamo es igual a null, devuelve true.
     * 
     * @return true si el libro se encuentra prestado.
     */
    public boolean prestado(){
        if(this.ultimoPrestamo().getFechaDevolucion() == null){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Devuelve el ultimo Prestamo del ArrayList.
     * Utiliza el metodo .get( .size() - 1 ).
     * 
     * @return ultimo Prestamo del ArrayList.
     */
    public Prestamo ultimoPrestamo(){
        return this.getPrestamos().get(this.getPrestamos().size() - 1);
    }
    /**
     * Devuelve el siguiente String: Titulo: <<titulo>> 
     * @return String con el titulo del libro.
     */
    public String toString(){
        return "Titulo: "+ this.getTitulo();
    }
    
}