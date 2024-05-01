package org.dgp.hw.healthchecks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dgp.hw.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthCheckTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void shouldBeOkWhenThereAreBooks() throws Exception {
        Mockito.when(bookService.booksCount()).thenReturn(5L);

        var result = mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);

        var booksNode = objectMapper
                .readTree(
                        result
                                .getResponse()
                                .getContentAsString())
                .findValue("books");

        assertNotNull(booksNode);

        assertEquals("UP", booksNode.findValue("status").asText());
    }

    @Test
    void shouldBe5xxErrorWhenThereIsNotAnyBook() throws Exception {
        Mockito.when(bookService.booksCount()).thenReturn(0L);

        var result = mockMvc.perform(get("/actuator/health"))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertNotNull(result);

        var booksNode = objectMapper
                .readTree(
                        result
                                .getResponse()
                                .getContentAsString())
                .findValue("books");

        assertNotNull(booksNode);

        assertEquals("DOWN", booksNode.findValue("status").asText());
    }

    @Test
    void shouldBe5xxErrorWhenBookServiceIsDown() throws Exception {
        Mockito.when(bookService.booksCount()).thenThrow(RuntimeException.class);

        var result = mockMvc.perform(get("/actuator/health"))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertNotNull(result);

        var booksNode = objectMapper
                .readTree(
                        result
                                .getResponse()
                                .getContentAsString())
                .findValue("books");

        assertNotNull(booksNode);

        assertEquals("DOWN", booksNode.findValue("status").asText());
    }
}
