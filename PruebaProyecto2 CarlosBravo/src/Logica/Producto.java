/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String presentacion;
    private int id_proveedor;
    private int id_categoria;
    private int id_marca;
    private String rutaImagen;

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
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the presentacion
     */
    public String getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the id_proveedor
     */
    public int getId_proveedor() {
        return id_proveedor;
    }

    /**
     * @param id_proveedor the id_proveedor to set
     */
    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    /**
     * @return the id_categoria
     */
    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * @param id_categoria the id_categoria to set
     */
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    /**
     * @return the id_marca
     */
    public int getId_marca() {
        return id_marca;
    }

    /**
     * @param id_marca the id_marca to set
     */
    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    // Método para guardar un producto en el archivo JSON correspondiente a la categoría
    public void guardarProducto(String categoria) {
     try {
            // Verificar si el archivo JSON existe, si no, crear uno nuevo
            File archivoJSON = new File(categoria + ".json");
            
            if (!archivoJSON.exists()) {
                archivoJSON.createNewFile();
                JSONArray productosArrayVacio = new JSONArray();
                FileWriter fileWriter = new FileWriter(archivoJSON);
                fileWriter.write(productosArrayVacio.toJSONString());
                fileWriter.flush();
                fileWriter.close();   
            }

            // Leer el archivo JSON de la categoría
            JSONParser parser = new JSONParser();
            JSONArray productosArray = (JSONArray) parser.parse(new FileReader(archivoJSON));

            // Verificar si el producto con el mismo ID ya existe en el archivo
            for (Object obj : productosArray) {
                JSONObject productoJSON = (JSONObject) obj;
                int productoId = Integer.parseInt(productoJSON.get("id").toString());
                if (productoId == id) {
                    System.out.println("El producto con ID " + id + " ya ha sido guardado previamente.");
                    return; // Salir del método sin agregar el producto duplicado
                }
            }

            // Crear el objeto JSON del nuevo producto
            JSONObject productoJSON = new JSONObject();
            productoJSON.put("id", id);
            productoJSON.put("nombre", nombre);
            productoJSON.put("precio", precio);
            productoJSON.put("presentacion", presentacion);
            productoJSON.put("id_proveedor", id_proveedor);
            productoJSON.put("id_categoria", id_categoria);
            productoJSON.put("id_marca", id_marca);
            productoJSON.put("RutaImagen", rutaImagen);

            // Agregar el nuevo producto al array de productos
            productosArray.add(productoJSON);

            // Escribir el array actualizado de productos en el archivo JSON correspondiente a la categoría
            FileWriter fileWriter = new FileWriter(categoria + ".json"); // Aquí se utiliza el nombre de la categoría
            fileWriter.write(productosArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Producto guardado exitosamente.");
            
            //imprimir la ubicacion del archivo json
            System.out.println("Ruta del archivo JSON: " + archivoJSON.getAbsolutePath());
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar todos los productos de una categoría
    public void mostrarProductos(String categoria) throws ParseException {
        try {
            // Leer el archivo JSON de la categoría
            JSONParser parser = new JSONParser();
            JSONArray productosArray = (JSONArray) parser.parse(new FileReader(categoria + ".json"));

        // Mostrar los productos
        System.out.println("Productos en la categoria \"" + categoria + "\":");
        for (Object obj : productosArray) {
            JSONObject productoJSON = (JSONObject) obj;
            System.out.println("ID: " + productoJSON.get("id"));
            System.out.println("Nombre: " + productoJSON.get("nombre"));
            System.out.println("Precio: " + productoJSON.get("precio"));
            System.out.println("Presentacion: " + productoJSON.get("presentacion"));
            System.out.println("ID Proveedor: " + productoJSON.get("id_proveedor"));
            System.out.println("ID Categoria: " + productoJSON.get("id_categoria"));
            System.out.println("ID Marca: " + productoJSON.get("id_marca"));
            System.out.println("---------------------------");
        }
        
        // Vaciar el archivo JSON correspondiente a la categoría para eliminar los productos previos
        //FileWriter fileWriter = new FileWriter(categoria + ".json");
        //fileWriter.write("[]");
        //fileWriter.flush();
        //fileWriter.close();
        
        // Imprimir el contenido completo del archivo JSON
        System.out.println(productosArray.toJSONString());
        
        } catch (IOException | ParseException e) {
        }
    }
    
    public void actualizarTabla(DefaultTableModel modeloTabla, String categoria) {
        try {
            // Leer el archivo JSON de la categoría
            JSONParser parser = new JSONParser();
            JSONArray productosArray = (JSONArray) parser.parse(new FileReader(categoria + ".json"));

            // Limpiar la tabla antes de agregar los nuevos datos
            modeloTabla.setRowCount(0);

            // Agregar los productos al modelo de tabla
            for (Object obj : productosArray) {
                JSONObject productoJSON = (JSONObject) obj;
                int Id = Integer.parseInt(productoJSON.get("id").toString());
                String Nombre = productoJSON.get("nombre").toString();
                double Precio = Double.parseDouble(productoJSON.get("precio").toString());
                String Presentacion = productoJSON.get("presentacion").toString();
                int Id_proveedor = Integer.parseInt(productoJSON.get("id_proveedor").toString());
                int Id_categoria = Integer.parseInt(productoJSON.get("id_categoria").toString());
                int Id_marca = Integer.parseInt(productoJSON.get("id_marca").toString());

                modeloTabla.addRow(new Object[]{Id, Nombre, Precio, Presentacion, Id_proveedor, Id_categoria, Id_marca});
            }
        } catch (IOException | ParseException e) {
            // Manejo de excepciones (opcional)
        }
    }

    /**
     * @return the rutaImagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * @param rutaImagen the rutaImagen to set
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
