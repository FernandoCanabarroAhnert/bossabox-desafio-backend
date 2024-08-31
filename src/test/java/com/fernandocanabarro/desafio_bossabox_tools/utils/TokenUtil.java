package com.fernandocanabarro.desafio_bossabox_tools.utils;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.LoginRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.json.JacksonJsonParser;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TokenUtil {

    public static String obtainAccessToken(MockMvc mockMvc,ObjectMapper objectMapper,String username, String password) throws Exception{
        ResultActions result = mockMvc.perform(post("/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequestDTO(username, password)))
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("accessToken").toString();
    }
}
