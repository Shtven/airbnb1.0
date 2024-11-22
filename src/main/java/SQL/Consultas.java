/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gdemg
 */
public class Consultas {
        
    public void mostrarTabla(JTable tabla){
        SQLconection sqlserver = new SQLconection();
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        modelo.addColumn("ID");
        modelo.addColumn("Precio");
        modelo.addColumn("Tamaño(m2)");
        modelo.addColumn("Zona");
        modelo.addColumn("Recamaras");
        modelo.addColumn("Baño");
        modelo.addColumn("Cocina");
        modelo.addColumn("Comedor");
        modelo.addColumn("Sala");
        modelo.addColumn("Internet");
        modelo.addColumn("Disponibilidad");
        
        tabla.setModel(modelo);
        
        sql = "SELECT * FROM Airbnb;";
        
        String [] valores = new String [11];
        
        Statement st;
        
        try{
            st = sqlserver.conexion().createStatement();
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
                valores[0] = result.getString(1);
                valores[1] = result.getString(2);
                valores[2] = result.getString(3);
                valores[3] = result.getString(4);
                valores[4] = result.getString(5);
                valores[5] = result.getString(6);
                valores[6] = result.getString(7);
                valores[7] = result.getString(8);
                valores[8] = result.getString(9);
                valores[9] = result.getString(10);
                valores[10] = result.getString(11);
                
                
                modelo.addRow(valores);
            }
            
            tabla.setModel(modelo);
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no mostrados. Error: " + e.toString());
        }
    }
    
    public void insertar(float precio, JTextField tamaño, JComboBox zona, JTextField recamaras, JTextField baño, JTextField cocina, JTextField comedor, JTextField sala, JTextField internet){
        SQLconection sql = new SQLconection();
        
        String consulta = "insert into Airbnb (PRECIO, TAMANO, ZONA, RECAMARAS, BAÑO, COCINA, COMEDOR, SALA, INTERNET) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
           
            CallableStatement ct = sql.conexion().prepareCall(consulta);
            
            ct.setFloat(1,precio);
            ct.setFloat(2, Float.parseFloat(tamaño.getText()));
            ct.setString(3, zona.getSelectedItem().toString());
            ct.setInt(4, Integer.parseInt(recamaras.getText()));
            ct.setInt(5, Integer.parseInt(baño.getText()));
            ct.setInt(6, Integer.parseInt(cocina.getText()));
            ct.setInt(7, Integer.parseInt(comedor.getText()));
            ct.setInt(8, Integer.parseInt(sala.getText()));
            ct.setString(9, internet.getText());
            
            ct.execute();
            
            JOptionPane.showMessageDialog(null, "Datos guardados.");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no guardados. Error: " + e.toString());
        }
    }
        
        
    public void seleccionar(JTable tabla, JTextField id, JTextField precio, JTextField tamaño, JComboBox zona, JTextField recamaras, JTextField baño, JTextField cocina, JTextField comedor, JTextField sala, JTextField internet){
        
        try{
           
            int fila = tabla.getSelectedRow();
            
            if(fila >= 0){
                id.setText(tabla.getValueAt(fila, 0).toString());
                precio.setText(tabla.getValueAt(fila, 1).toString());
                tamaño.setText(tabla.getValueAt(fila, 2).toString());
                zona.setSelectedItem(tabla.getValueAt(fila, 3).toString());
                recamaras.setText(tabla.getValueAt(fila, 4).toString());
                baño.setText(tabla.getValueAt(fila, 5).toString());
                cocina.setText(tabla.getValueAt(fila, 6).toString());
                comedor.setText(tabla.getValueAt(fila, 7).toString());
                sala.setText(tabla.getValueAt(fila, 8).toString());
                internet.setText(tabla.getValueAt(fila, 9).toString());
            }
            
        } 
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no seleccionados. Error: " + e.toString());
        }
    
    }
    
    public void eliminarDato(JTextField id){
        SQLconection sql = new SQLconection();
        
        String consulta = "delete from Airbnb where ID = ?";
        
        try{
           
            CallableStatement ct = sql.conexion().prepareCall(consulta);
            
            ct.setInt(1, Integer.parseInt(id.getText()));
            
            ct.execute();
            
            JOptionPane.showMessageDialog(null, "Datos eliminados.");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no eliminados. Error: " + e.toString());
        }
    }
    
    public void actualizarDato(JTextField id, String dato){
        SQLconection sql = new SQLconection();
        
        String consulta = "update Airbnb set ESTADO = ? where ID = ?";
        
        try{
           
            CallableStatement ct = sql.conexion().prepareCall(consulta);
            
            ct.setString(1, dato);
            ct.setInt(2,Integer.parseInt(id.getText()));
            
            ct.execute();
            
            JOptionPane.showMessageDialog(null, "Dato actualizado.");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no actualizados. Error: " + e.toString());
        }
    }
    
    public void comboDisponible(JComboBox box){
        String[] categorias = {"Disponible", "En renta"};
        
        box.setModel(new DefaultComboBoxModel<>(categorias));
    }
    
    public void comboZona(JComboBox box){
        String[] categorias = {"Norte", "Sur", "Centro", "Oriente", "Poniente"};
        
        box.setModel(new DefaultComboBoxModel<>(categorias));
    }
    
    public float calcularPrecioAjustado(JComboBox zona) {
        float incremento = 0;
        float precioBase = 5000;
        
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
