/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
        
    public void mostrarTabla(JTable tabla, JPanel panel){
        SQLconection sqlserver = new SQLconection();
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        
        modelo.addColumn("ID");
        modelo.addColumn("Precio");
        modelo.addColumn("Tamaño");
        modelo.addColumn("Zona");
        modelo.addColumn("Fecha de Registro");
        modelo.addColumn("Recamaras");
        modelo.addColumn("Baño");
        modelo.addColumn("Cocina");
        modelo.addColumn("Comedor");
        modelo.addColumn("Sala");
        modelo.addColumn("Internet");
        modelo.addColumn("Disponibilidad");
        
        tabla.setModel(modelo);
        
        sql = "SELECT * FROM Airbnb;";
        
        String [] valores = new String [12];
        
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
                valores[11] = result.getString(12);
                
                
                
                
                modelo.addRow(valores);
            }
            
            tabla.setModel(modelo);
            tabla.setPreferredScrollableViewportSize(new Dimension(1080, 450));
                        
            JScrollPane scrollPane = new JScrollPane(tabla);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            panel.removeAll();
            panel.setLayout(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
                      
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no mostrados. Error: " + e.toString());
        }
    }
    
    public void insertar(float precio, JComboBox tamaño, JComboBox zona, JTextField fecha, JTextField recamaras, JTextField baño, JTextField cocina, JTextField comedor, JTextField sala, JTextField internet){
        SQLconection sql = new SQLconection();
        
        String consulta = "insert into Airbnb (PRECIO, TAMANO, ZONA, FECHAINGRESO, RECAMARAS, BAÑO, COCINA, COMEDOR, SALA, INTERNET) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
           
            CallableStatement ct = sql.conexion().prepareCall(consulta);
            
            ct.setFloat(1,precio);
            ct.setString(2,tamaño.getSelectedItem().toString());
            ct.setString(3, zona.getSelectedItem().toString());
            ct.setString(4, fecha.getText());
            ct.setInt(5, Integer.parseInt(recamaras.getText()));
            ct.setInt(6, Integer.parseInt(baño.getText()));
            ct.setInt(7, Integer.parseInt(cocina.getText()));
            ct.setInt(8, Integer.parseInt(comedor.getText()));
            ct.setInt(9, Integer.parseInt(sala.getText()));
            ct.setString(10, internet.getText());
            
            ct.execute();
            
            JOptionPane.showMessageDialog(null, "Datos guardados.");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no guardados. Error: " + e.toString());
        }
    }
        
        
    public void seleccionar(JTable tabla, JTextField id, JTextField precio, JComboBox tamaño, JComboBox zona, JTextField fecha, JTextField recamaras, JTextField baño, JTextField cocina, JTextField comedor, JTextField sala, JTextField internet){
        
        try{
           
            int fila = tabla.getSelectedRow();
            
            if(fila >= 0){
                id.setText(tabla.getValueAt(fila, 0).toString());
                precio.setText(tabla.getValueAt(fila, 1).toString());
                tamaño.setSelectedItem(tabla.getValueAt(fila, 2).toString());
                zona.setSelectedItem(tabla.getValueAt(fila, 3).toString());
                fecha.setText(tabla.getValueAt(fila, 4).toString());
                recamaras.setText(tabla.getValueAt(fila, 5).toString());
                baño.setText(tabla.getValueAt(fila, 6).toString());
                cocina.setText(tabla.getValueAt(fila, 7).toString());
                comedor.setText(tabla.getValueAt(fila, 8).toString());
                sala.setText(tabla.getValueAt(fila, 9).toString());
                internet.setText(tabla.getValueAt(fila, 10).toString());
            }
            
        } 
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no seleccionados. Error: " + e.toString());
        }
    
    }
    
    public void eliminarDato(int id){
        SQLconection sql = new SQLconection();
        
        String consulta = "delete from Airbnb where ID = ?";
        String consulta2 = "delete from DISPONIBLE where ID = ?";
        try{
           
            CallableStatement ct = sql.conexion().prepareCall(consulta2);
            CallableStatement cbt = sql.conexion().prepareCall(consulta);
            
            ct.setInt(1, id);
            
            ct.execute();
            
            cbt.setInt(1, id);
            
            cbt.execute();
            
            JOptionPane.showMessageDialog(null, "Datos eliminados.");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no eliminados. Error: " + e.toString());
        }
    }
    
    public void actualizarDato(int id, String dato){
        SQLconection sql = new SQLconection();
        
        String consulta = "update Airbnb set ESTADO = ? where ID = ?";
        String consulta2 = "INSERT INTO DISPONIBLE (ID, CANTIDAD) VALUES (?, ?)";
        
        
        try{
           
            CallableStatement ct = sql.conexion().prepareCall(consulta);
            
            ct.setString(1, dato);
            ct.setInt(2,id);
            
            ct.execute();
            
            if(dato.equals("En renta")){
                CallableStatement cs = sql.conexion().prepareCall(consulta2);
                
                cs.setInt(1, id);
                cs.setInt(2, 1);
                
                cs.execute();
            }
            
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
    
    public int selec(JTable tabla){
        int x = 0;
        
           
            int fila = tabla.getSelectedRow();
            String dato = "";
            if(fila >= 0){
                
                dato = tabla.getValueAt(fila, 11).toString();
                
            }
            
        if(dato.equals("En renta")){
                return x = 1;
            }
            else{
                return x = 0;
            }

    }
    
    public String selecDato(JTable tabla){
        
            int fila = tabla.getSelectedRow();
            String dato = "";
            if(fila >= 0){
                
                dato = tabla.getValueAt(fila, 0).toString();
                
            }
            
            return dato;
    }
    
    public void tablaEstadisticas(JTable tabla, JPanel panel){
        SQLconection sqlserver = new SQLconection();
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        modelo.addColumn("ID");
        modelo.addColumn("Veces que se rentó");
        
        
        tabla.setModel(modelo);
        
        sql = "select * from CANTIDAD_RENTA";
        
        String [] valores = new String [2];
        
        Statement st;
        
        try{
            st = sqlserver.conexion().createStatement();
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
                valores[0] = result.getString(1);
                valores[1] = result.getString(2);
                
                modelo.addRow(valores);
            }
            
            tabla.setModel(modelo);
            
            tabla.setPreferredScrollableViewportSize(new Dimension(555, 300));
                        
            JScrollPane scrollPane = new JScrollPane(tabla);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            panel.removeAll();
            panel.setLayout(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Datos no mostrados. Error: " + e.toString());
        }
    }
    
    
    // Método para obtener propiedades que llevan más de un año registradas
    public void obtenerPropiedadesParaCambiarPrecio() {
        SQLconection sqlserver = new SQLconection();
        ArrayList<String> alertas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        
        Connection con = sqlserver.conexion(); 
        
        String query = "SELECT ID, PRECIO, FECHAINGRESO, " +
                       "       DATEDIFF(YEAR, FECHAINGRESO, GETDATE()) AS Anios " +
                       "FROM Airbnb " +
                       "WHERE DATEDIFF(YEAR, FECHAINGRESO, GETDATE()) >= 1;";

        ps = con.prepareStatement(query);
        rs = ps.executeQuery();

        
        while (rs.next()) {
            int id = rs.getInt("ID");
            double precio = rs.getDouble("PRECIO");
            String fechaIngreso = rs.getString("FECHAINGRESO");
            int anios = rs.getInt("Anios");

            
            String mensaje = "ALERTA: Propiedad con ID: " + id + 
                             " registrada el " + fechaIngreso + 
                             " lleva más de " + anios + " años. Precio actual: " + precio + ". Porcentaje a aumentar al precio: 7.9%. Precio Estimado: " +(precio + (precio * 7.9/100)) + "\n";
            alertas.add(mensaje);
        }
        
        String alertageneral = "";
        if (!alertas.isEmpty()) {
            for (String alerta : alertas) {
            alertageneral = alertageneral + alerta;
            }
            
            JOptionPane.showInternalMessageDialog(null,alertageneral);
        } 
        else {
            System.out.println("No hay propiedades que necesiten cambio de precio.");
        }
        }catch (Exception e) {
    
        JOptionPane.showMessageDialog(null, "No se pudo mostrar alerta. Error: " + e.toString());
        } 
    }
    
}
   






