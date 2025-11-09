import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.ArrayList;

// Heredamos de JFrame e implementamos ActionListener [cite: 561-562, 856-857]
public class VentanaPrincipal extends JFrame implements ActionListener {

    // --- Componentes Estructurales ---
    private Biblioteca biblioteca; // Se inicializará después de pedir el nombre
    private JPanel panelNorte;
    private JPanel panelCentro;
    private JTextArea areaResultados;
    
    // --- Botones Menú Principal (Norte) ---
    private JButton bNavLibros, bNavSocios, bNavSalir;

    // --- Componentes de Login ---
    private JTextField tLoginUser, tLoginPass;
    private JButton bLogin;
    
    // --- Botones Menú Libros ---
    private JButton bMenuNuevo, bMenuPrestar, bMenuDevolver, bMenuVencidos, bMenuQuien, bMenuListaLibros, bMenuTitulos;
    
    // --- Botones Menú Socios ---
    private JButton bMenuEstudiante, bMenuDocente, bMenuCantidad, bMenuResponsables, bMenuListaSocios, bMenuBuscar;

    // --- Componentes de Formularios (se re-usan) ---
    private JTextField tCampo1, tCampo2, tCampo3, tCampo4;
    private JButton bFormularioAceptar;

    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        // AHORA NO RECIBE LA BIBLIOTECA
        
        // 1. Configurar el JFrame (Ventana)
        this.setTitle("Gestión de Biblioteca"); // Título temporal
        this.setSize(700, 550);
        this.setLayout(new BorderLayout(5, 5)); // [cite: 801-804]

