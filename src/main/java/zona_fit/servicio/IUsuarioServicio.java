package zona_fit.servicio;

import zona_fit.model.Usuarios;
import java.util.List;

public interface IUsuarioServicio {
    public List<Usuarios> listarUsuarios();

    public Usuarios buscarUsuarioPorId(Integer id);

    public void addUsuario(Usuarios usuario);

    public void deleteUsuario(Usuarios usuario);
}
