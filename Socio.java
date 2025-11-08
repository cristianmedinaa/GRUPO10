import java.util.*;
/**
 * Clase abstracta que contiene el dni, nombre y arrayList de prestamos de un determinado socio..
 * 
 * @author Pannunzio Nicolas
 * @version 06/11/2025
 */
public abstract class Socio
{
    //Variables de instancia.
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;
    
    /**
     * Constructor caso 0.
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo){
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList());
    }
    /**
     * Constructor caso 1.
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo){
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList());
        this.agregarPrestamo(p_prestamo);
    }
    /**
     * Constructor caso *.
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos){
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }
    
    //Setters -----------------------------------
    private void setDniSocio(int p_dniSocio){
        this.dniSocio = p_dniSocio;
    }
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    protected void setDiasPrestamo(int p_diasPrestamo){
        this.diasPrestamo = p_diasPrestamo;
    }
    private void setPrestamos(ArrayList<Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    
    //Getters -----------------------------------
    public int getDniSocio(){
        return this.dniSocio;
    }
    public String getNombre(){
        return this.nombre;
    }
    public int getDiasPrestamo(){
        return this.diasPrestamo;
    }
    public ArrayList<Prestamo> getPrestamos(){
        return this.prestamos;
    }
    
    //Otros metodos
    /**
     * Permite agregar un Prestamo al ArrayList
     * @param Prestamo que sera agregado al arrayList
     * @return True si pudo ser agregado correctamente.
     */
    public boolean agregarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    /**
     * Permite quitar un determinado prestamo del ArrayList.
     * @Prestamo que se desea remover.
     * @return True si se pudo remover el Prestamo correctamente
     */
    public boolean quitarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().remove(p_prestamo);
    }
    /**
     * Devuelve la cantidad de prestamos actuales del socio.
     * @return un int = getPrestamos().size()
     */
    public int cantLibrosPrestados(){
        return this.getPrestamos().size();
    }
    /**
     * Devuelve un string con DNI, Nombre, Clase y cantLibrosPrestados.
     * @return un string con DNI, Nombre, Clase y cantLibrosPrestados.
     */
    public String toString(){
        return ("D.N.I.: "+ this.getDniSocio()+ " || " + this.getNombre()+
                " ("+ this.soyDeLaClase() + ") || Libros Prestados: "
                + this.cantLibrosPrestados() +"\n");
    }
    /**
     * Instancia uns variable temporal Calendar con la fecha actual para verificar, 
     * mediante un Foreach si hay algun prestamo vencido.
     * @return true si no hay un prestamo vencido.
     */
    public boolean puedePedir(){
        Calendar fechaHoy = Calendar.getInstance();
        
        for(Prestamo unPre : this.getPrestamos()){
            if(unPre.getFechaDevolucion() == null && unPre.vencido(fechaHoy)){
               return false;
            }
        }
        //Si la fecha de devolucion es distinta de null no interesa porque ya delvolvió, sea con atraso o no.
        
        return true;
    }
    
    /**
     * Clase abstracta que devuelve un string con el tipo determinado de Socio.
     */
    public abstract String soyDeLaClase();
}
