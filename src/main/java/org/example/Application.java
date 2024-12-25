package org.example;

// Импорт необходимых классов и аннотаций
import org.modelmapper.ModelMapper; // Библиотека для преобразования объектов (DTO ↔ Entity)
import org.springframework.boot.SpringApplication; // Класс для запуска Spring Boot приложения
import org.springframework.boot.autoconfigure.SpringBootApplication; // Аннотация для автоматической конфигурации Spring Boot
import org.springframework.context.annotation.Bean; // Аннотация для создания Spring Bean
import org.springframework.context.annotation.Configuration; // (Не используется в данном коде, но импортирована)
import org.slf4j.Logger; // Интерфейс для ведения логов
import org.slf4j.LoggerFactory; // Фабрика для создания логгеров

/**
 * Главный класс Spring Boot приложения.
 *
 * Аннотация @SpringBootApplication объединяет три функции:
 * - @Configuration: Позволяет регистрировать бины.
 * - @EnableAutoConfiguration: Включает автоматическую конфигурацию Spring Boot.
 * - @ComponentScan: Сканирует компоненты для автоматической регистрации.
 */
@SpringBootApplication
public class Application {

    // Логгер для записи логов на основе SLF4J
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Точка входа в Spring Boot приложение.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        // Запуск приложения
        SpringApplication.run(Application.class, args);

        // Запись информационного сообщения о старте приложения
        logger.info("Application started successfully!");
    }

    /**
     * Определение бина для ModelMapper.
     *
     * ModelMapper — это библиотека для упрощения преобразования между объектами
     * (например, между DTO и Entity).
     *
     * @return Экземпляр ModelMapper, который можно использовать в других компонентах.
     */
    @Bean
    public ModelMapper modelMapper() {
        // Создание экземпляра ModelMapper
        ModelMapper modelMapper = new ModelMapper();

        // Логирование события создания бина
        logger.debug("ModelMapper bean initialized");

        // Возврат экземпляра для использования в контексте Spring
        return modelMapper;
    }
}
