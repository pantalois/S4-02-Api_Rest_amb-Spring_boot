package cat.itacademy.s04.s02.n01.fruit.mapper;

import cat.itacademy.s04.s02.n01.fruit.dto.*;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import org.springframework.stereotype.Component;

@Component
public class FruitMapper {

    public Fruit toEntity(FruitCreateRequest request) {
        Fruit fruit = new Fruit();
        fruit.setName(request.name());
        fruit.setWeightInKilos(request.weightInKilos());
        return fruit;
    }

    public void updateEntity(FruitUpdateRequest request, Fruit fruit) {
        fruit.setName(request.name());
        fruit.setWeightInKilos(request.weightInKilos());
    }

    public FruitResponse toResponse(Fruit fruit) {
        return new FruitResponse(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());
    }
}