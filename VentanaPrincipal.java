import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.ArrayList;

/**
 * Heredamos de JFrame e implementamos ActionListener para manejar todos los eventos
 * en una sola ventana.
 */
public class VentanaPrincipal extends JFrame implements ActionListener {

    // --- Componentes Estructurales ---
    private Biblioteca biblioteca;
    private JPanel panelNorte;
    private JPanel panelCentro;
    private JTextArea areaResultados;
    
    // --- Botones Menú Principal (Norte) ---
    private JButton bNavLibros, bNavSocios, bNavSalir;

    // --- Componentes de Login ---
    private JTextField tLoginUser;
    private JPasswordField tLoginPass;
    private JButton bLogin;
    
    // --- Botones Menú Libros ---
    private JButton bMenuNuevo, bMenuPrestar, bMenuDevolver, bMenuVencidos, bMenuQuien, bMenuListaLibros, bMenuTitulos;
    private JButton bMenuEliminarLibro; 
    
    // --- Botones Menú Socios ---
    private JButton bMenuEstudiante, bMenuDocente, bMenuCantidad, bMenuResponsables, bMenuListaSocios, bMenuBuscar;
    private JButton bMenuEliminarSocio; 
    
    // --- Componentes de Formularios---
    private JTextField tCampo1, tCampo2, tCampo3, tCampo4;
    private JButton bFormularioAceptar;

    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        // 1. Configurar el JFrame (Ventana)
        this.setTitle("Gestión de Biblioteca"); // Título temporal
        this.setSize(800, 600); 
        this.setLayout(new BorderLayout(5, 5)); 

        // 2. Crear Panel NORTE (Menú Principal)
        panelNorte = new JPanel();
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        
        bNavLibros = new JButton("Gestión de Libros"); 
        bNavSocios = new JButton("Gestión de Socios");
        bNavSalir = new JButton("Salir");
        
        bNavLibros.addActionListener(this); 
        bNavSocios.addActionListener(this);
        bNavSalir.addActionListener(this);
        
        panelNorte.add(bNavLibros);
        panelNorte.add(bNavSocios);
        panelNorte.add(bNavSalir);
        
