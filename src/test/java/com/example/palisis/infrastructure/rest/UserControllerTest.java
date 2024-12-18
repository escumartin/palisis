package com.example.palisis.infrastructure.rest;

import com.example.palisis.application.dto.OperationLineDTO;
import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUserById_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[0].name").value("Line 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[1].name").value("Line 2"));
    }

    @Test
    void testGetUserById_InvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/users/0"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].operationLines[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].operationLines[0].name").value("Line 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].operationLines[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].operationLines[1].name").value("Line 2"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].operationLines[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].operationLines[0].name").value("Line 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].operationLines[1].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].operationLines[1].name").value("Line 4"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Alice"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].lastName").value("Johnson"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].operationLines[0].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].operationLines[0].name").value("Line 5"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("Bob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].lastName").value("Brown"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].operationLines[0].id").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].operationLines[0].name").value("Line 6"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[4].name").value("Charlie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].lastName").value("Davis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].operationLines[0].id").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].operationLines[0].name").value("Line 7"));
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/users/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser_InvalidId() throws Exception {
        mockMvc.perform(delete("/api/v1/users/0"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCreateUser_InvalidInput() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("", "", List.of());

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCreateUser_InvalidDTO() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("John", "Doe", "SecurePass123!", List.of(new OperationLineDTO(1L, "Line 1")));

        mockMvc.perform(put("/api/v1/users/99")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateUser_InvalidDTO() throws Exception {
        mockMvc.perform(put("/api/v1/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DirtiesContext
    @Test
    void testUpdateUser_Success() throws Exception {
        List<OperationLineDTO> operationLines = List.of(new OperationLineDTO(5L, "Line 5"));
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("John updated", "Doe", "SecurePass123!", operationLines);

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[0].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[0].name").value("Line 5"));
    }

    @DirtiesContext
    @Test
    void testCreateUser_Success() throws Exception {
        List<OperationLineDTO> operationLines = List.of(new OperationLineDTO(5L, "Line 1"), new OperationLineDTO(6L, "Line 2"));
        UserCreateDTO userCreateDTO = new UserCreateDTO("John create", "Doe", operationLines);

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John create"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[0].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[0].name").value("Line 5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[1].id").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationLines[1].name").value("Line 6"));
    }

    @DirtiesContext
    @Test
    void testDeleteUser_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
