/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Bravo
 */
public class Validar {
    public static boolean validarTelefono(String telefono) {
        return telefono.matches("\\d{8}");  // Verifica si son exactamente 8 dígitos
    }

    public static boolean validarCedula(String cedula) {
        return cedula.matches("\\d{1,12}");  // Verifica si hay entre 1 y 12 dígitos
    }

    public static boolean validarNombre(String nombre) {
        return nombre.matches("[a-zA-Z]+");  // Verifica que solo haya letras
    }
    public static boolean validarPrimerA(String primerA) {
        return primerA.matches("[a-zA-Z]+");  // Verifica que solo haya letras
    }
    public static boolean validarSegundoA(String segundoA) {
        return segundoA.matches("[a-zA-Z]+");  // Verifica que solo haya letras
    }
}
