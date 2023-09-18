package endava.internship.traininglicensessharing.rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                version = "1.0.0",
                title = "Training Licenses",
                description = "Summary of all Training Licenses' paths and their specific details"),
        servers = {
                @Server(description = "Current ENV",
                url = "${swagger.serverUrl}")
        })
public class SwaggerConfiguration {
}
