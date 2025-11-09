package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addFruit_whenDataIsValid_thenReturn201AndFruitDetails() throws Exception {
        Fruit fruit = new Fruit("Apple", 1);
        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fruit)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(1));
    }

    @Test
    void addFruit_whenDataIsInvalid_thenReturn400() throws Exception {
        Fruit fruit1 = new Fruit("Apple", 0);
        Fruit fruit2 = new Fruit("", 1);
        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fruit1)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(fruit2)))
                .andExpect(status().isBadRequest());
    }
}
