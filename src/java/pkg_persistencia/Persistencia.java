/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg_persistencia;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Hu
 */
public class Persistencia {
    
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/proyectointegrador";
    private final String USER = "root";
    private final String PASWORD = "";
    private static String HHZC_clave = "clave";
    
    public Connection cadena;

    public Persistencia() {
         this.cadena = null;
    }
    
    
    public Connection conectar() throws SQLException {
        try {
            Class.forName(DRIVER);
            this.cadena = DriverManager.getConnection(URL, USER, PASWORD);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return this.cadena;

    }

    public void desconectar() {
        try {
            this.cadena.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // ------ Credenciales
    public static boolean findUser (String as_user, String as_pass){
        boolean mensaje = false;
        String sql="";
        int r=0;
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM credenciales WHERE usuario ='"+as_user+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                r+=1;
                res.getString(1);
                res.getString(2);
            }
            if(r==1){
                mensaje=true;
            }else{
            mensaje = false;
        }
            cn.close();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
        return mensaje;
    }
    
    public static String crearUsuario(String as_codigo, String as_user, String as_pass)
    {     
        String mensaje="";
        String sql = "";
        boolean existe = false;
        Persistencia cone = new Persistencia();
        try{
            existe = findUser(as_user, as_pass);
            if (existe == false) {
                sql = "Insert INTO credenciales VALUES (?,?,?)";
                Connection con = cone.conectar();
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, as_codigo);
                pst.setString(2, as_user);
                pst.setString(3, as_pass);
                pst.execute();
                mensaje = "Usuario creado con exito";
                pst.close();
            } else{
                mensaje = "Usuario ya existe";
            }
           
        }catch (SQLException e)
        {
            mensaje = "no se pudo crear el usuario";
        }
        return mensaje;
    }
    
    public static String insertarActivo(String as_codigo, String as_nombre, String as_fechaAdq)
    {     
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "Insert INTO activo VALUES (?,?,?)";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.setString(2, as_nombre);
            pst.setString(3, as_fechaAdq);
            pst.execute();
            mensaje = "Insertado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo insertar";
        }
        return mensaje;
    }
    
