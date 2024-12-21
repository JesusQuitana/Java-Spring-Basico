package zona_fit.servicio;

import zona_fit.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zona_fit.servicio.repository.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements IUsuarioServicio{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuarios> listarUsuarios() {
        List<Usuarios> users = usuarioRepositorio.findAll();
        return users;
    }

    @Override
    public Usuarios buscarUsuarioPorId(Integer id) {
        Usuarios user = usuarioRepositorio.findById(id).orElse(null);
        return user;
    }

    @Override
    public void addUsuario(Usuarios usuario) {
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void deleteUsuario(Usuarios usuario) {
        usuarioRepositorio.delete(usuario);
    }
}
