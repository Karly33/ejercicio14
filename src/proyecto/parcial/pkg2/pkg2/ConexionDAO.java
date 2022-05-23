/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.parcial.pkg2.pkg2;

import java.sql.*;
import java.sql.Connection;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author JM
 */
public class ConexionDAO {
PreparedStatement ps;
Connection conexion = null;  
List<Dto> listaDATOS = new ArrayList<>();

private void conectar(){

 String username = "root";
 String password ="rogeliogonzalez9";
 String url = "jdbc:mysql://localhost:3306/KARLY4S21?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTim";
   

try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        
  conexion = (Connection) DriverManager.getConnection(url, username, password);
    

} catch (ClassNotFoundException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
}  


public boolean insertar(Dto datos){
boolean estado = true;
try{
conectar();
PreparedStatement ps = conexion.prepareStatement("insert into datos(nombre, edad, correo)values(?,?,?)");
ps.setString(1, datos.getNombre());  
ps.setString(2, datos.getEdad());
ps.setString(3, datos.getCorreo());
ps.execute();

}catch(Exception ex){
estado = false;
}finally{
 cerrar();
}
  return estado; 
}


public boolean cargar (){
boolean estado = true;
Dto datos;
try{
conectar();    
PreparedStatement ps = conexion.prepareStatement("select * from datos");
ResultSet resultado = ps.executeQuery();
while(resultado.next()){
datos = new Dto();
datos.setId(resultado.getInt("id"));
datos.setNombre(resultado.getString("nombre"));
datos.setEdad(resultado.getString("edad"));
datos.setCorreo(resultado.getString("correo"));
listaDATOS.add(datos);
}
}catch(Exception ex){
estado = false;

}finally{
 cerrar();
}
return estado;
}


public boolean actualizar(Dto datos){
boolean estado = true;
try{
conectar();    
PreparedStatement ps = conexion.prepareStatement("update datos set nombre = ?, edad = ?, correo = ? where id ?  = ");
ps.setString(1, datos.getNombre());
ps.setString(2, datos.getEdad());
ps.setString(3, datos.getCorreo());
ps.setInt(4, datos.getId());
ps.execute();
}catch(SQLException ex){
estado = false;
}finally{
cerrar();
}
return estado;
}


public boolean eliminar (Dto datos){
boolean estado = true;
try{
conectar();    
PreparedStatement ps = conexion.prepareStatement("delete from datos where id = ?");
ps.setInt(1, datos.getId());
ps.execute();
}catch(SQLException ex){
estado = false;
}finally{
cerrar();
}
return estado;
}


public List<Dto> getDatos(){
return listaDATOS;
}


private void cerrar(){
    try {
        conexion.close();
    } catch (SQLException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