        // 3. Crear Panel SUR (Resultados)
        areaResultados = new JTextArea(15, 60); 
        JScrollPane scrollPane = new JScrollPane(areaResultados); 
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // 4. Crear Panel CENTRO (Contenido dinámico)
        panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER)); 

        // 5. Añadir paneles al JFrame
        Container c = this.getContentPane(); 
        c.add(panelNorte, BorderLayout.NORTH);
        c.add(panelCentro, BorderLayout.CENTER);
        c.add(scrollPane, BorderLayout.SOUTH);
        
        // 6. Iniciar pidiendo el nombre de la biblioteca
        bNavLibros.setEnabled(false);
        bNavSocios.setEnabled(false);
        bNavSalir.setEnabled(false);
        
        // Usamos el formulario genérico para pedir el nombre
        mostrarFormulario("Aceptar Nombre", "Nombre de la Biblioteca:", null, null, null);
        areaResultados.setText("Bienvenido. Ingrese el nombre de su biblioteca para comenzar.");
        
        // 7. Manejador de cierre
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        this.addWindowListener(new ManejadorCierre()); 
    }

    /**
     * Manejador central de TODOS los eventos de botones
     */
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource(); //

        // --- Evento del Botón "Aceptar" (Formulario Genérico) ---
        if (fuente == bFormularioAceptar) {
            String accion = bFormularioAceptar.getText();
            
            // 1. Manejamos el PRIMER formulario (pedir nombre)
            if (accion.equals("Aceptar Nombre")) {
                String nombre = tCampo1.getText();
                if (nombre == null || nombre.trim().isEmpty()) {
                    areaResultados.setText("Error: El nombre no puede estar vacío.");
                    return;
                }
                // CREAMOS LA BIBLIOTECA
                this.biblioteca = new Biblioteca(nombre.trim()); 
                this.setTitle("Gestión de Biblioteca: " + this.biblioteca.getNombre());
                
                // Pasamos al login
                mostrarPanelLogin(); 
                areaResultados.setText("Biblioteca '" + nombre + "' creada. Ahora inicie sesión.");
            
            // 2. Si no es el primer formulario, procesa la acción normal
            } else {
                procesarFormulario(accion);
            }
        
        // --- Eventos de Login ---
        } else if (fuente == bLogin) {
            validarLogin();
        
        // --- Eventos Menú Principal (Norte) ---
        } else if (fuente == bNavLibros) {
            mostrarMenuLibros();
        } else if (fuente == bNavSocios) {
            mostrarMenuSocios();
        } else if (fuente == bNavSalir) {
            System.exit(0); 
            
        // --- Eventos Menú Libros ---
        } else if (fuente == bMenuNuevo) {
            mostrarFormulario("Nuevo Libro", "Título:", "Edición:", "Editorial:", "Año:");
        } else if (fuente == bMenuPrestar) {
            mostrarFormulario("Prestar Libro", "DNI Socio:", "Nro. Libro (de la lista):", null, null);
            areaResultados.setText(biblioteca.listaDeLibros());
        } else if (fuente == bMenuDevolver) {
            mostrarFormulario("Devolver Libro", "Nro. Libro (de la lista):", null, null, null);
            areaResultados.setText(biblioteca.listaDeLibros());
        } else if (fuente == bMenuVencidos) {
            mostrarPrestamosVencidos();
        } else if (fuente == bMenuQuien) {
            mostrarFormulario("Quién tiene el Libro", "Nro. Libro (de la lista):", null, null, null);
            areaResultados.setText(biblioteca.listaDeLibros());
        } else if (fuente == bMenuListaLibros) {
            areaResultados.setText(biblioteca.listaDeLibros());
            mostrarMenuLibros(); 
        } else if (fuente == bMenuTitulos) {
            areaResultados.setText(biblioteca.listaDeTitulos());
            mostrarMenuLibros();
        } else if (fuente == bMenuEliminarLibro) { 
            mostrarFormulario("Eliminar Libro", "Nro. Libro (de la lista):", null, null, null);
            areaResultados.setText("--- !ADVERTENCIA: ESTA ACCION NO SE PUEDE REVERTIR! ---\n\n" + biblioteca.listaDeLibros());
            
        // --- Eventos Menú Socios ---
        } else if (fuente == bMenuEstudiante) {
            mostrarFormulario("Nuevo Estudiante", "Nombre:", "Carrera:", "DNI:", null);
        } else if (fuente == bMenuDocente) {
            mostrarFormulario("Nuevo Docente", "Nombre:", "Área:", "DNI:", null);
        } else if (fuente == bMenuCantidad) {
            mostrarFormulario("Cantidad por Tipo", "Tipo (Estudiante o Docente):", null, null, null);
        } else if (fuente == bMenuResponsables) {
            areaResultados.setText(biblioteca.listaDeDocentesResponsables());
            mostrarMenuSocios();
        } else if (fuente == bMenuListaSocios) {
            areaResultados.setText(biblioteca.listaDeSocios());
            mostrarMenuSocios();
        } else if (fuente == bMenuBuscar) {
            mostrarFormulario("Buscar Socio", "DNI Socio:", null, null, null);
        } else if (fuente == bMenuEliminarSocio) { 
            mostrarFormulario("Eliminar Socio", "DNI Socio:", null, null, null);
            areaResultados.setText("--- !ADVERTENCIA: ESTA ACCION NO SE PUEDE REVERTIR! ---\n");
        }
    }
    
    // ===================================================================
    // --- Métodos de Lógica de la GUI ---
    // ===================================================================

    /**
     * Limpia el panel central y añade uno nuevo.
     */
    private void cambiarPanelCentro(JPanel nuevoPanel) {
        panelCentro.removeAll(); 
        panelCentro.add(nuevoPanel); 
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    /**
     * Muestra el formulario de Login en el panel central.
     */
    private void mostrarPanelLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); 
        
        panel.add(new JLabel("Usuario:")); 
        tLoginUser = new JTextField("---", 15); 
        panel.add(tLoginUser);
        
        panel.add(new JLabel("Contraseña:"));
        tLoginPass = new JPasswordField("---", 15); 
        panel.add(tLoginPass);
        
        bLogin = new JButton("Ingresar"); 
        bLogin.addActionListener(this); 
        panel.add(bLogin);
        
        // Añadir un componente vacío para alinear el botón
        panel.add(new JLabel("")); 
        
        cambiarPanelCentro(panel);
    }

    /**
     * Valida los datos del login.
     */
    private void validarLogin() {
        String user = tLoginUser.getText(); 
        String pass = new String(tLoginPass.getPassword()); 

        if (user.equals("admin") && pass.equals("1234")) {
            // Login OK - Activamos el menú
            bNavLibros.setEnabled(true);
            bNavSocios.setEnabled(true);
            bNavSalir.setEnabled(true);
            
            panelCentro.removeAll();
            panelCentro.add(new JLabel("¡Bienvenido! Seleccione una opción del menú superior."));
            panelCentro.revalidate();
            panelCentro.repaint();
            areaResultados.setText("Login exitoso.");
        } else {
            areaResultados.setText("Error: Usuario o contraseña incorrectos.");
        }
    }

    /**
     * Muestra el sub-menú de Libros en el panel central.
     */
    private void mostrarMenuLibros() {
        // Usamos GridLayout para un menú más ordenado
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5)); 
        
        bMenuNuevo = new JButton("Nuevo Libro");
        bMenuPrestar = new JButton("Prestar");
        bMenuDevolver = new JButton("Devolver");
        bMenuVencidos = new JButton("Vencidos");
        bMenuQuien = new JButton("Quien lo tiene");
        bMenuListaLibros = new JButton("Lista Libros"); 
        bMenuTitulos = new JButton("Lista Títulos");
        bMenuEliminarLibro = new JButton("Eliminar Libro"); 
        
        bMenuNuevo.addActionListener(this);
        bMenuPrestar.addActionListener(this);
        bMenuDevolver.addActionListener(this);
        bMenuVencidos.addActionListener(this);
        bMenuQuien.addActionListener(this);
        bMenuListaLibros.addActionListener(this); 
        bMenuTitulos.addActionListener(this);
        bMenuEliminarLibro.addActionListener(this); 
        
        panel.add(bMenuNuevo);
        panel.add(bMenuPrestar);
        panel.add(bMenuDevolver);
        panel.add(bMenuVencidos);
        panel.add(bMenuQuien);
        panel.add(bMenuListaLibros); 
        panel.add(bMenuTitulos);
        panel.add(bMenuEliminarLibro);
        
        cambiarPanelCentro(panel);
    }

    /**
     * Muestra el sub-menú de Socios en el panel central.
     */
    private void mostrarMenuSocios() {
        // Usamos GridLayout para un menú más ordenado
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5)); 
        
        bMenuEstudiante = new JButton("Nuevo Estudiante");
        bMenuDocente = new JButton("Nuevo Docente");
        bMenuCantidad = new JButton("Cantidad por Tipo");
        bMenuResponsables = new JButton("Docentes Responsables");
        bMenuListaSocios = new JButton("Lista de Socios"); 
        bMenuBuscar = new JButton("Buscar Socio");
        bMenuEliminarSocio = new JButton("Eliminar Socio");

        bMenuEstudiante.addActionListener(this);
        bMenuDocente.addActionListener(this);
        bMenuCantidad.addActionListener(this);
        bMenuResponsables.addActionListener(this);
        bMenuListaSocios.addActionListener(this); 
        bMenuBuscar.addActionListener(this);
        bMenuEliminarSocio.addActionListener(this);

        panel.add(bMenuEstudiante);
        panel.add(bMenuDocente);
        panel.add(bMenuCantidad);
        panel.add(bMenuResponsables);
        panel.add(bMenuListaSocios); 
        panel.add(bMenuBuscar);
        panel.add(bMenuEliminarSocio);
        
        cambiarPanelCentro(panel);
    }
    
    /**
     * Muestra un formulario genérico en el panel central.
     */
    private void mostrarFormulario(String tituloBoton, String l1, String l2, String l3, String l4) {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5)); 
        
        tCampo1 = new JTextField(20);
        tCampo2 = new JTextField(20);
        tCampo3 = new JTextField(20);
        tCampo4 = new JTextField(20);

        if (l1 != null) {
            formPanel.add(new JLabel(l1)); 
            formPanel.add(tCampo1); 
        }
        if (l2 != null) {
            formPanel.add(new JLabel(l2));
            formPanel.add(tCampo2);
        }
        if (l3 != null) {
            formPanel.add(new JLabel(l3));
            formPanel.add(tCampo3);
        }
        if (l4 != null) {
            formPanel.add(new JLabel(l4));
            formPanel.add(tCampo4);
        }
        
        bFormularioAceptar = new JButton(tituloBoton); 
        bFormularioAceptar.addActionListener(this); 
        formPanel.add(bFormularioAceptar);
        
        cambiarPanelCentro(formPanel);
    }
    
    /**
     * Procesa la lógica de negocio cuando se hace clic en "Aceptar" en un formulario.
     * Incluye todas las validaciones del ejecutable de consola.
     */
    private void procesarFormulario(String accion) {
        if (this.biblioteca == null) {
            areaResultados.setText("Error: La biblioteca no se ha inicializado.");
            return;
        }

        try {
            switch (accion) {
                
                case "Nuevo Libro":
                    String titulo = tCampo1.getText();
                    int edicion = Integer.parseInt(tCampo2.getText());
                    String editorial = tCampo3.getText();
                    int anio = Integer.parseInt(tCampo4.getText());
                    
                    biblioteca.nuevoLibro(titulo, edicion, editorial, anio);
                    areaResultados.setText("Libro '" + titulo + "' guardado.");
                    mostrarMenuLibros(); 
                    break;
                    
                case "Prestar Libro":
                    
                    Socio socio = biblioteca.buscarSocio(Integer.parseInt(tCampo1.getText()));
                    if (socio == null) {
                        areaResultados.setText("Error: El DNI no pertenece a ningún socio.");
                        mostrarMenuLibros();
                        break;
                    }
                    
                    int indicePrestar = Integer.parseInt(tCampo2.getText());
                    if (indicePrestar < 1 || indicePrestar > biblioteca.getArrayLibros().size()) {
                        areaResultados.setText("Error: El Nro. de Libro no existe en la lista.");
                        mostrarMenuLibros();
                        break;
                    }
                    
                    Libro libro = biblioteca.getArrayLibros().get(indicePrestar - 1);
                    
        
                    boolean exito = biblioteca.prestarLibro(Calendar.getInstance(), socio, libro);
                    
                    if (exito) {
                        areaResultados.setText("Préstamo del libro '" + libro.getTitulo() + "' al socio '" + socio.getNombre() + "' registrado con éxito.");
                    } else {
                        areaResultados.setText("No se pudo registrar el préstamo.\n(El libro puede estar ya prestado o el socio no estar habilitado).");
                    }
                
                    mostrarMenuLibros();
                    break;
                    
                case "Devolver Libro":
                   
                    int indiceDev = Integer.parseInt(tCampo1.getText());
                    if (indiceDev < 1 || indiceDev > biblioteca.getArrayLibros().size()) {
                        areaResultados.setText("Error: El Nro. de Libro no existe en la lista.");
                        mostrarMenuLibros();
                        break;
                    }
                    
                    Libro libroDev = biblioteca.getArrayLibros().get(indiceDev - 1);
                    biblioteca.devolverLibro(libroDev);
                    areaResultados.setText("Libro devuelto.");
                    mostrarMenuLibros();
                    break;
                    
                case "Quién tiene el Libro":
                    
                    int indiceQuien = Integer.parseInt(tCampo1.getText());
                    if (indiceQuien < 1 || indiceQuien > biblioteca.getArrayLibros().size()) {
                        areaResultados.setText("Error: El Nro. de Libro no existe en la lista.");
                        mostrarMenuLibros();
                        break;
                    }

                    Libro libroQuien = biblioteca.getArrayLibros().get(indiceQuien - 1);
                    areaResultados.setText("Lo tiene: " + biblioteca.quienTieneElLibro(libroQuien));
                    mostrarMenuLibros();
                    break;
                    
                case "Eliminar Libro": 
                    int indiceElim = Integer.parseInt(tCampo1.getText());
                    if (indiceElim < 1 || indiceElim > biblioteca.getArrayLibros().size()) {
                        areaResultados.setText("Error: El Nro. de Libro no existe en la lista.");
                        mostrarMenuLibros();
                        break;
                    }
                    
                    biblioteca.getArrayLibros().remove(indiceElim - 1);
                    areaResultados.setText("Libro eliminado con éxito.");
                    mostrarMenuLibros();
                    break;


                case "Nuevo Estudiante":
                    biblioteca.nuevoSocioEstudiante(Integer.parseInt(tCampo3.getText()), tCampo1.getText(), tCampo2.getText());
                    areaResultados.setText("Estudiante '" + tCampo1.getText() + "' guardado.");
                    mostrarMenuSocios();
                    break;

                case "Nuevo Docente":
                    biblioteca.nuevoSocioDocente(Integer.parseInt(tCampo3.getText()), tCampo1.getText(), tCampo2.getText());
                    areaResultados.setText("Docente '" + tCampo1.getText() + "' guardado.");
                    mostrarMenuSocios();
                    break;

                case "Cantidad por Tipo":
                    String tipo = tCampo1.getText();
                    if(tipo.equalsIgnoreCase("docente") || tipo.equalsIgnoreCase("estudiante")){
                        int cant = biblioteca.cantidadSociosPorTipos(tipo);
                        areaResultados.setText("Hay " + cant + " socios de tipo '" + tipo + "'.");
                    } else {
                        areaResultados.setText("Error: Tipo no válido. Ingrese 'Estudiante' o 'Docente'.");
                    }
                    mostrarMenuSocios();
                    break;
                    
                case "Buscar Socio":
                    Socio s = biblioteca.buscarSocio(Integer.parseInt(tCampo1.getText()));
                    areaResultados.setText(s != null ? s.toString() : "Socio no encontrado.");
                    mostrarMenuSocios();
                    break;
                    
                case "Eliminar Socio": 
                    int dniElim = Integer.parseInt(tCampo1.getText());
                    Socio socioElim = biblioteca.buscarSocio(dniElim);
                    
                    if (socioElim != null) {
                        biblioteca.getArraySocios().remove(socioElim);
                        areaResultados.setText("Socio eliminado con éxito.");
                    } else {
                        areaResultados.setText("Error: El DNI no pertenece a ningún socio.");
                    }
                    mostrarMenuSocios();
                    break;
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Verifique que los números (DNI, Año, Nro. Libro, etc.) sean correctos.");
        } catch (LibroNoPrestadoException ex) { // Asumimos que esta excepción existe
            areaResultados.setText("Error: " + ex.getMessage());
        } catch (Exception ex) {
            areaResultados.setText("Error inesperado: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    }
    
    /**
     * Muestra la lista de préstamos vencidos en el área de resultados.
     */
    private void mostrarPrestamosVencidos() {
        if (biblioteca.getArrayLibros().isEmpty()) { 
            areaResultados.setText("-- Primero debe agregar libros --");
            mostrarMenuLibros();
            return;
        }
        
        ArrayList<Prestamo> vencidos = biblioteca.prestamosVencidos();
        if (vencidos.isEmpty()) {
            areaResultados.setText("No hay préstamos vencidos.");
        } else {
            areaResultados.setText("--- Préstamos Vencidos ---\n\n");
            for (Prestamo p : vencidos) {
                areaResultados.append(p.toString() + "\n-------------------\n"); 
            }
        }
        mostrarMenuLibros();
    }
    
    /**
     * Clase interna para manejar el evento de cierre de la ventana.
     */
    class ManejadorCierre extends WindowAdapter { 
        public void windowClosing(WindowEvent e) {
            System.exit(0); 
        }
    }
}