    public static String listarActivo()
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM activo";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){       
                mensaje += res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+"/";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo listar";
        }
        return mensaje;
    }
    
    
    public static String mostrarActivo(String as_codigo)
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM activo WHERE codigo='"+as_codigo+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
      
     public static String actualizarActivo(String as_codigo, String as_nombre, String as_fechaAdq)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "UPDATE activo SET nombre = ?, fechaAdq = ? WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_nombre);
            pst.setString(2, as_fechaAdq);
            pst.setString(3, as_codigo);
            pst.execute();
            mensaje = "actualizado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo actualizar";
        }
        return mensaje;
    }
    
    public static String eliminarActivo (String as_codigo)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "DELETE FROM activo WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.execute();
            mensaje = "eliminado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo eliminar";
        }
        return mensaje;
    } 
    
    public static String buscarActivo(String as_pBuscar)
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM activo WHERE nombre='"+as_pBuscar+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
    
    
    
    // ---Actividad
    public static String insertarActividad(String as_codigo, String as_nombre) throws Exception
    {     
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "Insert INTO actividad VALUES (?,?)";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            as_nombre = HHZC_Encriptar(as_nombre,HHZC_clave);
            pst.setString(1, as_codigo);
            pst.setString(2, as_nombre);
            pst.execute();
            mensaje = "Insertado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo insertar";
        }
        return mensaje;
    }
    
    public static String listarActividad() throws Exception
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM actividad";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){       
                mensaje += res.getString(1)+";"+HHZC_Desencriptar(res.getString(2),HHZC_clave)+"/";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo listar";
        }
        return mensaje;
    }
    
    
    public static String mostrarActividad(String as_codigo) throws Exception
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM actividad WHERE codigo='"+as_codigo+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+HHZC_Desencriptar(res.getString(2),HHZC_clave)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
      
     public static String actualizarActividad(String as_codigo, String as_nombre) throws Exception
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "UPDATE actividad SET nombre = ? WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            as_nombre = HHZC_Encriptar(as_nombre,HHZC_clave);
            pst.setString(1, as_nombre);
            pst.setString(2, as_codigo);
            pst.execute();
            mensaje = "actualizado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo actualizar";
        }
        return mensaje;
    }
    
    public static String eliminarActividad (String as_codigo)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "DELETE FROM actividad WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.execute();
            mensaje = "eliminado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo eliminar";
        }
        return mensaje;
    } 
    
    public static String buscarActividad(String as_pBuscar) throws Exception
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM actividad WHERE nombre='"+as_pBuscar+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+HHZC_Desencriptar(res.getString(2),HHZC_clave)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
    
    // --Mantenimiento
    public static String insertarMantenimiento(String as_codigo, String as_fecha, String as_responsable)
    {     
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "Insert INTO mantenimiento VALUES (?,?,?)";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.setString(2, as_fecha);
            pst.setString(3, as_responsable);
            pst.execute();
            mensaje = "Insertado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo insetar";
        }
        return mensaje;
    }
    
     
    public static String mostrarMantenimiento(String as_codigo)
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM mantenimiento WHERE codigo='"+as_codigo+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
      
    public static String actualizarMantenimiento(String as_codigo, String as_fecha, String as_responsable)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "UPDATE mantenimiento SET fecha = ?, responsable = ? WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_fecha);
            pst.setString(2, as_responsable);
            pst.setString(3, as_codigo);
            pst.execute();
            mensaje = "actualizado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo actualizar";
        }
        return mensaje;
    }
    
    public static String eliminarMantenimiento (String as_codigo)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "DELETE FROM mantenimiento WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.execute();
            mensaje = "eliminado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo eliminar";
        }
        return mensaje;
    } 
    
    public static String listarMantenimiento()
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM mantenimiento";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){       
                mensaje += res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+"/";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo listar";
        }
        return mensaje;
    }
    
    public static String buscarMantenimiento(String as_pBuscar)
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM detallemantenimiento WHERE codigo='"+as_pBuscar+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+";"+res.getString(4)+";"+res.getString(5)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
    
    // --- Detalle Mantenimiento
    public static String insertarDetalleMantenimiento(String as_codigo,String as_codigoManteni, String as_codigoActivo, String as_codigoActividad, String as_valor){
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "Insert INTO detallemantenimiento VALUES (?,?,?,?,?)";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.setString(2, as_codigoManteni);
            pst.setString(3, as_codigoActivo);
            pst.setString(4, as_codigoActividad);
            pst.setString(5, as_valor);
            pst.execute();
            mensaje = "Insertado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo insetar";
        }
        return mensaje;
    }
    
      
    public static String mostrarDetalleMantenimiento(String as_codigo)
    {
        String mensaje="";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM detallemantenimiento WHERE codigo='"+as_codigo+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){
                mensaje = res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+";"+res.getString(4)+";"+res.getString(5)+";";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se encontro";
        }
        return mensaje;
    }
      
     public static String actualizarDetalleMantenimiento(String as_codigo,String as_codigoManteni, String as_codigoActivo, String as_codigoActividad, String as_valor)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "UPDATE detallemantenimiento SET codigoactivo = ?, codigoactividad = ?, valor=? WHERE codigo = ? AND codigomantenimiento = ?";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigoActivo);
            pst.setString(2, as_codigoActividad);
            pst.setString(3, as_valor);
            pst.setString(4, as_codigo);
            pst.setString(5, as_codigoManteni);
            pst.execute();
            mensaje = "actualizado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo actualizar";
        }
        return mensaje;
    }
    
    public static String eliminarDetalleMantenimiento (String as_codigo)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "DELETE FROM detallemantenimiento WHERE codigo = ? ";
            Connection con = cone.conectar();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, as_codigo);
            pst.execute();
            mensaje = "eliminado con exito";
            pst.close();
        }catch (SQLException e)
        {
            mensaje="no se pudo eliminar";
        }
        return mensaje;
    } 
    
    public static String listarDetalleMantenimiento(String as_idMantenimiento)
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT * FROM detallemantenimiento WHERE codigoMantenimiento = '"+as_idMantenimiento+"'";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){       
                mensaje += res.getString(1)+";"+res.getString(2)+";"+res.getString(3)+";"+res.getString(4)+";"+res.getString(5)+"/";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo listar";
        }
        return mensaje;
    }
    
    public String reporte1Man()
    {
        String mensaje = "";
        String sql = "";
        Persistencia cone = new Persistencia();
        try{
            sql = "SELECT codigoactivo,valor FROM detallemantenimiento";
            Connection con = cone.conectar();
            Statement cn = con.createStatement();
            ResultSet res = cn.executeQuery(sql);
            while(res.next()){       
                mensaje += res.getString(1)+";"+res.getString(2)+"/";
            }
            cn.close();
        }catch (SQLException e)
        {
            mensaje = "no se pudo listar";
        }
        return mensaje;
    }
    
    public static String HHZC_Encriptar(String HHZC_datos, String HHZC_claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = Persistencia.HHZC_crearClave(HHZC_claveSecreta);
        Cipher HHZC_cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
        HHZC_cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptar = HHZC_datos.getBytes("UTF-8");
        byte[] bytesEncriptados = HHZC_cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
        return encriptado;
    }
    
    public static String HHZC_Desencriptar(String HHZC_datosEncriptados, String HHZC_claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = Persistencia.HHZC_crearClave(HHZC_claveSecreta);
        Cipher HHZC_cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        HHZC_cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] HHZC_bytesEncriptados = Base64.getDecoder().decode(HHZC_datosEncriptados);
        byte[] HHZC_datosDesencriptados = HHZC_cipher.doFinal(HHZC_bytesEncriptados);
        String HHZC_datos = new String(HHZC_datosDesencriptados);
        return HHZC_datos;
    }
    
    private static SecretKeySpec HHZC_crearClave(String HHZC_clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] HHZC_claveEncriptacion = HHZC_clave.getBytes("UTF-8");
        MessageDigest HHZC_sha = MessageDigest.getInstance("SHA-1");
        HHZC_claveEncriptacion = HHZC_sha.digest(HHZC_claveEncriptacion);
        HHZC_claveEncriptacion = Arrays.copyOf(HHZC_claveEncriptacion, 16);
        SecretKeySpec HHZC_secretKey = new SecretKeySpec(HHZC_claveEncriptacion, "AES");
        return HHZC_secretKey;
    }
}
