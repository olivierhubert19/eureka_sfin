package com.example.b1sfin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Api Docs",
                description = "All about public API",
                contact = @Contact(
                        name = "Đỗ Thành Công",
                        email = "dothanhcong1932002@gmail.com"
                )
        ),
        servers = {
                @Server(description = "Dev",
                    url = "http://localhost:8081"),
                @Server(description = "Test",
                    url = "http://localhost:8081")
        }
)
public class OpenApiConfig {
}
