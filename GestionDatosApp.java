import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionDatosApp extends JFrame {
    private JTextField campoTexto;
    private JButton botonAgregar;
    private JButton botonLimpiar;
    private JButton botonEliminar;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaDatos;
    private JLabel etiquetaEstado;

    public GestionDatosApp() {
        // Configurar la ventana principal
        setTitle("Gestión de Datos - Aplicación GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Centrar la ventana
        
        // Inicializar componentes
        inicializarComponentes();
        
        // Configurar el diseño
        configurarLayout();
        
        // Configurar eventos
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Campo de texto para entrada de datos
        campoTexto = new JTextField(20);
        
        // Botones
        botonAgregar = new JButton("Agregar");
        botonLimpiar = new JButton("Limpiar Todo");
        botonEliminar = new JButton("Eliminar Seleccionado");
        
        // Modelo y lista para mostrar datos
        modeloLista = new DefaultListModel<>();
        listaDatos = new JList<>(modeloLista);
        listaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Etiqueta de estado
        etiquetaEstado = new JLabel("Listo para agregar datos");
        etiquetaEstado.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void configurarLayout() {
        // Panel principal con BorderLayout
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
        
        // Panel superior para título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel titulo = new JLabel("Sistema de Gestión de Datos");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        panelTitulo.add(titulo);
        
        // Panel para entrada de datos
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEntrada.setBackground(new Color(220, 220, 220));
        panelEntrada.add(new JLabel("Ingrese dato:"));
        panelEntrada.add(campoTexto);
        panelEntrada.add(botonAgregar);
        
        // Panel para botones de control
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(new Color(220, 220, 220));
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonEliminar);
        
        // Panel inferior que combina botones + estado
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(etiquetaEstado, BorderLayout.SOUTH);
        
        // Panel central para la lista
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)), 
                "Datos Agregados"));
        panelCentral.add(new JScrollPane(listaDatos), BorderLayout.CENTER);
        
        // Agregar componentes a la ventana principal
        add(panelTitulo, BorderLayout.NORTH);
        add(panelEntrada, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Ajustar tamaño del panel de lista
        panelCentral.setPreferredSize(new Dimension(200, 0));
    }

    private void configurarEventos() {
        // Evento para botón Agregar
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDato();
            }
        });

        // Evento para botón Limpiar
        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTodo();
            }
        });
        
        // Evento para botón Eliminar
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSeleccionado();
            }
        });

        // Evento para la lista (selección)
        listaDatos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = listaDatos.getSelectedIndex();
                    if (selectedIndex != -1) {
                        etiquetaEstado.setText("Seleccionado: " + modeloLista.getElementAt(selectedIndex));
                    }
                }
            }
        });

        // Evento para Enter en el campo de texto
        campoTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDato();
            }
        });
    }

    private void agregarDato() {
        String dato = campoTexto.getText().trim();
        
        // Validar entrada vacía
        if (dato.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese un dato válido", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Agregar dato a la lista
        modeloLista.addElement(dato);
        campoTexto.setText(""); // Limpiar campo
        etiquetaEstado.setText("Dato agregado: " + dato);
    }

    private void limpiarTodo() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar todos los datos?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            modeloLista.clear();
            campoTexto.setText("");
            listaDatos.clearSelection();
            etiquetaEstado.setText("Todos los datos han sido eliminados");
        }
    }
    
    private void eliminarSeleccionado() {
        int selectedIndex = listaDatos.getSelectedIndex();
        if (selectedIndex != -1) {
            String datoEliminado = modeloLista.getElementAt(selectedIndex);
            modeloLista.remove(selectedIndex);
            etiquetaEstado.setText("Dato eliminado: " + datoEliminado);
        } else {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un elemento para eliminar",
                "Error",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Ejecutar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Establecer look and feel del sistema
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                GestionDatosApp app = new GestionDatosApp();
                app.setVisible(true);
            }
        });
    }
}
