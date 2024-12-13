package org.example.drawer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example") // ”казывает, где Spring должен искать компоненты дл€ инъекции
public class DrawerWindowConfig {

}
