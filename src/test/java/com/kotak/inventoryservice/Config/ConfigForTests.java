package com.kotak.inventoryservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Import({ContainerConfig.class, SetupConfig.class})
public class ConfigForTests extends WebMvcConfigurationSupport {
}
