/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Logica.*;
import java.awt.GridLayout;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Bravo
 */
public class Interfaz extends javax.swing.JFrame {

    private Producto producto = new Producto();
    private Cliente cliente = new Cliente(this);
    private JSONObject rootObject;

    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
        //desactivarPaneles();
        JSONParser parser = new JSONParser();
        try {
            rootObject = (JSONObject) parser.parse(new FileReader("C:/Users/Bravo/Documents/Prueba-Proyecto2/PruebaProyecto2 CarlosBravo/src/Logica/Productos.json"));
            System.out.print("Productos agregados al panel");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        llenarTablas();
    }

    private void llenarTablas() {
        JTable[] tablas = {tblPollo, tblRes, tblCerdo, tblMariscos, tblFrutas, tblVerduras, tblEmbutidos};

        for (JTable tabla : tablas) {
            DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
            modeloTabla.setRowCount(0);
            producto.actualizarTabla(modeloTabla, obtenerCategoriaPorTabla(tabla));
        }
    }

    private String obtenerCategoriaPorTabla(JTable tabla) {
        if (tabla == tblPollo) {
            System.out.print("Productos agregados al panel pllo");
            return "Pollo";
        } else if (tabla == tblRes) {
            return "Res";
        } else if (tabla == tblCerdo) {
            return "Cerdo";
        } else if (tabla == tblMariscos) {
            return "Mariscos";
        } else if (tabla == tblFrutas) {
            return "Frutas";
        } else if (tabla == tblVerduras) {
            return "Verduras";
        } else if (tabla == tblEmbutidos) {
            return "Embutidos";
        } else {
            return "";
        }
    }

    public void actualizarTablaCliente() {
        cliente.actualizarTabla((DefaultTableModel) tblClientes.getModel());
    }

    private void limpiarCampos() {
        this.txtCedula.setText("");
        this.txtNombre.setText("");
        this.txtPrimerA.setText("");
        this.txtSegundoA.setText("");
        this.txtTelefono.setText("");
        this.txtCorreo.setText("");
    }

    // Método para verificar si los campos están vacíos
    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void desactivarPaneles() {
        for (int i = 1; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setEnabledAt(i, false);
        }
    }

    private void activarPaneles() {
        for (int i = 1; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setEnabledAt(i, true);
        }
    }

    public void mostrarProductosEnPaneles(String categoria) {
        JSONObject categoriasObject = (JSONObject) rootObject.get("categorias");
        JSONObject categoriaObject = (JSONObject) categoriasObject.get(categoria);
        JSONObject subcategoriasObject = (JSONObject) categoriaObject.get("subcategorias");

        JPanel categoriaPanel = obtenerPanelPorCategoria(categoria);
        categoriaPanel.setLayout(new GridLayout(0, 6));

        for (Object subcategoriaKey : subcategoriasObject.keySet()) {
            JSONArray productosArray = (JSONArray) subcategoriasObject.get(subcategoriaKey);

            for (Object obj : productosArray) {
                JSONObject productoJSON = (JSONObject) obj;
                JPanel productoPanel = crearPanelProducto(productoJSON);
                categoriaPanel.add(productoPanel);
            }
        }

        categoriaPanel.revalidate();
        categoriaPanel.repaint();
    }

    private JPanel obtenerPanelPorCategoria(String categoria) {
        if (categoria.equals("Pollo")) {
            return jpPollo;
        } else if (categoria.equals("Res")) {
            return jpRes;
        } else if (categoria.equals("Cerdo")) {
            return jpCerdo;
        } else if (categoria.equals("Mariscos")) {
            return jpMariscos;
        } else if (categoria.equals("Frutas")) {
            return jpFrutas;
        } else if (categoria.equals("Verduras")) {
            return jpVerduras;
        } else if (categoria.equals("Embutidos")) {
            return jpEmbutidos;
        } else {
            return new JPanel(); // Manejar este caso según tus necesidades
        }
    }

