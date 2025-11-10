package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping("/fruits")
    public List<Fruit> getAllFruits(@RequestParam(required = false) String name) {
        List<Fruit> fruits = fruitService.listAllFruits(name == null ? "" : name );
        return fruits.stream()
                .filter(fruitName -> name == null || name.isBlank() || fruitName.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @GetMapping("/fruits/{id}")
    public Fruit getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"));
    }

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit addFruit(@Valid @RequestBody Fruit fruit) {
        return fruitService.createFruit(fruit);
    }

}
