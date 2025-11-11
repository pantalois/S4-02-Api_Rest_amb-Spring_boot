package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    FruitRepository fruitRepository;

    @MockitoBean
    FruitService fruitService;

    @BeforeEach
    void deleteAll() {
        fruitRepository.deleteAll();
    }

    @Test
    void addFruit_whenDataIsValid_thenReturn201AndFruitDetails() throws Exception {

        Fruit fruit = new Fruit("Apple", 1);
        when(fruitRepository.save(fruit)).thenReturn(fruit);

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

    @Test
    void getAllFruits_whenThereIsData_thenReturn200AndFruitDetails() throws Exception {

        List<Fruit> mockFruits = List.of(
                new Fruit("Apple", 1),
                new Fruit("Banana", 1)
        );

        when(fruitRepository.findAll()).thenReturn(mockFruits);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].weightInKilos").value(1))
                .andExpect(jsonPath("$[1].name").value("Banana"))
                .andExpect(jsonPath("$[1].weightInKilos").value(1));
    }

    @Test
    void getAllFruits_whenThereIsNoData_thenReturn200AndFruitDetails() throws Exception {
        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk());
    }

    @Test
    void getFruitById_whenThereIsData_thenReturn200AndFruitDetails() throws Exception {
        Fruit fruit = new Fruit("Apple", 1);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(1));
    }

    @Test
    void getFruitById_whenThereIsNoValidData_thenReturn404AndCustomMessage() throws Exception {
        when(fruitRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Fruit not found"));

    }

    @Test
    void updateFruit_whenDataIsValid_thenReturn200AndFruitDetails() throws Exception {
    }
}
