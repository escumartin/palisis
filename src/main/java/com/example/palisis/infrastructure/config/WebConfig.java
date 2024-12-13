package com.example.palisis.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
    }

    private static class LocalDateFormatter implements org.springframework.format.Formatter<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        @Override
        public LocalDate parse(String text, java.util.Locale locale) {
            return LocalDate.parse(text, formatter);
        }

        @Override
        public String print(LocalDate object, java.util.Locale locale) {
            return object.format(formatter);
        }
    }

    private static class LocalDateTimeFormatter implements org.springframework.format.Formatter<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        @Override
        public LocalDateTime parse(String text, java.util.Locale locale) {
            return LocalDateTime.parse(text, formatter);
        }

        @Override
        public String print(LocalDateTime object, java.util.Locale locale) {
            return object.format(formatter);
        }
    }
}
