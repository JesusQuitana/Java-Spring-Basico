package zona_fit.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zona_fit.model.Usuarios;
import zona_fit.servicio.IUsuarioServicio;

import java.util.List;
import java.util.Random;

@Component
@ViewScoped //Solo si la pagina es de una sola vista
public class IndexController {

    @Autowired
    IUsuarioServicio usuarioServicio;
    private List<Usuarios> usuarios;
    private Usuarios usuarioSeleccionado;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostConstruct //Metodo que se manda a llamar automaticamente despues del constructor
    public void init() {
        cargarDatos();
    }

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public Usuarios getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuarios usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public void cargarDatos() {
        this.usuarios = this.usuarioServicio.listarUsuarios();
        this.usuarios.forEach(usuario->logger.info(usuario.toString()));
    }

    public void agregarUsuario() {
        this.usuarioSeleccionado = new Usuarios();
    }

    public void guardarUsuario() {
        logger.info("Usuario guardado" + this.usuarioSeleccionado.toString());
        //Agregar
        if(this.usuarioSeleccionado.getId() == null) {
            this.usuarioSeleccionado.setMembresia(new Random().nextInt(0, 999999));
            this.usuarioServicio.addUsuario(usuarioSeleccionado);
            this.usuarios.add(usuarioSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Agregado con Exito"));
        }
        //Actualizar
        else {
            this.usuarioServicio.addUsuario(this.usuarioSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Actulizado Correctamente"));
        }

        //Ocultar la ventana Modal
        PrimeFaces.current().executeScript("PF('ventanaModalUsuario').hide()");
        //Actualizar la ventana Modal
        PrimeFaces.current().ajax().update("usuarios-form:mensajes", "usuarios-form:usuarios-tabla");
        //Reset al Usuario Seleccionado
        this.usuarioSeleccionado = null;
    }

    public void eliminarUsuario() {
        logger.info("Usuario eliminado" + this.usuarioSeleccionado.toString());
        this.usuarioServicio.deleteUsuario(this.usuarioSeleccionado);
        PrimeFaces.current().ajax().update("usuarios-form:mensajes", "usuarios-form:usuarios-tabla");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Eliminado con Exito"));
        this.usuarios.remove(this.usuarioSeleccionado);
        this.usuarioSeleccionado = null;
    }
}
