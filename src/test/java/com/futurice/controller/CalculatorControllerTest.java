package com.futurice.controller;


import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CalculatorControllerTest {

    private CalculatorController calculatorController = new CalculatorController();

    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(calculatorController).build();

    @Test
    void should_return_success_on_valid_input() throws Exception {
        final String base64Query = Base64.getEncoder().encodeToString("2 + 8".getBytes("utf-8"));

       final MockHttpServletResponse response =  mockMvc.perform(get("/calculate")
                .contentType("application/json")
                .param("query", base64Query))
                .andExpect(status().isOk())
        .andReturn()
        .getResponse();

       assertEquals(response.getContentAsString(), "{\"result\":10.0,\"error\":\"false\"}");
    }

    @Test
    void should_return_error_json_on_invalid_input() throws Exception {
        final String base64Query = Base64.getEncoder().encodeToString("2+8+".getBytes("utf-8"));

       final MockHttpServletResponse response =  mockMvc.perform(get("/calculate")
                .contentType("application/json")
                .param("query", base64Query))
                .andExpect(status().isOk())
        .andReturn()
        .getResponse();

       assertEquals(response.getContentAsString(), "{\"result\":\"invalid input\",\"error\":\"true\"}");
    }

    @Test
    void should_return_success_on_executing_path_for_bundle_js() throws Exception {

        final MockHttpServletResponse response =  mockMvc.perform(get("/bundle.js")
                .contentType("text/javascript"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertEquals(response.getContentType(), "application/javascript");
    }

    @Test
    void should_return_calculator_ui_on_executing_base_url() throws Exception {

        final MockHttpServletResponse response =  mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertEquals(response.getContentAsString(), "<html><body><div id=\"app\"></div>\n" +
                "<script src=\"./bundle.js\"></script></body></html>");
    }


}