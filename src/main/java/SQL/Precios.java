/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import javax.swing.*;

/**
 *
 * @author gdemg
 */
public class Precios {
    private float precioBase = 5000f;
    private float precioxRecamara = 500f;
    private float precioxBaño = 250f;
    private float precioxCocina = 359f;
    private float precioxComedor = 150f;
    private float precioxSala = 150f;
    private float precioxInternet = 100f;
    
    public float calcularPrecioporParametro(JComboBox tamaño, JTextField recamaras, JTextField baño, JTextField cocina, JTextField comedor, JTextField sala, JTextField internet){
        float incremento = 0;
        
        switch (tamaño.getSelectedItem().toString()) {
            case "Pequeño":
                incremento = 100;
                break;
            case "Mediano":
                incremento = 150;
                break;
            case "Grande":
                incremento = 200;
                break;
        }
        
        incremento =+ precioxRecamara * Integer.parseInt(recamaras.getText());
        incremento =+ precioxRecamara * Integer.parseInt(baño.getText());
        incremento =+ precioxRecamara * Integer.parseInt(cocina.getText());
        incremento =+ precioxRecamara * Integer.parseInt(comedor.getText());
        incremento =+ precioxRecamara * Integer.parseInt(sala.getText());
        
        if(internet.getText().toString().equals("Si")){
            incremento =+ precioxInternet;
        }
        
        return precioBase + incremento;
    }
    
    public float calcularPreciodeZona(JComboBox zona) {
        float incremento = 0;
        
        switch (zona.getSelectedItem().toString()) {
            case "Norte":
            case "Sur":
                incremento = 1500;
                break;
            case "Oriente":
            case "Poniente":
                incremento = 2500;
                break;
            case "Centro":
                incremento = 3000;
                break;
            default:
                System.out.println("Zona no reconocida, sin incremento.");
                break;
        }

        return precioBase + incremento;
    }
}
