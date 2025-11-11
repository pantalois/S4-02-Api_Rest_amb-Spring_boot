package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;

import java.util.List;
import java.util.Optional;

public interface FruitService {
    Fruit createFruit(Fruit fruit);
    List<Fruit> listAllFruits(String name);
    Optional<Fruit> getFruitById(Long id);
    Fruit updateFruit(Long id, Fruit fruit);
}
