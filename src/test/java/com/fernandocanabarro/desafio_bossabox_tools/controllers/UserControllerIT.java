package com.fernandocanabarro.desafio_bossabox_tools.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.User;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    private List<String> insertedDocumentIds = new ArrayList<>();
    private RegistrationRequestDTO registrationRequestDTO;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setup() throws Exception{
        registrationRequestDTO = new RegistrationRequestDTO("Fernando", "fernando@gmail.com", "12345Az@");
        loginRequestDTO = new LoginRequestDTO("alex@gmail.com", "123456Az@");
    }

    @AfterEach
    public void cleanUp() {
        for (String id : insertedDocumentIds) {
            mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), User.class);
        }
        insertedDocumentIds.clear();
    }

    @Test
    public void registerShouldReturnHttpStatus201WhenDataIsValid() throws Exception{
        ResultActions result = mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.fullName").value("Fernando"))
            .andExpect(jsonPath("$.email").value("fernando@gmail.com"))
            .andExpect(jsonPath("$.roles[0].authority").value("ROLE_USER"));

        String responseContent = result.andReturn().getResponse().getContentAsString();
        String id = JsonPath.parse(responseContent).read("$.id");
        insertedDocumentIds.add(id);
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenFullNameIsBlank() throws Exception{
        registrationRequestDTO.setFullName("");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("fullName"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenEmailIsBlank() throws Exception{
        registrationRequestDTO.setEmail("");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("email"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordHasLessThan8Chars() throws Exception{
        registrationRequestDTO.setPassword("aZ1234@");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve conter pelo menos 8 caracteres"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHave1UpperCaseLetter() throws Exception{
        registrationRequestDTO.setPassword("12345az@");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve conter pelo menos uma letra maiúscula"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHave1LowerCaseLetter() throws Exception{
        registrationRequestDTO.setPassword("12345AZ@");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve conter pelo menos uma letra minúscula"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHave1Number() throws Exception{
        registrationRequestDTO.setPassword("aBCDEAZ@");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve conter pelo menos um número"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHave1SpecialChar() throws Exception{
        registrationRequestDTO.setPassword("123456Az");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve conter pelo menos um caractere especial"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenEmailAlreadyExists() throws Exception{
        registrationRequestDTO.setEmail("alex@gmail.com");
        mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(registrationRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("email"))
            .andExpect(jsonPath("$.errors[0].message").value("Este Email já existe"));
    }

    @Test
    public void loginShouldReturnHttpStatus200WhenDataIsValid() throws Exception{
        mockMvc.perform(post("/users/login")
            .content(objectMapper.writeValueAsString(loginRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.expiresIn").value(86400));
    }

    @Test
    public void loginShouldReturnHttpStatus401WhenDataIsInvalid() throws Exception{
        loginRequestDTO.setPassword("12345Az@sdgkjdkg");
        mockMvc.perform(post("/users/login")
            .content(objectMapper.writeValueAsString(loginRequestDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

   
}
