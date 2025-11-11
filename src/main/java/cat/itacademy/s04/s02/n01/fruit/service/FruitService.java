package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitCreateRequest;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitUpdateRequest;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

import java.util.List;
import java.util.Optional;

public interface FruitService {
    FruitResponse createFruit(FruitCreateRequest request);
    List<FruitResponse> listAllFruits(String name);
    FruitResponse getFruitById(Long id);
    FruitResponse updateFruit(Long id, FruitUpdateRequest request);
    void deleteFruit(Long id);
}
