package com.kotak.inventoryservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyConfig {
    @Bean
    public int dependencyPlaceholder() {
        return 0;
    }
}
