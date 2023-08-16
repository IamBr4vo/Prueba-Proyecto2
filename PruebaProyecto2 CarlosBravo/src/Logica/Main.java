package Logica;

import GUI.Interfaz;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Bravo
 */
public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        // Ejemplo de uso de la clase Producto
        Producto producto1 = new Producto();
        producto1.setId(3);
        producto1.setNombre("Posta de Cerdo");
        producto1.setPrecio(13.3);
        producto1.setPresentacion("500g");
        producto1.setId_proveedor(82);
        producto1.setId_categoria(15);
        producto1.setId_marca(73);
        producto1.setRutaImagen("/Gatos/1.1.jpg");
        
        Producto producto2 = new Producto();
        producto2.setId(5);
        producto2.setNombre("Camaron");
        producto2.setPrecio(9.3);
        producto2.setPresentacion("430g");
        producto2.setId_proveedor(7);
        producto2.setId_categoria(57);
        producto2.setId_marca(99);
        producto2.setRutaImagen("/Gatos/descarga (1).jpg");
        
        Producto producto3 = new Producto();
        producto3.setId(8);
        producto3.setNombre("Camaron");
        producto3.setPrecio(9.3);
        producto3.setPresentacion("430g");
        producto3.setId_proveedor(7);
        producto3.setId_categoria(57);
        producto3.setId_marca(99);
        producto3.setRutaImagen("/Gatos/descarga (3).jpg");
        
        
        //producto1.guardarProducto("carnes");
        //producto2.guardarProducto("carnes");
        //producto3.guardarProducto("carnes");
        producto1.mostrarProductos("carnes");
        
        Interfaz mostrar = new Interfaz();
        mostrar.setVisible(true);
        mostrar.setResizable(false);
        mostrar.setLocationRelativeTo(null);
        mostrar.actualizarTablaCliente();
        mostrar.mostrarProductosEnPaneles("Frescos");
        
        
        //Cliente cliente = new Cliente();
        //cliente.mostrarCliente();

       
    }  
}
