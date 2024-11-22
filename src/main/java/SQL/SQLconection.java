/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author gdemg
 */
public class SQLconection {
    Connection conexion = null;
    
    String usuario = "usersql";
    String password = "hola2";
    String database = "Prueba_airbnb";
    String ip = "localhost";
    String puerto = "1433";
    
    String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + "/" + database;
    
    public Connection conexion(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String cadena = "jdbc:sqlserver://localhost:" + puerto + ";" + "databaseName=" + database + ";" + "encrypt=true;trustServerCertificate=true";
            conexion = DriverManager.getConnection(cadena,usuario, password);
            //JOptionPane.showMessageDialog(null, "Se conectó a la base de datos correctamentente.");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Conexión fallida.\n Error: " + e.toString());
        }
        return conexion;
    }
}
