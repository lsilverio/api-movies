package br.com.teste.backend.controller;

import br.com.teste.backend.dto.ResponseDto;
import br.com.teste.backend.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @MockBean
    private ResponseDto mockResponse;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockResponse = getResponseDto();
    }

    @Test
    public void testCalculateIntervals() throws Exception {
        when(movieService.calculateIntervals()).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/movies/prizes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        ResponseDto responseDto = objectMapper.readValue(contentAsString, ResponseDto.class);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getMin());
        assertNotNull(responseDto.getMax());
        verify(movieService, times(1)).calculateIntervals();
    }

    private ResponseDto getResponseDto() throws JsonProcessingException {
        String json = "{\"min\":[{\"producer\":\"Producer 1\",\"interval\":6,\"previousWin\":1995,\"followingWin\":2001}],\"max\":[{\"producer\":\"Producer 1\",\"interval\":14,\"previousWin\":2001,\"followingWin\":2015}]}";
        return objectMapper.readValue(json, ResponseDto.class);
    }

}

