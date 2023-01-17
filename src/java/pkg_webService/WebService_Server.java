/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg_webService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import pkg_persistencia.Persistencia;

/**
 *
 * @author Hu
 */
@WebService(serviceName = "WebService_Server")
public class WebService_Server {

    Persistencia persistencia = new Persistencia();
    
    // --------- Login
    @WebMethod(operationName = "login")
    public boolean login(@WebParam(name = "as_user") String as_user, @WebParam(name = "as_pass") String as_pass) {
        return persistencia.findUser(as_user, as_pass);
    }
    
    @WebMethod(operationName = "crearUsuario")
    public String crearUsuario(@WebParam(name = "as_codigo") String as_codigo, @WebParam(name = "as_user") String as_user, @WebParam(name = "as_pass") String as_pass) {
        return persistencia.crearUsuario(as_codigo, as_user, as_pass);
    }
    
    // -----------Activo
    @WebMethod(operationName = "insertarActivo")
    public String insertarActivo(@WebParam(name = "as_codigo") String as_codigo,@WebParam(name = "as_nombre") String as_nombre, @WebParam(name = "as_fechaAdq") String as_fechaAdq) {
        return persistencia.insertarActivo(as_codigo,as_nombre, as_fechaAdq);
    }
    
    @WebMethod(operationName = "listarActivo")
    public String listarActivo() {
        return persistencia.listarActivo();
    }
    
    @WebMethod(operationName = "mostrarActivo")
    public String mostrarActivo(@WebParam(name = "as_id") String as_id) {
        return persistencia.mostrarActivo(as_id);
    }
    
    @WebMethod(operationName = "actualizarActivo")
    public String actualizarActivo(@WebParam(name = "as_id") String as_id, @WebParam(name = "as_nombre") String as_nombre, @WebParam(name = "as_fechaAdq") String as_fechaAdq) {
        return persistencia.actualizarActivo(as_id, as_nombre, as_fechaAdq);
    }
    
    @WebMethod(operationName = "eliminarActivo")
    public String eliminarActivo(@WebParam(name = "as_id") String as_id) {
        return persistencia.eliminarActivo(as_id);
    }
    
    @WebMethod(operationName = "buscarActivo")
    public String buscarActivo(@WebParam(name = "as_pBuscar") String as_pBuscar) {
        return persistencia.buscarActivo(as_pBuscar);
    }
    
    // -----------Actividad
    @WebMethod(operationName = "insertarActividad")
    public String insertarActividad(@WebParam(name = "as_id") String as_id, @WebParam(name = "as_nombre") String as_nombre) throws Exception {
        return persistencia.insertarActividad(as_id, as_nombre);
    }
    
    @WebMethod(operationName = "listarActividad")
    public String listarActividad() throws Exception {
        return persistencia.listarActividad();
    }
    
    @WebMethod(operationName = "mostrarActividad")
    public String mostrarActividad(@WebParam(name = "as_id") String as_id) throws Exception {
        return persistencia.mostrarActividad(as_id);
    }
    
    @WebMethod(operationName = "actualizarActividad")
    public String actualizarActividad(@WebParam(name = "as_id") String as_id, @WebParam(name = "as_nombre") String as_nombre) throws Exception {
        return persistencia.actualizarActividad(as_id, as_nombre);
    }
    
    @WebMethod(operationName = "eliminarActividad")
    public String eliminarActividad(@WebParam(name = "as_id") String as_id) {
        return persistencia.eliminarActividad(as_id);
    }
    
    @WebMethod(operationName = "buscarActividad")
    public String buscarActividad(@WebParam(name = "as_pBuscar") String as_pBuscar) throws Exception {
        return persistencia.buscarActividad(as_pBuscar);
    }
    
    // -----------Mantenimiento
    @WebMethod(operationName = "insertarMantenimiento")
    public String insertarMantenimiento(@WebParam(name = "as_codigo") String as_codigo, @WebParam(name = "as_fecha") String as_fecha, @WebParam(name = "as_responsable") String as_responsable) {
        return persistencia.insertarMantenimiento(as_codigo,as_fecha, as_responsable);
    }
    
    @WebMethod(operationName = "listarMantenimiento")
    public String listarMantenimiento() {
        return persistencia.listarMantenimiento();
    }
    
    @WebMethod(operationName = "mostrarMantenimiento")
    public String mostrarMantenimiento(@WebParam(name = "as_id") String as_id) {
        return persistencia.mostrarMantenimiento(as_id);
    }
    
    @WebMethod(operationName = "actualizarMantenimiento")
    public String actualizarMantenimiento(@WebParam(name = "as_id") String as_id, @WebParam(name = "as_fecha") String as_fecha, @WebParam(name = "as_responsable") String as_responsable) {
        return persistencia.actualizarMantenimiento(as_id, as_fecha, as_responsable);
    }
    
    @WebMethod(operationName = "eliminarMantenimiento")
    public String eliminarMantenimiento(@WebParam(name = "as_id") String as_id) {
        return persistencia.eliminarMantenimiento(as_id);
    }
    
    @WebMethod(operationName = "buscarMantenimiento")
    public String buscarMantenimiento(@WebParam(name = "as_pBuscar") String as_pBuscar) {
        return persistencia.buscarMantenimiento(as_pBuscar);
    }
    
    // ----------- DetalleContabilidad
    @WebMethod(operationName = "insertarDetalleMantenimiento")
    public String insertarDetalleMantenimiento(@WebParam(name = "as_codigo") String as_codigo,@WebParam(name = "as_idMantenimiento") String as_idMantenimiento, @WebParam(name = "as_idActivo") String as_idActivo, @WebParam(name = "as_idActividad") String as_idActividad, @WebParam(name = "as_valor") String as_valor) {
        return persistencia.insertarDetalleMantenimiento(as_codigo,as_idMantenimiento, as_idActivo, as_idActividad, as_valor);
    }
    
    @WebMethod(operationName = "listarDetalleMantenimiento")
    public String listarDetalleMantenimiento(@WebParam(name = "as_idMantenimiento") String as_idMantenimiento) {
        return persistencia.listarDetalleMantenimiento(as_idMantenimiento);
    }
    
    @WebMethod(operationName = "mostrarDetalleMantenimiento")
    public String mostrarDetalleMantenimiento(@WebParam(name = "as_id") String as_id) {
        return persistencia.mostrarDetalleMantenimiento(as_id);
    }
    
    @WebMethod(operationName = "actualizarDetalleMantenimiento")
    public String actualizarDetalleMantenimiento(@WebParam(name = "as_id") String as_id,@WebParam(name = "as_idMantenimiento") String as_idMantenimiento, @WebParam(name = "as_idActivo") String as_idActivo, @WebParam(name = "as_idActividad") String as_idActividad, @WebParam(name = "as_valor") String as_valor) {
        return persistencia.actualizarDetalleMantenimiento(as_id, as_idMantenimiento, as_idActivo, as_idActividad, as_valor);
    }
    
    @WebMethod(operationName = "eliminarDetalleMantenimiento")
    public String eliminarDetalleMantenimiento(@WebParam(name = "as_id") String as_id) {
        return persistencia.eliminarDetalleMantenimiento(as_id);
    }
    
    @WebMethod(operationName = "buscarDetalleMantenimiento")
    public String buscarDetalleMantenimiento(@WebParam(name = "as_pBuscar") String as_pBuscar) {
        return persistencia.mostrarDetalleMantenimiento(as_pBuscar);
    }
    
    @WebMethod(operationName = "reporte1Man")
    public String reporte1Man() {
        return persistencia.reporte1Man();
    }
}
