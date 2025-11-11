package cat.itacademy.s04.s02.n01.fruit;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitCreateRequest;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitUpdateRequest;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest
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

        FruitCreateRequest request = new FruitCreateRequest("Apple", 1);
        FruitResponse response = new FruitResponse(1L, "Apple", 1);

        when(fruitService.createFruit(request)).thenReturn(response);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
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
        List<FruitResponse> mockFruits = List.of(
                new FruitResponse(1L, "Apple", 1),
                new FruitResponse(2L, "Banana", 1)
        );

        when(fruitService.listAllFruits(null)).thenReturn(mockFruits);

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
        Long id = 1L;

        FruitResponse response = new FruitResponse(id, "Apple", 1);

        when(fruitService.getFruitById(id)).thenReturn(response);

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(1));
    }

    @Test
    void getFruitById_whenThereIsNoValidData_thenReturn404AndCustomMessage() throws Exception {
        Long id = 1L;
        doThrow(new FruitNotFoundException("Fruit not found"))
                .when(fruitService).getFruitById(id);

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Fruit not found"));
    }

    @Test
    void updateFruit_whenDataIsValid_thenReturn200AndFruitDetails() throws Exception {
        Long id = 1L;

        FruitUpdateRequest request = new FruitUpdateRequest("Banana", 5);

        FruitResponse response = new FruitResponse(id, "Banana", 5);

        when(fruitService.updateFruit(eq(id), any(FruitUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/fruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.weightInKilos").value(5));
    }



    @Test
    void deleteFruit_whenIdNotFound_then404() throws Exception {
        Long id = 5L;

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"))
                .when(fruitService).deleteFruit(id);

        mockMvc.perform(delete("/fruits/{id}", id))
                .andExpect(status().isNotFound());
    }
}
