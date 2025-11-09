import java.io.*;
import java.util.*;


/**
 * Clase principal que gestiona la Biblioteca, ejecuta los informes
 * y maneja la persistencia de datos usando archivos secuenciales.
 * 
 *
 * @author (Medina Cristian, Nataniel Vallejos,Pannunzio Nicolas, Ramirez Emiliano, Ramiro Nuñez,Quintana Antonio)
 * @version 07/11/2025
 */
public class GestionBiblioteca {
    private static Scanner sc = new Scanner(System.in);
    private static  Biblioteca biblioteca;

    
    
    public static void main(String[] args) {
        sc.useDelimiter("\n");
        int option = 1;
        String nombreBiblioteca, usuario, contrasenia;
        
        //Solicitud de nombre de la biblioteca para la creacion, junto con un login constante
        System.out.println("\fIngrese el nombre de la biblioteca: ");
        nombreBiblioteca = sc.nextLine();
        biblioteca = new Biblioteca(nombreBiblioteca);
        
        System.out.println("Ingrese el usuario: ");
        usuario = sc.nextLine();
        System.out.println("Ingrese la contraseña: ");
        contrasenia = sc.nextLine();
        
        
        //Ventana menu para gestionar socios si el usuario es correcto
        if(usuario.equals("admin") && contrasenia.equals("1234")){
            //Si se acredita el usuario se habilita esta ventana
            while(option != 0){
                //Para poder leer los datos, se da una ventana de tiempo al usuario antes de limpiar
                try {
                    Thread.sleep(1000);
                    System.out.print("\n\n\nRETORNADO A LAS OPCIONES |");
                    Thread.sleep(1000);
                    System.out.print("-----");
                    Thread.sleep(1000);
                    System.out.print("-----");
                    Thread.sleep(1000);
                    System.out.print("|");
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("\f");
                
                
                //Opciones generales para tratar los datos del menu
                System.out.printf("\t\t--BIENVENIDO A LA BIBLIOTECA '%s'--", nombreBiblioteca);
                System.out.println("\n\n\t|1 - Gestion de libros"
                                    + "\n\t|2 - Gestion de socios"
                                    + "\n\t|3 - Salir"
                                    + "\n\nIngrese la opcion a realizar:");
                option = sc.nextInt();
                sc.nextLine();
                
                
                //Switch que despliega dentro suyo un manojo de opciones para gestionar libros o socios
                switch(option){
                    case 1: 
                        int option1;
                        System.out.println("\f\t\t--ESPACIO DE GESTION DE LIBROS--"
                                            + "\n\n\t|1-Nuevo libro"
                                            + "\n\t|2-Prestar libro"
                                            + "\n\t|3-Devolver libro"
                                            + "\n\t|4-Prestamos vencidos"
                                            + "\n\t|5-Quien tiene el libro"
                                            + "\n\t|6-Lista de libros"
                                            + "\n\t|7-Lista de titulos"
                                            + "\n\t|8-Eliminar libro"
                                            + "\n\t|0-Volver al menu principal"
                                            + "\n\nIngrese la opcion a realizar:");
                        option1 = sc.nextInt();
                        sc.nextLine();
                        switch(option1){
                            case 1: 
                                //Linea 391
                                GestionBiblioteca.nuevoLibro();
                                break;
                            case 2: 
                                
                                GestionBiblioteca.prestarLibro();
                                break;
                                
                            case 3: 
                                System.out.println("\f\t\t--ESPACIO 'DEVOLVER LIBRO'--\n\n");
                                
                                if(biblioteca.getArrayLibros().size() > 0){
                                    int libroBusca2;
                                    
                                    System.out.println("\n\nSeleccione un libro prestado (Indice int): ");
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca2 = sc.nextInt();
                                    sc.nextLine();
                                
                                    Libro libroPrestado = biblioteca.getArrayLibros().get(libroBusca2 - 1);
                                    try{
                                        biblioteca.devolverLibro(libroPrestado);
                                        System.out.println("\n\n--El libro a sido devuelto con exito!--\n\n");
                                    }catch(LibroNoPrestadoException e){
                                        System.out.println(e);
                                    }                                                                       
                                }else{
                                    System.out.println("--Primero debe agregar libros (Opcion '1')!--");
                                }
                                
                                break;
                                
                            case 4: 
                                System.out.println("\f\t\t--ESPACIO 'PRESTAMOS VENCIDOS'--\n\n");
                                
                                if(biblioteca.getArrayLibros().size() > 0){
                                    if(biblioteca.prestamosVencidos().size() > 0){
                                        for(Prestamo unPres : biblioteca.prestamosVencidos()){
                                            System.out.println("\n" + unPres.toString() + "\n");
                                        }
                                    }else{
                                        System.out.println("\n--No hay prestamos vencidos actualmente!--\n");
                                    }
                                }else{
                                    System.out.println("\n\n--Primero debe agregar libros (Opcion '1')!--\n\n");
                                }
                                
                                break;
                                
                            case 5: 
                                System.out.println("\f\t\t--ESPACIO 'QUIEN TIENE EL LIBRO'--\n\n");
                                
                                if(biblioteca.getArrayLibros().size() > 0){
                                    int libroBusca2;
                                    
                                    System.out.println("\n\nSeleccione un libro prestado (Indice int): ");
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca2 = sc.nextInt();
                                    sc.nextLine();
                                
                                    Libro libroAPrestar = biblioteca.getArrayLibros().get(libroBusca2 - 1);
                                    
                                    try{
                                        System.out.println(biblioteca.quienTieneElLibro(libroAPrestar));
                                    }catch(LibroNoPrestadoException e){
                                        System.out.println(e);
                                    } 
                                }else{
                                    System.out.println("--Primero debe agregar libros (Opcion '1')!--");
                                }
                                
                                break;
                                
                            case 6:
                                System.out.println("\f\t\t--ESPACIO 'LISTA DE LIBROS'--\n\n");
                                if(biblioteca.getArrayLibros().size() > 0){
                                    System.out.println(biblioteca.listaDeLibros());
                                }else{
                                    System.out.println("--Primero debe agregar libros (Opcion '1')!--");
                                }
                                
                                break;
                                
                            case 7:
                                System.out.println("\f\t\t--ESPACIO 'LISTA DE TITULOS'--\n\n");
                                
                                if(biblioteca.getArrayLibros().size() > 0){
                                    System.out.println(biblioteca.listaDeTitulos());
                                }else{
                                    System.out.println("--Primero debe agregar libros (Opcion '1')!--");
                                }
                                
                                break;
                                
                            case 8:
                                System.out.println("\f\t\t--ESPACIO 'ELIMINAR LIBRO'--\n\n");
            
                                if(biblioteca.getArrayLibros().size() > 0){
                                    int libroBusca3;
                                    System.out.printf("\n!--ADVERTENCIA--: ESTA ACCION NO SE PUEDE REVERTIR!\n\n" 
                                    + "Seleccione un libro a eliminar (Indice int):"); 
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca3 = sc.nextInt();
                                    sc.nextLine();
        
                                    if(libroBusca3 >= 1 && libroBusca3 <= biblioteca.getArrayLibros().size()){
                                        int indiceReal = libroBusca3 - 1; 
                                        biblioteca.getArrayLibros().remove(indiceReal);
            
                                        System.out.printf("\nEl libro ha sido removido con exito!\n");
                                    }else{
                                        System.out.printf("\nEl indice no pertenece a ningun libro\n");
                                    }
                                    }else{
                                        System.out.println("--Primero debe agregar libros (Opcion '1')--\n"); 
                                }
            
                                break;
                                default: 
                                    break;
                        }
                        
                        break;
                        
                    case 2: 
                        int option2;
                        System.out.println("\f\t\t--ESPACIO DE 'GESTION DE SOCIOS'--"
                                                + "\n\n\t|1-Nuevo socio estudiante"
                                                + "\n\t|2-Nuevo socio docente"
                                                + "\n\t|3-Cantidad de socios por tipo"
                                                + "\n\t|4-Lista de docentes responsables"
                                                + "\n\t|5-Lista de socios"
                                                + "\n\t|6-Buscar socio"
                                                + "\n\t|7-Eliminar socio"
                                                + "\n\t|0-Volver al menu principal"
                                                + "\n\nIngrese la opcion a realizar:");
                        option2 = sc.nextInt();
                        sc.nextLine();
                        switch(option2){
                            case 1: 
                                System.out.println("\f\t\t--ESPACIO DE 'INGRESO ESTUDIANTE'--\n\n");
                                int dniSocio1;
                                String nombre1, carrera;
                                                                    
                                System.out.println("Ingrese el nombre del estudiante:");
                                nombre1 = sc.nextLine();
                                    
                                System.out.println("Ingrese la carrera del estudiante:");
                                carrera = sc.nextLine();
                                
                                System.out.println("Ingrese el DNI del estudiante (int):");
                                dniSocio1 = sc.nextInt();
                                sc.nextLine();
                                    
                                biblioteca.nuevoSocioEstudiante(dniSocio1, nombre1, carrera);
                                
                                System.out.printf("\n\nEl estudiante %s fue agregado con exito!\n\n",nombre1);
                                
                                break;
                                
                           case 2: 
                                System.out.println("\f\t\t--ESPACIO DE 'INGRESO PROFESOR'--\n\n");
                                int dniSocio2;
                                String nombre2, area;
                                
                                System.out.println("Ingrese el nombre del docente:");
                                nombre2 = sc.nextLine();
                                    
                                System.out.println("Ingrese el area del docente:");
                                area = sc.nextLine();
                            
                                System.out.println("Ingrese el DNI del docente (int):");
                                dniSocio2 = sc.nextInt();
                                sc.nextLine();
                                
                                biblioteca.nuevoSocioDocente(dniSocio2, nombre2, area);
                                
                                System.out.printf("\n\nEl docente %s fue agregado con exito!\n\n",nombre2);
                                
                                break;
                                
                           case 3: 
                                System.out.println("\f\t\t--ESPACIO 'CONTADOR POR TIPO DE SOCIO'--\n\n");
                                   
                                if(biblioteca.getArraySocios().size() > 0){
                                   String tipo;
                                   System.out.println("Ingrese el tipo (ESTUDIANTE/DOCENTE):");
                                   tipo = sc.nextLine();
                                   if(tipo.equalsIgnoreCase("docente") || tipo.equalsIgnoreCase("estudiante")){
                                       System.out.printf("\n\nLa cantidad de socios del tipo '%s' es: %d", tipo, biblioteca.cantidadSociosPorTipos(tipo));
                                   }else{
                                       System.out.println("\n\nNo ingreso un tipo valido!"); 
                                   }
                                }else{
                                   System.out.println("--Primero debe cargar socios!(Opcion '2')--\n"); 
                                }
                                     
                                break;
                                   
                           case 4:    
                                System.out.println("\f\t\t--ESPACIO 'LISTADO DE DOCENTES RESPONSABLES'--\n\n");
                                   
                                if(biblioteca.getArraySocios().size() > 0){
                                    System.out.printf("\t\t---Lista de Docentes Responsables:---\n\n%s", biblioteca.listaDeDocentesResponsables());
                                }else{
                                    System.out.println("--Primero debe cargar socios! (Opcion '2')--\n");
                                }
                                    
                                break;
                                
                           case 5:
                                System.out.println("\f\t\t--ESPACIO 'LISTADO DE SOCIOS'--\n\n");
                                   
                                if(biblioteca.getArraySocios().size() > 0){
                                   System.out.printf("\t\t---Lista de socios:---\n\n%s", biblioteca.listaDeSocios());
                                }else{
                                    System.out.println("--Primero debe cargar socios! (Opcion '2')--\n");
                                }
                                    
                               break;
                                   
                           case 6:
                                System.out.println("\f\t\t--ESPACIO 'BUSCAR SOCIO'--\n\n");
                                   
                                if(biblioteca.getArraySocios().size() > 0){
                                    int dniSocio;
                                    System.out.printf("\nIngrese el DNI del socio a buscar (sin puntos):"); 
                                    dniSocio = sc.nextInt();
                                    sc.nextLine();
                                    
                                    Socio socioEncontrado = biblioteca.buscarSocio(dniSocio);
                                    if(socioEncontrado != null){
                                        System.out.printf("\nEl DNI pertenece a '%s' y es un socio de la biblioteca!\n", socioEncontrado.getNombre());
                                    }else{
                                        System.out.printf("\nEl DNI no pertenece a ningun socio\n");
                                    }
                                    }else{
                                        System.out.println("--Primero debe cargar socios (Opcion '2')--\n");
                                    }
                                    
                                break;
                            
                           case 7:
                                System.out.println("\f\t\t--ESPACIO 'ELIMINAR SOCIO'--\n\n");
                                   
                                if(biblioteca.getArraySocios().size() > 0){
                                    int dniSocio3;
                                    System.out.printf("\n!--ADVERTENCIA--: ESTA ACCION NO SE PUEDE REVERTIR!\n\n" 
                                                        + "Ingrese el DNI del socio a eliminar (sin puntos):"); 
                                    dniSocio3 = sc.nextInt();
                                    sc.nextLine();
                                    
                                    Socio socioEncontrado = biblioteca.buscarSocio(dniSocio3);
                                    if(socioEncontrado != null){
                                        biblioteca.getArraySocios().remove(socioEncontrado);
                                        System.out.printf("\nEl socio ha sido removido con exito!\n");
                                    }else{
                                        System.out.printf("\nEl DNI no pertenece a ningun socio\n");
                                    }
                                    }else{
                                        System.out.println("--Primero debe cargar socios (Opcion '2')--\n");
                                }
                                    
                                break;
                            
                                default: 
                                    break; 
                            }
                            
                            break;
                        case 3: 
                            System.out.printf("\f\t\t---Gracias por haber utilizado los servicios de gestion para '%s', hasta pronto!---", nombreBiblioteca);
                            option = 0;
                            break;
                }
            }
        }else{
            System.out.println("El usuario o contraseña no son correctos");//deberia llamar nuevamente al menu de login para poder volver a ingresar el usuario y nombre
        }
    }
    
                 
    private static void nuevoLibro(){
        System.out.println("\f\t\t--ESPACIO 'NUEVO LIBRO'--\n\n");
                                
        String tituloP;
        int edicionP;
        String editorialP;
        int anioP;
                               
        System.out.println("Ingrese el nombre del libro:");
        tituloP = sc.nextLine();
                             
        System.out.println("Ingrese la edicion del libro (int):");
        edicionP = sc.nextInt();
        sc.nextLine();
                               
        System.out.println("Ingrese la editorial del libro:");
        editorialP = sc.nextLine();
                                
        System.out.println("Ingrese el año del libro:");
        anioP = sc.nextInt();
        sc.nextLine();
                                
        biblioteca.nuevoLibro(tituloP, edicionP, editorialP, anioP);
                                
        System.out.printf("\n\nEl libro %s a sido agregado con exito!",tituloP);
    }
    private static void prestarLibro(){
        System.out.println("\f\t\t--ESPACIO 'PRESTAR LIBRO'--\n\n");
                                
                                if(biblioteca.getArrayLibros().size() > 0){
                                    int dniBusca,libroBusca;
                                    
                                    System.out.println("Ingrese el DNI del socio al que desea prestar");
                                    dniBusca = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Seleccione un libro que no este Prestado (Indice int): ");
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca = sc.nextInt();
                                    sc.nextLine();
                                    
    
                                    Socio socioAPrestar = biblioteca.buscarSocio(dniBusca);
                                    Libro libroAPrestar = biblioteca.getArrayLibros().get(libroBusca - 1);
                        
                                    if(socioAPrestar == null){
                                        System.out.println("\n\n--No se encontro el socio relacionado a ese DNI--\n\n");
                                    } else {
                                        Calendar fechaHoy = Calendar.getInstance();
                                        System.out.printf("\n\nEl prestamo del libro %s fue '%s'",libroAPrestar.getTitulo(),  biblioteca.prestarLibro(fechaHoy, socioAPrestar, libroAPrestar));
                                    }
                                }else{
                                    System.out.println("--Primero debe agregar libros (Opcion '1')!--");
                                }
    }
    private static void devolverLibro(){}
    private static void prestamosVencidos(){}
    private static void quienLoTiene(){}
    private static void listaLibros(){}
    private static void listaTitulos(){}
    private static void eliminarLibro(){}
    
    
    
    
}
