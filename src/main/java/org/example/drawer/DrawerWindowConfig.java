package org.example.drawer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для инициализации контекста Spring.
 * Он использует аннотации для автоматического сканирования компонентов в пакете "org.example"
 * и настройки зависимостей через Spring контейнер.
 */
@Configuration
@ComponentScan(basePackages = "org.example") // Указывает Spring, что необходимо сканировать пакет "org.example" для поиска компонентов
public class DrawerWindowConfig {

}
