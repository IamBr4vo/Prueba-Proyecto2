/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import GUI.Interfaz;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Bravo
 */
public class Cliente {

    private int id;
    private String cedula;
    private String nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String telefono;
    private String correo;
    private Interfaz interfaz;
    private static final HashSet<Integer> idsGenerados = new HashSet<>();

    public Cliente() {
    }

    public Cliente(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the primer_apellido
     */
    public String getPrimer_apellido() {
        return primer_apellido;
    }

    /**
     * @param primer_apellido the primer_apellido to set
     */
    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    /**
     * @return the segundo_apellido
     */
    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    /**
     * @param segundo_apellido the segundo_apellido to set
     */
    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Método para guardar un cliente en el archivo JSON de clientes
    public void guardarCliente() {
        try {
            // Verificar si el archivo JSON existe, si no, crear uno nuevo
            File archivoJSON = new File("Cliente.json");

            if (!archivoJSON.exists()) {
                archivoJSON.createNewFile();
                JSONArray clientesArrayVacio = new JSONArray();
                FileWriter fileWriter = new FileWriter(archivoJSON);
                fileWriter.write(clientesArrayVacio.toJSONString());
                fileWriter.flush();
                fileWriter.close();
            }

            // Leer el archivo JSON de clientes
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader(archivoJSON));

            // Verificar si el cliente con la misma cédula ya existe en el archivo
            for (Object obj : clientesArray) {
                JSONObject clienteJSON = (JSONObject) obj;
                String cedulaCliente = clienteJSON.get("cedula").toString();
                if (cedulaCliente.equals(cedula)) {
                    System.out.println("Ya existe un cliente con la cédula " + cedula);
                    return; // Salir del método sin agregar el cliente duplicado
                }
            }

            // Crear el objeto JSON del nuevo cliente
            JSONObject clienteJSON = new JSONObject();
            clienteJSON.put("id", id);
            clienteJSON.put("cedula", cedula);
            clienteJSON.put("nombre", nombre);
            clienteJSON.put("primer_apellido", primer_apellido);
            clienteJSON.put("segundo_apellido", segundo_apellido);
            clienteJSON.put("telefono", telefono);
            clienteJSON.put("correo", correo);

            // Agregar el nuevo cliente al array de clientes
            clientesArray.add(clienteJSON);

            // Escribir el array actualizado de clientes en el archivo JSON de clientes
            FileWriter fileWriter = new FileWriter("Cliente.json");
            fileWriter.write(clientesArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Cliente guardado exitosamente.");

            //imprimir la ubicación del archivo json
            //System.out.println("Ruta del archivo JSON: " + archivoJSON.getAbsolutePath());
            interfaz.actualizarTablaCliente();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void mostrarCliente() throws ParseException {
        try {
            // Leer el archivo JSON de la categoría
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader("Cliente.json"));

            // Mostrar los productos
            System.out.println("Clientes ");
            for (Object obj : clientesArray) {
                JSONObject clientesJSON = (JSONObject) obj;
                System.out.println("ID: " + clientesJSON.get("id"));
                System.out.println("Cedula: " + clientesJSON.get("cedula"));
                System.out.println("Nombre: " + clientesJSON.get("nombre"));
                System.out.println("Primer apellido : " + clientesJSON.get("primer_apellido"));
                System.out.println("Segundo apellido: " + clientesJSON.get("segundo_apellido"));
                System.out.println("Telefono : " + clientesJSON.get("telefono"));
                System.out.println("Correo : " + clientesJSON.get("correo"));
                System.out.println("---------------------------");
            }

            // Imprimir el contenido completo del archivo JSON
            //System.out.println(clientesArray.toJSONString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTabla(DefaultTableModel modeloTabla) {
        try {
            // Leer el archivo JSON de clientes
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader("Cliente.json"));

            // Limpiar la tabla antes de agregar los nuevos datos
            modeloTabla.setRowCount(0);

            // Agregar los clientes al modelo de tabla
            for (Object obj : clientesArray) {
                JSONObject clienteJSON = (JSONObject) obj;
                int Id = Integer.parseInt(clienteJSON.get("id").toString());
                String Cedula = clienteJSON.get("cedula") != null ? clienteJSON.get("cedula").toString() : "";
                String Nombre = clienteJSON.get("nombre") != null ? clienteJSON.get("nombre").toString() : "";
                String Primer_apellido = clienteJSON.get("primer_apellido") != null ? clienteJSON.get("primer_apellido").toString() : "";
                String Segundo_apellido = clienteJSON.get("segundo_apellido") != null ? clienteJSON.get("segundo_apellido").toString() : "";
                String Telefono = clienteJSON.get("telefono") != null ? clienteJSON.get("telefono").toString() : "";
                String Correo = clienteJSON.get("correo") != null ? clienteJSON.get("correo").toString() : "";
                modeloTabla.addRow(new Object[]{Id, Cedula, Nombre, Primer_apellido, Segundo_apellido, Telefono, Correo});
            }
        } catch (IOException | ParseException e) {
            // Manejo de excepciones (opcional)
            e.printStackTrace();
        }
    }
    // Método para actualizar la información de un cliente existente

    public void actualizar_cliente(int id, String cedula, String nombre, String primer_apellido, String segundo_apellido, String telefono, String correo) {
        try {
            // Leer el archivo JSON de clientes
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader("Cliente.json"));
            // Buscar el cliente con el ID especificado
            boolean clienteEncontrado = false;
            for (Object obj : clientesArray) {
                JSONObject clienteJSON = (JSONObject) obj;
                int clienteId = Integer.parseInt(clienteJSON.get("id").toString());

                if (clienteId == id) {
                    // Actualizar la información del cliente si los campos de texto no están en blanco
                    if (!cedula.isEmpty()) {
                        clienteJSON.put("cedula", cedula);
                    }
                    if (!nombre.isEmpty()) {
                        clienteJSON.put("nombre", nombre);
                    }
                    if (!primer_apellido.isEmpty()) {
                        clienteJSON.put("primer_apellido", primer_apellido);
                    }
                    if (!segundo_apellido.isEmpty()) {
                        clienteJSON.put("segundo_apellido", segundo_apellido);
                    }
                    if (!telefono.isEmpty()) {
                        clienteJSON.put("telefono", telefono);
                    }
                    if (!correo.isEmpty()) {
                        clienteJSON.put("correo", correo);
                    }

                    clienteEncontrado = true;
                    break; // Salir del bucle una vez que se actualizó el cliente
                }
            }

            if (clienteEncontrado) {
                // Escribir el array actualizado de clientes en el archivo JSON
                FileWriter fileWriter = new FileWriter("Cliente.json");
                fileWriter.write(clientesArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();
                System.out.println("Cliente actualizado exitosamente.");
            } else {
                System.out.println("No se encontró un cliente con el ID " + id);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

// Método para eliminar un cliente del JSON mediante su ID
    public void eliminar_cliente(int id) {
        try {
            // Leer el archivo JSON de clientes
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader("Cliente.json"));

            List<Integer> indicesAEliminar = new ArrayList<>();
            // Buscar el cliente con el ID especificado
            boolean clienteEncontrado = false;
            for (int i = 0; i < clientesArray.size(); i++) {
                JSONObject clienteJSON = (JSONObject) clientesArray.get(i);
                int clienteId = Integer.parseInt(clienteJSON.get("id").toString());

                if (clienteId == id) {
                    indicesAEliminar.add(i);
                    clienteEncontrado = true;
                }
            }
            if (clienteEncontrado) {
                // Eliminar los clientes con los IDs indicados del JSON
                for (int i : indicesAEliminar) {
                    clientesArray.remove(i);
                }
                // Escribir el array actualizado de clientes en el archivo JSON
                FileWriter fileWriter = new FileWriter("Cliente.json");
                fileWriter.write(clientesArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();

                System.out.println("Cliente eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un cliente con el ID " + id);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar que no exista otro cliente con la misma cédula
    public boolean validar_cliente(String cedula) {
        try {
            // Leer el archivo JSON de clientes
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader("Cliente.json"));

            // Buscar el cliente con la cédula especificada
            for (Object obj : clientesArray) {
                JSONObject clienteJSON = (JSONObject) obj;
                String cedulaCliente = clienteJSON.get("cedula").toString();

                if (cedulaCliente.equals(cedula)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un cliente con la cédula " + cedula, "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Ya existe un cliente con la misma cédula
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return true; // No se encontró otro cliente con la misma cédula
    }
    // Método para generar un ID aleatorio y verificar su disponibilidad

    public int generarIdAleatorio() {
        // Generar un ID aleatorio entre 1 y 1000
        Random random = new Random();
        int idAleatorio;
        do {
            idAleatorio = random.nextInt(1000) + 1;
        } while (!validar_id(idAleatorio) || idsGenerados.contains(idAleatorio));

        // Agregar el ID generado al HashSet
        idsGenerados.add(idAleatorio);

        return idAleatorio;
    }

    // Método para verificar si un ID ya está en uso por otro cliente
    public boolean validar_id(int id) {
        try {
            // Leer el archivo JSON de clientes
            JSONParser parser = new JSONParser();
            JSONArray clientesArray = (JSONArray) parser.parse(new FileReader("Cliente.json"));

            // Buscar el cliente con el ID especificado
            for (Object obj : clientesArray) {
                JSONObject clienteJSON = (JSONObject) obj;
                int clienteId = Integer.parseInt(clienteJSON.get("id").toString());

                if (clienteId == id) {
                    return false; // El ID ya está en uso
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return true; // El ID no está en uso, es válido
    }

    //solo en caso de emergencia jaja
    public void vaciarClientes() {
        try {
            // Crear un nuevo archivo JSON vacío para reemplazar el archivo existente
            File archivoJSON = new File("Cliente.json");

            // Escribir un array JSON vacío en el nuevo archivo
            JSONArray clientesArrayVacio = new JSONArray();
            FileWriter fileWriter = new FileWriter(archivoJSON);
            archivoJSON.createNewFile();

            fileWriter.write(clientesArrayVacio.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            System.out.println("El archivo JSON de clientes ha sido vaciado.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