    private JPanel crearPanelProducto(JSONObject productoJSON) {
        JPanel productoPanel = new JPanel();
        productoPanel.setLayout(new BoxLayout(productoPanel, BoxLayout.Y_AXIS));

        // Obtener los datos del producto desde el JSON
        int idProducto = Integer.parseInt(productoJSON.get("id").toString());
        String nombre = productoJSON.get("nombre").toString();
        double precio = Double.parseDouble(productoJSON.get("precio").toString());
        String presentacion = productoJSON.get("presentacion").toString();
        String rutaImagen = productoJSON.get("RutaImagen").toString();

        // Cargar la imagen y agregarla al panel
        URL imageUrl = getClass().getClassLoader().getResource(rutaImagen);
        if (imageUrl != null) {
            ImageIcon imagen = new ImageIcon(imageUrl);
            JLabel imagenLabel = new JLabel(imagen);
            productoPanel.add(imagenLabel);
            System.out.println("URL de la imagen: " + imageUrl);
        } else {
            System.out.println("No se pudo cargar la imagen: " + rutaImagen);
        }

        // Agregar etiquetas para los detalles del producto
        productoPanel.add(new JLabel("ID: " + idProducto));
        productoPanel.add(new JLabel("Nombre: " + nombre));
        productoPanel.add(new JLabel("Precio: $" + precio));
        productoPanel.add(new JLabel("Presentación: " + presentacion));

        // Agregar botón de comprar
        JButton comprarButton = new JButton("Comprar");
        productoPanel.add(comprarButton);
        return productoPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAñadirCliente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPrimerA = new javax.swing.JTextField();
        txtSegundoA = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnVaciar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jpnProductos = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblPollo = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblCerdo = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblRes = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblMariscos = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblFrutas = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblVerduras = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblEmbutidos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jpPollo = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jpRes = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jpCerdo = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jpMariscos = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jpFrutas = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jpVerduras = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jpEmbutidos = new javax.swing.JPanel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Cedula", "Nombre", "Primer Apellido", "Segundo Apellido", "Telefono", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblClientesPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        btnAñadirCliente.setText("Añadir");
        btnAñadirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirClienteActionPerformed(evt);
            }
        });

        jLabel1.setText("Cedula");

        jLabel2.setText("Nombre");

        jLabel3.setText("Primer Apellido");

        jLabel4.setText("Segundo Apellido");

        jLabel5.setText("Correo");

        txtPrimerA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrimerAActionPerformed(evt);
            }
        });

        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });

        jLabel6.setText("Telefono");

        btnVaciar.setText("VaciarTabla");
        btnVaciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciarActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Eliminar Cliente"));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel7.setText("ID");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Actualizar Cliente"));

        jLabel8.setText("ID");

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAñadirCliente))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnVaciar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(138, 138, 138))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPrimerA, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSegundoA, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtPrimerA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtSegundoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(btnAñadirCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVaciar)
                        .addGap(24, 24, 24))))
        );

        jTabbedPane1.addTab("Clientes", jPanel3);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Carnes", jPanel2);

        tblPollo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane7.setViewportView(tblPollo);

        jTabbedPane2.addTab("Pollo", jScrollPane7);

        tblCerdo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane8.setViewportView(tblCerdo);

        jTabbedPane2.addTab("Cerdo", jScrollPane8);

        tblRes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane9.setViewportView(tblRes);

        jTabbedPane2.addTab("Res", jScrollPane9);

        tblMariscos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane10.setViewportView(tblMariscos);

        jTabbedPane2.addTab("Mariscos", jScrollPane10);

        tblFrutas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane11.setViewportView(tblFrutas);

        jTabbedPane2.addTab("Frutas", jScrollPane11);

        tblVerduras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane12.setViewportView(tblVerduras);

        jTabbedPane2.addTab("Verduras", jScrollPane12);

        tblEmbutidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Presentación", "id_proveedor", " id_categoria", "id_marca"
            }
        ));
        jScrollPane6.setViewportView(tblEmbutidos);

        jTabbedPane2.addTab("Embutidos", jScrollPane6);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 989, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );

        jpnProductos.setViewportView(jPanel6);

        jTabbedPane1.addTab("Producto", jpnProductos);

        javax.swing.GroupLayout jpPolloLayout = new javax.swing.GroupLayout(jpPollo);
        jpPollo.setLayout(jpPolloLayout);
        jpPolloLayout.setHorizontalGroup(
            jpPolloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpPolloLayout.setVerticalGroup(
            jpPolloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane5.setViewportView(jpPollo);

        jTabbedPane3.addTab("tab1", jScrollPane5);

        javax.swing.GroupLayout jpResLayout = new javax.swing.GroupLayout(jpRes);
        jpRes.setLayout(jpResLayout);
        jpResLayout.setHorizontalGroup(
            jpResLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpResLayout.setVerticalGroup(
            jpResLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane13.setViewportView(jpRes);

        jTabbedPane3.addTab("tab2", jScrollPane13);

        javax.swing.GroupLayout jpCerdoLayout = new javax.swing.GroupLayout(jpCerdo);
        jpCerdo.setLayout(jpCerdoLayout);
        jpCerdoLayout.setHorizontalGroup(
            jpCerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpCerdoLayout.setVerticalGroup(
            jpCerdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane14.setViewportView(jpCerdo);

        jTabbedPane3.addTab("tab3", jScrollPane14);

        javax.swing.GroupLayout jpMariscosLayout = new javax.swing.GroupLayout(jpMariscos);
        jpMariscos.setLayout(jpMariscosLayout);
        jpMariscosLayout.setHorizontalGroup(
            jpMariscosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpMariscosLayout.setVerticalGroup(
            jpMariscosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane15.setViewportView(jpMariscos);

        jTabbedPane3.addTab("tab4", jScrollPane15);

        javax.swing.GroupLayout jpFrutasLayout = new javax.swing.GroupLayout(jpFrutas);
        jpFrutas.setLayout(jpFrutasLayout);
        jpFrutasLayout.setHorizontalGroup(
            jpFrutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpFrutasLayout.setVerticalGroup(
            jpFrutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane16.setViewportView(jpFrutas);

        jTabbedPane3.addTab("tab5", jScrollPane16);

        javax.swing.GroupLayout jpVerdurasLayout = new javax.swing.GroupLayout(jpVerduras);
        jpVerduras.setLayout(jpVerdurasLayout);
        jpVerdurasLayout.setHorizontalGroup(
            jpVerdurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpVerdurasLayout.setVerticalGroup(
            jpVerdurasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane17.setViewportView(jpVerduras);

        jTabbedPane3.addTab("tab6", jScrollPane17);

        javax.swing.GroupLayout jpEmbutidosLayout = new javax.swing.GroupLayout(jpEmbutidos);
        jpEmbutidos.setLayout(jpEmbutidosLayout);
        jpEmbutidosLayout.setHorizontalGroup(
            jpEmbutidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        jpEmbutidosLayout.setVerticalGroup(
            jpEmbutidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        jScrollPane18.setViewportView(jpEmbutidos);

        jTabbedPane3.addTab("tab7", jScrollPane18);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow != -1) {
            String cedula = tblClientes.getValueAt(selectedRow, 1).toString();
            String nombre = tblClientes.getValueAt(selectedRow, 2).toString();
            String primerA = tblClientes.getValueAt(selectedRow, 3).toString();
            String segundoA = tblClientes.getValueAt(selectedRow, 4).toString();
            String telefono = tblClientes.getValueAt(selectedRow, 5).toString();
            String correo = tblClientes.getValueAt(selectedRow, 6).toString();
            if (Validar.validarTelefono(telefono) && Validar.validarCedula(cedula) && Validar.validarNombre(nombre) && Validar.validarPrimerA(primerA) && Validar.validarSegundoA(segundoA)) {
                // Obtén el id del cliente seleccionado
                int idClienteAActualizar = Integer.parseInt(tblClientes.getValueAt(selectedRow, 0).toString());
                // Actualiza el cliente con la información de la tabla
                cliente.actualizar_cliente(
                        idClienteAActualizar,
                        cedula,
                        nombre,
                        primerA,
                        segundoA,
                        telefono,
                        correo
                );
                // Actualizar la tabla después de la actualización
                actualizarTablaCliente();
                // Limpiar campos y mostrar mensaje de éxito
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
            } else {
                // Mostrar un mensaje de error para los datos no válidos
                JOptionPane.showMessageDialog(this, "Los datos ingresados no son válidos.");
            }
        } else {
            // Mostrar un mensaje de error para la selección
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow != -1) {
            int idClienteAEliminar = Integer.parseInt(tblClientes.getValueAt(selectedRow, 0).toString());
            cliente.eliminar_cliente(idClienteAEliminar);

            // Actualizar la tabla después de eliminar el cliente
            actualizarTablaCliente();
            limpiarCampos();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarActionPerformed
        cliente.vaciarClientes();
        actualizarTablaCliente();
    }//GEN-LAST:event_btnVaciarActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtPrimerAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrimerAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrimerAActionPerformed

    private void btnAñadirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirClienteActionPerformed
        // Obtener los datos ingresados en los campos de texto
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();
        String primerA = txtPrimerA.getText();
        String segundoA = txtSegundoA.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        // Verificar que los campos no estén vacíos
        if (camposVacios(cedula, nombre, primerA, segundoA, telefono, correo)) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idAleatorio = cliente.generarIdAleatorio();
        Cliente nuevoCliente = new Cliente(this);
        nuevoCliente.setId(idAleatorio);
        nuevoCliente.setCedula(cedula);
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setPrimer_apellido(primerA);
        nuevoCliente.setSegundo_apellido(segundoA);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setCorreo(correo);

        // Guardar el cliente y actualizar la tabla solo si la validación fue exitosa
        if (Validar.validarTelefono(telefono) && Validar.validarCedula(cedula) && Validar.validarNombre(nombre) && Validar.validarPrimerA(primerA) && Validar.validarSegundoA(segundoA)) {
            nuevoCliente.guardarCliente();
            actualizarTablaCliente();
            limpiarCampos();
            activarPaneles();
        } else {
            JOptionPane.showMessageDialog(this, "Algunos datos ingresados no son válidos: ");

        }
    }//GEN-LAST:event_btnAñadirClienteActionPerformed

    private void tblClientesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblClientesPropertyChange
        int row = tblClientes.getSelectedRow();
        int column = tblClientes.getSelectedColumn();
        if (column != -1 && row != -1) {
            String newValue = tblClientes.getValueAt(row, column).toString();
            String columnName = tblClientes.getColumnName(column);
            tblClientes.getModel().setValueAt(newValue, row, column);
        }
    }//GEN-LAST:event_tblClientesPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAñadirCliente;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnVaciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jpCerdo;
    private javax.swing.JPanel jpEmbutidos;
    private javax.swing.JPanel jpFrutas;
    private javax.swing.JPanel jpMariscos;
    private javax.swing.JPanel jpPollo;
    private javax.swing.JPanel jpRes;
    private javax.swing.JPanel jpVerduras;
    private javax.swing.JScrollPane jpnProductos;
    private javax.swing.JTable tblCerdo;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblEmbutidos;
    private javax.swing.JTable tblFrutas;
    private javax.swing.JTable tblMariscos;
    private javax.swing.JTable tblPollo;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTable tblRes;
    private javax.swing.JTable tblVerduras;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrimerA;
    private javax.swing.JTextField txtSegundoA;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
