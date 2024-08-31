package com.fernandocanabarro.desafio_bossabox_tools.controllers;

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
import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.Tool;
import com.fernandocanabarro.desafio_bossabox_tools.utils.TokenUtil;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ToolControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    private List<String> insertedDocumentIds = new ArrayList<>();

    private String adminUsername,adminPassword;
    private String userUsername,userPassword;
    private String bearerTokenAdmin,bearerTokenUser;
    private String existingId,nonExistingId;
    private ToolDTO dto;

    @BeforeEach
    public void setup() throws Exception{
        adminUsername = "alex@gmail.com";
        adminPassword = "123456Az@";
        userUsername = "ana@gmail.com";
        userPassword = "123456Az@";
        bearerTokenAdmin = TokenUtil.obtainAccessToken(mockMvc, objectMapper, adminUsername, adminPassword);
        bearerTokenUser = TokenUtil.obtainAccessToken(mockMvc, objectMapper, userUsername, userPassword);
        existingId = "66d392e9604d7b39ca465145";
        nonExistingId = "123yhg3rguih32ugh3288h";
        String tag = "tag";
        List<String> tags = Arrays.asList(tag);
        dto = new ToolDTO(null, "title", "link", "description", tags);
    }

    @AfterEach
    public void cleanUp() {
        for (String id : insertedDocumentIds) {
            mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Tool.class);
        }
        insertedDocumentIds.clear();
    }

    @Test
    public void findAllShouldReturnHttpStatus200WhenTagIsBlank() throws Exception{
        mockMvc.perform(get("/tools")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title").value("Spring Boot"))
            .andExpect(jsonPath("$.content[0].link").value("https://spring.io/projects/spring-boot"))
            .andExpect(jsonPath("$.content[0].description").value("Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can 'just run'."))
            .andExpect(jsonPath("$.content[0].tags").isNotEmpty());
    }

    @Test
    public void findAllShouldReturnHttpStatus200WhenTagExists() throws Exception{
        mockMvc.perform(get("/tools?tag=node")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title").value("hotel"))
            .andExpect(jsonPath("$.content[0].link").value("https://github.com/typicode/hotel"))
            .andExpect(jsonPath("$.content[0].description").value("Local app manager. Start apps within your browser, developer tool with local .localhost domain and https out of the box."))
            .andExpect(jsonPath("$.content[0].tags").isNotEmpty());
    }

    @Test
    public void findByIdShouldReturnHttpStatus200WhenToolExists() throws Exception{
        mockMvc.perform(get("/tools/{id}",existingId)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Notion"))
            .andExpect(jsonPath("$.link").value("https://notion.so"))
            .andExpect(jsonPath("$.description").value("All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized. "))
            .andExpect(jsonPath("$.tags").isNotEmpty());
    }

    @Test
    public void findByIdShouldReturnHttpStatus404WhenToolDoesNotExist() throws Exception{
        mockMvc.perform(get("/tools/{id}",nonExistingId)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnHttpStatus403WhenNoUserIsLogged() throws Exception{
        mockMvc.perform(post("/tools")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldReturnHttpStatus403WhenCommomUserIsLogged() throws Exception{
        mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        ResultActions result = mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("title"))
            .andExpect(jsonPath("$.link").value("link"))
            .andExpect(jsonPath("$.description").value("description"))
            .andExpect(jsonPath("$.tags[0]").value("tag"));

        String responseContent = result.andReturn().getResponse().getContentAsString();
        String documentId = JsonPath.parse(responseContent).read("$.id");

        insertedDocumentIds.add(documentId);
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenTitleIsBlank() throws Exception{
        dto.setTitle("");
        mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("title"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenLinkIsBlank() throws Exception{
        dto.setLink("");
        mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("link"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenDescriptionIsBlank() throws Exception{
        dto.setDescription("");
        mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("description"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void insertShouldReturnHttpStatus422WhenTagsIsEmpty() throws Exception{
        dto.setTags(Arrays.asList());
        mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("tags"))
            .andExpect(jsonPath("$.errors[0].message").value("Deve haver pelo menos uma Tag"));
    }

    @Test
    public void updateShouldReturnHttpStatus403WhenNoUserIsLogged() throws Exception{
        mockMvc.perform(put("/tools/{id}",existingId)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void updateShouldReturnHttpStatus403WhenCommomUserIsLogged() throws Exception{
        mockMvc.perform(put("/tools/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenUser)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void updateShouldReturnHttpStatus404WhenToolDoesNotExist() throws Exception{
        mockMvc.perform(put("/tools/{id}",nonExistingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        ResultActions result = mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("title"))
            .andExpect(jsonPath("$.link").value("link"))
            .andExpect(jsonPath("$.description").value("description"))
            .andExpect(jsonPath("$.tags[0]").value("tag"));

        String responseContent = result.andReturn().getResponse().getContentAsString();
        String documentId = JsonPath.parse(responseContent).read("$.id");

        mockMvc.perform(put("/tools/{id}",documentId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("title"))
            .andExpect(jsonPath("$.link").value("link"))
            .andExpect(jsonPath("$.description").value("description"))
            .andExpect(jsonPath("$.tags[0]").value("tag"));

        insertedDocumentIds.add(documentId);
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenTitleIsBlank() throws Exception{
        dto.setTitle("");
        mockMvc.perform(put("/tools/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("title"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenLinkIsBlank() throws Exception{
        dto.setLink("");
        mockMvc.perform(put("/tools/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("link"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenDescriptionIsBlank() throws Exception{
        dto.setDescription("");
        mockMvc.perform(put("/tools/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("description"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void updateShouldReturnHttpStatus422WhenTagsIsEmpty() throws Exception{
        dto.setTags(Arrays.asList());
        mockMvc.perform(put("/tools/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("tags"))
            .andExpect(jsonPath("$.errors[0].message").value("Deve haver pelo menos uma Tag"));
    }

    @Test
    public void deleteShouldReturnHttpStatus403WhenNoUserIsLogged() throws Exception{
        mockMvc.perform(delete("/tools/{id}",existingId)
            .accept(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnHttpStatus403WhenCommomUserIsLogged() throws Exception{
        mockMvc.perform(delete("/tools/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenUser)
            .accept(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnHttpStatus404WhenToolDoesNotExist() throws Exception{
        mockMvc.perform(delete("/tools/{id}",nonExistingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnHttpStatus204WhenIdExists() throws Exception{
        ResultActions result = mockMvc.perform(post("/tools")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("title"))
            .andExpect(jsonPath("$.link").value("link"))
            .andExpect(jsonPath("$.description").value("description"))
            .andExpect(jsonPath("$.tags[0]").value("tag"));

        String responseContent = result.andReturn().getResponse().getContentAsString();
        String documentId = JsonPath.parse(responseContent).read("$.id");

        mockMvc.perform(delete("/tools/{id}",documentId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());

        insertedDocumentIds.add(documentId);
        
    }

}
