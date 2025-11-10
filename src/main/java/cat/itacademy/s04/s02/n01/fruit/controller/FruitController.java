package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping("/fruits")
    public List<Fruit> getAllFruits() {
        return fruitService.listAllFruits();
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit addFruit(@Valid @RequestBody Fruit fruit) {
        return fruitService.createFruit(fruit);
    }

}
