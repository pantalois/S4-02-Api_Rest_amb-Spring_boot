package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.service.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping("/fruits")
    public ResponseEntity<List<Fruit>> getAllFruits(@RequestParam(required = false) String name) {
        List<Fruit> fruits = fruitService.listAllFruits(name);
        return ResponseEntity.ok(fruits);
    }

    @GetMapping("/fruits/{id}")
    public Fruit getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"));
    }

    @PostMapping("/fruits")
    public ResponseEntity<Fruit> addFruit(@Valid @RequestBody Fruit fruit) {
        Fruit createdFruit = fruitService.createFruit(fruit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFruit);
    }

    @PutMapping("/fruits/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable("id") Long id, @RequestBody @Valid Fruit fruit) {
        Fruit updatedFruit = fruitService.updateFruit(id, fruit);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFruit);
    }


}
