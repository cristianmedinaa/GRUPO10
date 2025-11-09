/**
 * Clase Biblioteca que lleva el manejo de los libros y socios (estudiantes y/o docentes)
 * @author Ramiro Nuñez, Medina Cristian, Ramirez Emiliano.
 * @version 07/11/2025
 */

import java.util.*;

public class Biblioteca{
    private String nombre;
    private ArrayList <Libro> libros;
    private ArrayList <Socio> socios;
    
    /**
     * Constructor correspondiente de la clase para la cardinalidad 0
     * @param p_nombre
     */
    public Biblioteca(String p_nombre){
        this.setNombre(p_nombre);
        this.setArrayLibros(new ArrayList <Libro> ());
        this.setArraySocios(new ArrayList <Socio> ());
    }

    /**
     * Constructor para la cardinalidad n
     * @param p_nombre nombre de la biblioteca
     * @param p_libros coleccion de libros
     * @param p_socios colecion de socios
     */
    public Biblioteca(String p_nombre, ArrayList <Libro> p_libros, ArrayList <Socio> p_socios){
        this.setNombre(p_nombre);
        this.setArrayLibros(p_libros);
        this.setArraySocios(p_socios);
    }
    
    /**
     * Set para nombre
     * @param p_nombre
     * @return no retorna nada
     */
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }

    /**
     * Set para la coleccion de libros
     * @param p_libros
     * @return no retorna nada
     */
    private void setArrayLibros(ArrayList <Libro> p_libros){
        this.libros = p_libros;
    }
    public void cargarLibros(ArrayList <Libro> p_libros){
        this.libros = p_libros;
    }

    /**
     * Set para la coleccion de socios
     * @param p_socios
     * @return no retorna nada
     */
    private void setArraySocios(ArrayList <Socio> p_socios){
        this.socios = p_socios;
    }
    public void cargarSocios(ArrayList <Socio> p_socios){
        this.socios = p_socios;
    }
    
    /**
     * Get para nombre
     * @param no recibe parametros
     * @return nombre
     */
    public String getNombre(){
        return this.nombre;
    }

    /**
     * Get para la coleccion de libros
     * @param no recibe parametros
     * @return coleccion de libros
     */
    public ArrayList <Libro> getArrayLibros(){
        return this.libros;
    }

    /**
     * Get para la coleccion de socios
     * @param no recibe parametros
     * @return coleccion de socios
     */
    public ArrayList <Socio> getArraySocios(){
        return this.socios;
    }
    
    /**
     * Metodo que añande un nuevo libro a la coleccion
     * @param p_titulo titulo del libro
     * @param p_edicion edicion del libro
     * @param p_editorial editorial que posee el libro
     * @param p_anio año de produccion
     */
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        this.getArrayLibros().add(new Libro(p_titulo, p_edicion, p_editorial, p_anio));
    }
    
    /**
     * Metodo para quitar libro de la coleccion
     * @param p_libro libroq que se quiere quitar
     * @return retorna true o false dependiendo de si se agrego o no el libro
     */
    public boolean quitarLibro(Libro p_libro){
        return this.getArrayLibros().remove(p_libro);
    }
    
    /**
     * Metodo para agregar un socio de tipo estudiante a la coleccion
     * @param p_dniSocio dni del socio a agregar
     * @param p_nombre nombre del socio a agregar
     * @param p_carrera carrera del socio a agregar
     * @return no retorna nada
     */
    public void nuevoSocioEstudiante(int p_dniSocio , String p_nombre, String p_carrera){
        Estudiante esp = new Estudiante(p_dniSocio, p_nombre, 20, p_carrera);
        this.getArraySocios().add(esp);  
    }
    
    /**Metodo para agregar un socio de tipo estudiante de la coleccion
     * @param p_estudiante a quitar
     */
    public void quitarSocioEstudiante(Estudiante p_estudiante){
        this.getArraySocios().remove(p_estudiante);
    }
    
    /**Metodo para agregar un socio de tipo docente a la coleccion
     * @param p_dnoSocio dni del socio a agregar
     */
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area){
        this.getArraySocios().add(new Docente(p_dniSocio, p_nombre, 5, p_area));
    }
    
    /**Metodo para quitar un socio de tipo docente de la coleccion
     * @param p_docente a quitar
     */
    public void quitarSocioDocente(Docente p_docente){
        this.getArraySocios().remove(p_docente);
    }
    
    /**
     * Metodo para prestar un libro a un socio
     * @param p_fechaRetiro fecha de retiro del libro
     * @param p_socio socio que desea retirar el libro
     * @param p_libro libro que se desea retirar
     * @return retorna true si se pudo prestar, false si no
     */
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro){
        if(p_libro.prestado()){//verificar si el libro ya esta prestado
            System.out.println("\nEl libro ya esta prestado, espera a que lo devuelvan para pedirlo nuevamente.");
            return false;
        }else{
            Prestamo prestamo = new Prestamo(p_fechaRetiro, p_libro, p_socio);//crea un prestamo y luego lo asocia al libro y al socio
            p_libro.agregarPrestamo(prestamo);
            p_socio.agregarPrestamo(prestamo);
            return true;
        }
    }
    
    /**
     * Metodo que devuelve un libro dependiendo de si esta prestado o no
     * @param p_libro libro que se desea devolver
     * @return no retorna nada
     */
    public void devolverLibro(Libro p_libro)throws LibroNoPrestadoException{
        if(p_libro.prestado()){//verifica si el libro esta prestado
            Prestamo ultimo = p_libro.ultimoPrestamo();
            ultimo.registrarFechaDevolucion(Calendar.getInstance());//marca la fecha actual como devolucion
        }else{//si no esta prestado lanza LibroNoPrestadoException
            throw new LibroNoPrestadoException("\n\nEl libro " + p_libro.getTitulo() + " no se puede devolver porque ya se encuentra en la biblioteca.\n\n");
        }
    }
    
    /**
     * Metodo para contar la cantidad de socios segun su tipo(estudiante o docente)
     * @param p_objeto socio
     * @return retorna la cantidad de socio de cada tipo
     */
    public int cantidadSociosPorTipos(String p_objeto){
        int cantSoc = 0;
        for(Socio s : this.getArraySocios()){
            if(s.soyDeLaClase().equalsIgnoreCase(p_objeto)){//utliza el metodo soyDeLaClase() para identificar el tipo
                cantSoc++;
            }
        }
        return cantSoc;
    }
    
    /**
     * Metodo para mostrar los prestamos vencidos
     * @param no recibe parametros
     * @return retorna la coleccion de prestamos vencidos
     */
    public ArrayList<Prestamo> prestamosVencidos(){
        ArrayList<Prestamo> vencidos = new ArrayList<Prestamo>();
        Calendar hoy = Calendar.getInstance();
        for(Libro l : this.getArrayLibros()){//recorre todos los libros
            Prestamo ultimo = l.ultimoPrestamo();//metodo de clase libro
            if(ultimo != null && ultimo.getFechaDevolucion() == null && ultimo.vencido(hoy)){
                vencidos.add(ultimo);//si no esta devuelto y vencido(hoy), se lo agrega
            }
        }
        return vencidos;
    }
    
    /**
     * Metodo para añadir docentes responsables a la coleccion
     * @param no recibe parametros
     * @return no retorna nada
     */
    public ArrayList<Docente> docentesResponsables(){
        ArrayList <Docente> responsables = new ArrayList <Docente>();
        for(Socio s : this.getArraySocios()){
            if(s.soyDeLaClase().equalsIgnoreCase("docente")){
                if (((Docente)s).esResponsable() ) responsables.add((Docente)s);                
            }
        }
        return responsables;
    }

    /**
     * Metodo para saber quien tiene el libro, siempre y cuando éste esté prestado
     * @param p_libro libro que se desea buscar
     * @return retorna un String
    */
    public String quienTieneElLibro(Libro p_libro)throws LibroNoPrestadoException{
        if(p_libro.prestado()){//si esta prestado, devuelve el nombre del socio
            return p_libro.ultimoPrestamo().getSocio().getNombre();
        }else{//si no esta prestado, lanza exception
            throw new LibroNoPrestadoException("\nEl libro se encuentra en la biblioteca");
        }
    }
    
    /**
     * Metodo que realiza un listado de los socios
     * @param no recibe parametros
     * @return retorna el listado en tipo String
     */
    public String listaDeSocios(){
        int cantDoc = 0, cantEst = 0, i = 1;
        String listaSocios = "";
        for(Socio s : this.getArraySocios()){
            if("Docente".equals(s.soyDeLaClase())){
                cantDoc++;
            }else if("Estudiante".equals(s.soyDeLaClase())){
                cantEst++;
            }
            listaSocios += "\n" + i++ + ")" + s.toString() + " || Libros Prestados: " + s.cantLibrosPrestados();
        }
        
        return listaSocios + 
        "\n*******************" + 
        "\nCantidad de Socios del tipo Estudiante: " + cantEst + 
        "\nCantidad de Socios del tipo Docente: " + cantDoc + 
        "\n*******************";

    }
    
    
    /**
     * Metodo que busca un socio por su numero de dni
     * @param p_dni numero de dni del socio
     * @return retorna el socio, si lo encuentra
     */
    public Socio buscarSocio(int p_dni){
        for(Socio s : this.getArraySocios()){
            if(s.getDniSocio() == p_dni){//si existe devuelve el socio
                return s;
            }
        }
        return null;
    }

    /**
     * Metodo que realiza un listado de los libros
     * @param no recibe parametros
     * @return retorna un String de los libros
     */
    public String listaDeLibros(){
        int i = 1;
        String listaLibros = "";
        for(Libro l : this.getArrayLibros()){
            if(l.prestado()){
                listaLibros += "\n" + i++ + ")" + l.toString() + " || Prestado: (Si)";
            }else{
                listaLibros += "\n" + i++ + ")" + l.toString() + " || Prestado: (No)";
            }
        }
        return listaLibros;
    }

    /**
     * Metodo que realiza una lista de docentes responsables
     * @param no recibe parametros
     * @return retorna en String el listado que realiza
     */
    public String listaDeDocentesResponsables(){
        String listaResponsables = "";
        for(Docente d : this.docentesResponsables()){
            listaResponsables += "\n*D.N.I.: " + d.toString();
        }
        return listaResponsables;
    }

    /**
     * Metodo que realiza una lista de titulos disponibles en la biblioteca
     * @param no recibe parametros
     * @return retorna en String el listado que realiza
     */
    /**
     * Método que realiza una lista de títulos disponibles en la biblioteca
     * @return retorna un String con el listado generado
     */
    public String listaDeTitulos() {
        String listado = "\n* Catálogo de libros *\n\n";
        int cantTotal = 0;

        for (Libro l : this.getArrayLibros()) {
            String prestado;
            if(l.prestado()){
                prestado = "Si";
            }else{
                prestado = "No";
            }
            listado += (cantTotal + 1) + ") Titulo: " + l.getTitulo() + " || Prestado: (" + prestado + ")\n";
            cantTotal++;
        }

        listado += "\nCantidad de libros: " + cantTotal;
        return listado;
    }
}