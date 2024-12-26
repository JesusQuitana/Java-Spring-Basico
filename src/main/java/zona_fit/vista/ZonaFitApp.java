package zona_fit.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zona_fit.model.Usuarios;
import zona_fit.servicio.IUsuarioServicio;
import zona_fit.servicio.UsuarioServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

//@Component
public class ZonaFitApp extends JFrame {
    private JPanel pane;
    private JButton save;
    private JButton limpiarButton;
    private JButton eliminarButton;
    private JTextField nombre;
    private JPanel inputs;
    private JPanel botones;
    private JScrollPane scrollPane1;
    private DefaultTableModel tablaUsuariosModel;
    private JTable tablaUsuarios;
    IUsuarioServicio usuarioServicio;
    private Integer idUser;

    @Autowired
    public ZonaFitApp(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
        runApp();
        //Accion Boton de Guardar
        save.addActionListener(e -> guardarUsuario());

        //Accion de Boton de Borrar Usuario
        eliminarButton.addActionListener(e -> eliminarUsuario());

        //Seleccionar un usuario
        tablaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarUsuario();
            }
        });

        //Accion de Boton de Limpiar
        limpiarButton.addActionListener(e -> limpiarInputs());
    }

    private void runApp() {
        setTitle("Zona Fit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pane);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        this.tablaUsuariosModel = new DefaultTableModel(0, 3);
        String[] head = {"Id", "Nombre", "Membresia"};
        tablaUsuariosModel.setColumnIdentifiers(head);
        this.tablaUsuarios = new JTable(tablaUsuariosModel);
        //Listar Usuarios
        listarUsers();
    }

    private void guardarUsuario() {
        if(nombre.getText().isEmpty()) {
            mostrarMensaje("Debe Proporcionar un Nombre");
            nombre.requestFocus();
            return;
        }
        //Recopilamos la informacion para guardarla en la base de datos
        String nombre = this.nombre.getText();

        Usuarios cliente = new Usuarios();
        cliente.setId(idUser);
        cliente.setNombre(nombre);

        if(this.idUser == null) {
            cliente.setMembresia(new Random().nextInt(999999));
            this.usuarioServicio.addUsuario(cliente);
            mostrarMensaje("Usuario Guardado Correctamente");
        }
        else {
            var info = tablaUsuarios.getSelectedRow();
            if(info != -1) {
                cliente.setMembresia(Integer.parseInt(tablaUsuarios.getModel().getValueAt(info, 2).toString()));
            }
            this.usuarioServicio.addUsuario(cliente);
            mostrarMensaje("Usuario Actualizado Correctamente");
        }
        limpiarInputs();
        listarUsers();
    }

    private void eliminarUsuario() {
        Usuarios user = usuarioServicio.buscarUsuarioPorId(this.idUser);
        usuarioServicio.deleteUsuario(user);
        mostrarMensaje("Usuario Eliminado Correctamente");
        limpiarInputs();
    }

    private void seleccionarUsuario() {
        var info = tablaUsuarios.getSelectedRow();
        if(info != -1) {
            this.idUser = Integer.parseInt(tablaUsuarios.getModel().getValueAt(info, 0).toString());
            this.nombre.setText(tablaUsuarios.getModel().getValueAt(info, 1).toString());
        }
    }

    private void listarUsers() {
        this.tablaUsuariosModel.setRowCount(0);
        List<Usuarios> users = this.usuarioServicio.listarUsuarios();
        users.forEach(user -> {
            Object[] info = {
                    user.getId(),
                    user.getNombre(),
                    user.getMembresia()
            };
            this.tablaUsuariosModel.addRow(info);
        });
    }

    private void limpiarInputs() {
        this.nombre.setText("");
        this.tablaUsuarios.getSelectionModel().clearSelection();
        this.idUser = null;
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
