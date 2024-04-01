package ar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "application.issue")
@Component
public class ApplicationProperties {
    private int MAX_ALLOWED_BOOKS;
}
