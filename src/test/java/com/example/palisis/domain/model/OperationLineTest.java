package com.example.palisis.domain.model;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperationLineTest {

    @Test
    void shouldCreateOperationLineWithAllFields() {
        // Prueba el constructor con todos los campos
        OperationLine operationLine = new OperationLine(1L, "Test Operation");

        assertThat(operationLine).isNotNull();
        assertThat(operationLine.getId()).isEqualTo(1L);
        assertThat(operationLine.getName()).isEqualTo("Test Operation");
    }

    @Test
    void shouldUseBuilderToCreateOperationLine() {
        // Prueba la creación del objeto usando el Builder
        OperationLine operationLine = OperationLine.builder()
                .id(2L)
                .name("Builder Operation")
                .build();

        assertThat(operationLine).isNotNull();
        assertThat(operationLine.getId()).isEqualTo(2L);
        assertThat(operationLine.getName()).isEqualTo("Builder Operation");
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        // Prueba los setters y getters
        OperationLine operationLine = new OperationLine();

        operationLine.setId(3L);
        operationLine.setName("Set/Get Operation");

        assertEquals(3L, operationLine.getId());
        assertEquals("Set/Get Operation", operationLine.getName());
    }

    @Test
    void shouldHandleNullName() {
        // Prueba que el nombre puede ser null sin errores
        OperationLine operationLine = OperationLine.builder()
                .id(4L)
                .name(null)
                .build();

        assertNotNull(operationLine);
        assertEquals(4L, operationLine.getId());
        assertThat(operationLine.getName()).isNull();
    }

    @Test
    void shouldCreateEmptyObject() {
        // Prueba el constructor sin argumentos (NoArgsConstructor)
        OperationLine operationLine = new OperationLine();

        assertNotNull(operationLine);
        assertThat(operationLine.getId()).isNull();
        assertThat(operationLine.getName()).isNull();
    }
}
