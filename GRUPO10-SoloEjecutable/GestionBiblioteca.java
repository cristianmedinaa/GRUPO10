import java.io.*;
import java.util.*;


/**
 * Clase principal que gestiona la Biblioteca, ejecuta los informes
 * y maneja la persistencia de datos usando archivos secuenciales.
 * 
 *
 * @author (Nataniel Vallejos y Cristian Medina)
 * @version 07/11/2025
 */
public class GestionBiblioteca {

    public static void main(String[] args) {
        Biblioteca biblioteca;
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        int option = 1;
        String nombreBiblioteca, usuario, contrasenia;
        
        
        System.out.println("\fIngrese el nombre de la biblioteca: ");
        nombreBiblioteca = sc.nextLine();
        
        biblioteca = new Biblioteca(nombreBiblioteca);
        
        System.out.println("Ingrese el usuario: ");
        usuario = sc.nextLine();
        
        System.out.println("Ingrese la contraseña: ");
        contrasenia = sc.nextLine();        
        //ESTO DEBE SER UNA VENTANA, SI EL USUARIO Y LA CONTRASENIA CONCUERDA DENTRO DE LA EJECUCION DEBE ABRIRSE OTRA VENTANA
        
        if(usuario.equals("admin") && contrasenia.equals("1234")){
            //ESTA ES LA VENTANA A LA QUE SE DEBE ACCEDER UNA VEZ EL USUARIO Y LA CONTRASENIA COINCIDEN
            while(option != 0){
                try {
                    // Pausa la ejecución por 2000 milisegundos (2 segundos)
                    Thread.sleep(1000);
                    System.out.print("\n\n\n\nRETORNADO A LAS OPCIONES |");
                    Thread.sleep(1000);
                    System.out.print("-----");
                    Thread.sleep(1000);
                    System.out.print("-----");
                    Thread.sleep(1000);
                    System.out.print("|");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Esto se ejecutaría si otro hilo "despierta" a este
                    // hilo antes de tiempo.
                    e.printStackTrace();
                }
                System.out.println("\f");
                System.out.printf("\t\t--BIENVENIDO A LA BIBLIOTECA '%s'--", nombreBiblioteca);
                System.out.println("\n\n\t|1 - Gestion de libros"
                                    + "\n\t|2 - Gestion de socios"//Cargar un nuevo socio
                                    + "\n\t|3 - Salir"
                                    + "\n\nIngrese la opcion a realizar:");
                option = sc.nextInt();
                sc.nextLine();
                
                //CADA SWITCH DEBE SER UNA VENTANA NUEVA, QUE DEJE ENTRAR A OTRA VENTANA DONDE ESTARAN LAS OPCIONES, TAL COMO EN EL CASE FIGURA
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
                                            + "\n\nIngrese la opcion a realizar:");
                        option1 = sc.nextInt();
                        sc.nextLine();
                        switch(option1){
                            case 1: 
                                System.out.println("\f\t\t--ESPACIO NUEVO LIBRO--\n\n");
                                
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
                                
                                break;
                            case 2: 
                                System.out.println("\f\t\t--ESPACIO PRESTAR LIBRO--\n\n");
                                
                                if(biblioteca.getArrayLibros() != null){
                                    int dniBusca,libroBusca;
                                    
                                    System.out.println("Ingrese el DNI del socio al que desea prestar");
                                    dniBusca = sc.nextInt();
                                    System.out.println("Seleccione un libro que no este Prestado: ");
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca = sc.nextInt();
                                    
    
                                    Socio socioAPrestar = biblioteca.buscarSocio(dniBusca);
                                    Libro libroAPrestar = biblioteca.getArrayLibros().get(libroBusca - 1);
                        
                                    if(socioAPrestar == null){
                                        System.out.println("\n\nNo se encontro el socio relacionado a ese DNI\n\n");
                                    } else {
                                        Calendar fechaHoy = Calendar.getInstance();
                                        System.out.println("\n\nSe realizo el prestamo con exito? --> "+ 
                                        biblioteca.prestarLibro(fechaHoy, socioAPrestar, libroAPrestar) + "\n\n");
                                    }
                                }else{
                                    System.out.println("\n\nPrimero debe agregar libros!\n\n");
                                }
                                
                                break;
                                
                                case 3: 
                                System.out.println("\f\t\t--ESPACIO DEVOLVER LIBRO--\n\n");
                                
                                if(biblioteca.getArrayLibros() != null){
                                    int libroBusca2;
                                    
                                    System.out.println("\n\nSeleccione un libro prestado: ");
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca2 = sc.nextInt();
                                
                                    Libro libroPrestado = biblioteca.getArrayLibros().get(libroBusca2 - 1);
                                    try{
                                        biblioteca.devolverLibro(libroPrestado);
                                        System.out.println("\n\nEl libro a sido devuelto con exito!\n\n");
                                    }catch(LibroNoPrestadoException e){
                                        System.out.println(e);
                                    }                                                                       
                                }else{
                                    System.out.println("\n\nPrimero debe agregar libros!\n\n");
                                }
                                
                                break;
                                
                                case 4: 
                                System.out.println("\f\t\t--ESPACIO PRESTAMOS VENCIDOS--\n\n");
                                
                                if(biblioteca.getArrayLibros() != null){
                                    for(Prestamo unPres : biblioteca.prestamosVencidos()){
                                        System.out.println("\n" + unPres.toString() + "\n");
                                    }                                            
                                }else{
                                    System.out.println("\n\nPrimero debe agregar libros!\n\n");
                                }
                                
                                break;
                                
                                case 5: 
                                System.out.println("\f\t\t--ESPACIO QUIEN TIENE EL LIBRO--\n\n");
                                
                                if(biblioteca.getArrayLibros() != null){
                                    int libroBusca2;
                                    
                                    System.out.println("\n\nSeleccione un libro prestado: ");
                                    System.out.println(biblioteca.listaDeLibros());
                                    libroBusca2 = sc.nextInt();
                                
                                    Libro libroAPrestar = biblioteca.getArrayLibros().get(libroBusca2 - 1);
                                    
                                    
                                    try{
                                        System.out.println(biblioteca.quienTieneElLibro(libroAPrestar));
                                       
                                    }catch(LibroNoPrestadoException e){
                                        System.out.println(e);
                                    } 
                                }else{
                                    System.out.println("\n\nPrimero debe agregar libros!\n\n");
                                }
                                
                                break;
                                
                                case 6:
                                System.out.println("\f\t\t--ESPACIO 'LISTA DE LIBROS'--\n\n");
                                if(biblioteca.getArrayLibros() != null){
                                    System.out.println(biblioteca.listaDeLibros());
                                }else{
                                    System.out.println("\n\nPrimero debe agregar libros!\n\n");
                                }
                                
                                break;
                                
                                case 7:
                                System.out.println("\f\t\t--ESPACIO 'LISTA DE TITULOS'--\n\n");
                                
                                if(biblioteca.getArrayLibros() != null){
                                    System.out.println(biblioteca.listaDeTitulos());
                                }else{
                                    System.out.println("Primero debe agregar libros!");
                                }
                                
                                break;
                            default: break;
                        }
                        break;
                        
                    case 2: 
                        int option2;
                        System.out.println("\f\t\t--ESPACIO DE GESTION DE SOCIOS--"
                                                + "\n\n\t|1-Nuevo socio estudiante"
                                                + "\n\t|2-Nuevo socio docente"
                                                + "\n\t|3-Cantidad de socios por tipo"
                                                + "\n\t|4-Lista de docentes responsables"
                                                + "\n\t|5-Lista de socios"
                                                + "\n\t|6-Buscar socio"
                                                + "\n\nIngrese la opcion a realizar:");
                        option2 = sc.nextInt();
                        sc.nextLine();
                        switch(option2){
                            case 1: 
                                System.out.println("\f\t\t--ESPACIO DE INGRESO ESTUDIANTE--\n\n");
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
                                       
                                break;
                           case 2: 
                                    System.out.println("\f\t\t--ESPACIO DE INGRESO PROFESOR--\n\n");
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
                                    
                                break;
                           case 3: 
                                   System.out.println("\f\t\t--ESPACIO CONTADOR POR TIPO DE SOCIO--\n\n");
                                   
                                   if(biblioteca.getArraySocios() != null){
                                       String tipo;
                                       System.out.println("Ingrese el tipo (ESTUDIANTE/DOCENTE):");
                                       tipo = sc.nextLine();
                                       if(tipo.equalsIgnoreCase("docente") || tipo.equalsIgnoreCase("estudiante")){
                                           System.out.printf("La cantidad de socios del tipo '%s' es: %d", tipo, biblioteca.cantidadSociosPorTipos(tipo));
                                       }else{
                                           System.out.println("No ingreso un tipo valido!"); 
                                       }
                                    }else{
                                       System.out.println("Primero debe cargar socios (Opcion '2')\n\n"); 
                                   }
                                   
                                   break;
                               case 4:    
                                   System.out.println("\f\t\t--ESPACIO 'LISTADO DE DOCENTES RESPONSABLES'--\n\n");
                                   
                                   if(biblioteca.getArraySocios() != null){
                                       System.out.printf("\f\t\t---Lista de Docentes Responsables:---\n\n%s", biblioteca.listaDeDocentesResponsables());
                                    }else{
                                        System.out.println("Primero debe cargar socios (Opcion '2')\n\n");
                                    }
                                    
                                   break;
                                
                               case 5:
                                   System.out.println("\f\t\t--ESPACIO 'LISTADO DE SOCIOS'--\n\n");
                                   
                                   if(biblioteca.getArraySocios() != null){
                                       System.out.printf("\n\n\t\t---Lista de socios:---\n\n%s", biblioteca.listaDeSocios());
                                    }else{
                                        System.out.println("Primero debe cargar socios (Opcion '2')\n\n");
                                    }
                                    
                                   break;
                               case 6:
                                   System.out.println("\f\t\t--ESPACIO 'LISTADO DE SOCIOS'--\n\n");
                                   
                                   if(biblioteca.getArraySocios() != null){
                                       int dniSocio;
                                       System.out.printf("\nIngrese el DNI del socio a buscar (sin puntos):"); 
                                       dniSocio = sc.nextInt();
                                       Socio socioEncontrado = biblioteca.buscarSocio(dniSocio);
                                       if(socioEncontrado != null){
                                            System.out.printf("El DNI pertenece a %s y es un socio", socioEncontrado.getNombre());
                                        }
                                    }else{
                                        System.out.println("Primero debe cargar socios (Opcion '2')\n\n");
                                    }
                                    
                                   break;
                               default: break; 
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
    
}
