package cotato.five.weather.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(server()));
    }

    private Server server() {
        Server server = new Server();
        server.setUrl("https://kych4n-server.click/");
        return server;
    }

    private Info apiInfo() {
        return new Info()
                .title("Cotato Weather")
                .description("Let's practice Swagger UI")
                .version("1.0.0");
    }
}
