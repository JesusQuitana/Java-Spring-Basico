package zona_fit;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import zona_fit.vista.ZonaFitApp;

import javax.swing.*;

//@SpringBootApplication
public class ZonaFitAplication {
    public static void main(String[] args) {
        //Configurar el Modo Oscuro
        FlatDarculaLaf.setup();

        //Iniciar la fabrica de Spring
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(ZonaFitAplication.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);

        //Crear Objeto Swing de la fabrica de Spring
        SwingUtilities.invokeLater(() -> {
            ZonaFitApp app = context.getBean(ZonaFitApp.class);
            app.setVisible(true);
        });
    }
}