        // 2. Crear Panel NORTE (Menú Principal)
        panelNorte = new JPanel();
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER)); // [cite: 707-709]
        
        bNavLibros = new JButton("Gestión de Libros"); // [cite: 866-869]
        bNavSocios = new JButton("Gestión de Socios");
        bNavSalir = new JButton("Salir");
        
        bNavLibros.addActionListener(this); // [cite: 859-860]
        bNavSocios.addActionListener(this);
        bNavSalir.addActionListener(this);
        
        panelNorte.add(bNavLibros);
        panelNorte.add(bNavSocios);
        panelNorte.add(bNavSalir);
        
        // 3. Crear Panel SUR (Resultados)
        areaResultados = new JTextArea(10, 50); // [cite: 1014-1015]
        JScrollPane scrollPane = new JScrollPane(areaResultados); // [cite: 1033-1034]
        areaResultados.setEditable(false);

        // 4. Crear Panel CENTRO (Contenido dinámico)
        panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER)); // [cite: 707-709]

        // 5. Añadir paneles al JFrame
        Container c = this.getContentPane(); // [cite: 676-678]
        c.add(panelNorte, BorderLayout.NORTH);
        c.add(panelCentro, BorderLayout.CENTER);
        c.add(scrollPane, BorderLayout.SOUTH);
        
        // 6. Iniciar pidiendo el nombre de la biblioteca
        // Desactivamos los botones del menú hasta el login
        bNavLibros.setEnabled(false);
        bNavSocios.setEnabled(false);
        bNavSalir.setEnabled(false);
        
        // Usamos el formulario genérico para pedir el nombre
        mostrarFormulario("Aceptar Nombre", "Nombre de la Biblioteca:", null, null, null);
        areaResultados.setText("Bienvenido. Ingrese el nombre de su biblioteca para comenzar.");
        
        // 7. Manejador de cierre
        this.addWindowListener(new ManejadorCierre()); // [cite: 636-638, 663-665]
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
            System.exit(0); //
            
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
        }
    }
    
    // ===================================================================
    // --- Métodos de Lógica de la GUI ---
    // ===================================================================

    private void cambiarPanelCentro(JPanel nuevoPanel) {
        panelCentro.removeAll(); 
        panelCentro.add(nuevoPanel); 
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    private void mostrarPanelLogin() {
        // No es necesario desactivar los botones, ya lo están

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); // [cite: 735-738]
        
        panel.add(new JLabel("Usuario:")); //
        tLoginUser = new JTextField("///", 15); // [cite: 991-995]
        panel.add(tLoginUser);
        
        panel.add(new JLabel("Contraseña:"));
        tLoginPass = new JTextField("///", 15); 
        panel.add(tLoginPass);
        
        bLogin = new JButton("Ingresar"); // [cite: 866-869]
        bLogin.addActionListener(this); // [cite: 859-860]
        panel.add(bLogin);
        
        cambiarPanelCentro(panel);
    }

    private void validarLogin() {
        String user = tLoginUser.getText(); 
        String pass = tLoginPass.getText();

        if (user.equals("admin") && pass.equals("1234")) {
            // Login OK - Activamos el menú
            bNavLibros.setEnabled(true);
            bNavSocios.setEnabled(true);
            bNavSalir.setEnabled(true); // Activamos Salir también
            
            panelCentro.removeAll();
            panelCentro.add(new JLabel("¡Bienvenido! Seleccione una opción del menú superior."));
            panelCentro.revalidate();
            panelCentro.repaint();
            areaResultados.setText("Login exitoso.");
        } else {
            areaResultados.setText("Error: Usuario o contraseña incorrectos.");
        }
    }

    private void mostrarMenuLibros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // [cite: 707-709]
        
        bMenuNuevo = new JButton("Nuevo Libro");
        bMenuPrestar = new JButton("Prestar");
        bMenuDevolver = new JButton("Devolver");
        bMenuVencidos = new JButton("Vencidos");
        bMenuQuien = new JButton("Quien lo tiene");
        bMenuListaLibros = new JButton("Lista Libros"); 
        bMenuTitulos = new JButton("Lista Títulos");
        
        bMenuNuevo.addActionListener(this);
        bMenuPrestar.addActionListener(this);
        bMenuDevolver.addActionListener(this);
        bMenuVencidos.addActionListener(this);
        bMenuQuien.addActionListener(this);
        bMenuListaLibros.addActionListener(this); 
        bMenuTitulos.addActionListener(this);
        
        panel.add(bMenuNuevo);
        panel.add(bMenuPrestar);
        panel.add(bMenuDevolver);
        panel.add(bMenuVencidos);
        panel.add(bMenuQuien);
        panel.add(bMenuListaLibros); 
        panel.add(bMenuTitulos);
        
        cambiarPanelCentro(panel);
    }

    private void mostrarMenuSocios() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // [cite: 707-709]
        
        bMenuEstudiante = new JButton("Nuevo Estudiante");
        bMenuDocente = new JButton("Nuevo Docente");
        bMenuCantidad = new JButton("Cantidad por Tipo");
        bMenuResponsables = new JButton("Docentes Responsables");
        bMenuListaSocios = new JButton("Lista de Socios"); 
        bMenuBuscar = new JButton("Buscar Socio");

        bMenuEstudiante.addActionListener(this);
        bMenuDocente.addActionListener(this);
        bMenuCantidad.addActionListener(this);
        bMenuResponsables.addActionListener(this);
        bMenuListaSocios.addActionListener(this); 
        bMenuBuscar.addActionListener(this);

        panel.add(bMenuEstudiante);
        panel.add(bMenuDocente);
        panel.add(bMenuCantidad);
        panel.add(bMenuResponsables);
        panel.add(bMenuListaSocios); 
        panel.add(bMenuBuscar);
        
        cambiarPanelCentro(panel);
    }
    
    private void mostrarFormulario(String tituloBoton, String l1, String l2, String l3, String l4) {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // [cite: 735-738]
        
        tCampo1 = new JTextField(20);
        tCampo2 = new JTextField(20);
        tCampo3 = new JTextField(20);
        tCampo4 = new JTextField(20);

        if (l1 != null) {
            formPanel.add(new JLabel(l1)); //
            formPanel.add(tCampo1); //
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
        
        bFormularioAceptar = new JButton(tituloBoton); // [cite: 866-869]
        bFormularioAceptar.addActionListener(this); // [cite: 859-860]
        formPanel.add(bFormularioAceptar);
        
        cambiarPanelCentro(formPanel);
    }
    
    private void procesarFormulario(String accion) {
        // Esta comprobación es vital. Si la biblioteca no se ha creado, no hace nada.
        if (this.biblioteca == null) {
            areaResultados.setText("Error: La biblioteca no se ha inicializado.");
            return;
        }

        try {
            switch (accion) {
                case "Nuevo Libro":
                    biblioteca.nuevoLibro(tCampo1.getText(), Integer.parseInt(tCampo2.getText()), tCampo3.getText(), Integer.parseInt(tCampo4.getText()));
                    areaResultados.setText("Libro '" + tCampo1.getText() + "' guardado.");
                    mostrarMenuLibros(); 
                    break;
                    
                case "Prestar Libro":
                    Socio socio = biblioteca.buscarSocio(Integer.parseInt(tCampo1.getText()));
                    Libro libro = biblioteca.getArrayLibros().get(Integer.parseInt(tCampo2.getText()) - 1);
                    boolean exito = biblioteca.prestarLibro(Calendar.getInstance(), socio, libro);
                    areaResultados.setText(exito ? "Préstamo exitoso." : "No se pudo prestar.");
                    mostrarMenuLibros();
                    break;
                    
                case "Devolver Libro":
                    Libro libroDev = biblioteca.getArrayLibros().get(Integer.parseInt(tCampo1.getText()) - 1);
                    biblioteca.devolverLibro(libroDev);
                    areaResultados.setText("Libro devuelto.");
                    mostrarMenuLibros();
                    break;
                    
                case "Quién tiene el Libro":
                    Libro libroQuien = biblioteca.getArrayLibros().get(Integer.parseInt(tCampo1.getText()) - 1);
                    areaResultados.setText("Lo tiene: " + biblioteca.quienTieneElLibro(libroQuien));
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
                    int cant = biblioteca.cantidadSociosPorTipos(tCampo1.getText());
                    areaResultados.setText("Hay " + cant + " socios de tipo '" + tCampo1.getText() + "'.");
                    mostrarMenuSocios();
                    break;
                    
                case "Buscar Socio":
                    Socio s = biblioteca.buscarSocio(Integer.parseInt(tCampo1.getText()));
                    areaResultados.setText(s != null ? s.toString() : "Socio no encontrado.");
                    mostrarMenuSocios();
                    break;
            }
        } catch (NumberFormatException ex) {
            areaResultados.setText("Error: Verifique que los números (DNI, Año, etc.) sean correctos.");
        } catch (IndexOutOfBoundsException ex) {
            areaResultados.setText("Error: El Nro. de Libro no existe en la lista.");
        } catch (LibroNoPrestadoException ex) {
            areaResultados.setText(ex.getMessage());
        } catch (Exception ex) {
            areaResultados.setText("Error inesperado: " + ex.getMessage());
        }
    }
    
    private void mostrarPrestamosVencidos() {
        ArrayList<Prestamo> vencidos = biblioteca.prestamosVencidos();
        if (vencidos.isEmpty()) {
            areaResultados.setText("No hay préstamos vencidos.");
        } else {
            areaResultados.setText("--- Préstamos Vencidos ---\n");
            for (Prestamo p : vencidos) {
                areaResultados.append(p.toString() + "\n-------------------\n"); // [cite: 1041-1042]
            }
        }
        mostrarMenuLibros();
    }
    
    class ManejadorCierre extends WindowAdapter { // [cite: 663-667]
        public void windowClosing(WindowEvent e) {
            System.exit(0); //
        }
    }
}