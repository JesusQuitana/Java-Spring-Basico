package zona_fit.servicio.repository;

import zona_fit.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Integer> {
}
