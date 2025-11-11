package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitCreateRequest;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitUpdateRequest;
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
    public ResponseEntity<List<FruitResponse>> getAllFruits(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(fruitService.listAllFruits(name));
    }

    @GetMapping("/fruits/{id}")
    public ResponseEntity<FruitResponse> getFruitById(@PathVariable Long id) {
        return ResponseEntity.ok(fruitService.getFruitById(id));
    }

    @PostMapping("/fruits")
    public ResponseEntity<FruitResponse> addFruit(@Valid @RequestBody FruitCreateRequest request) {
        FruitResponse createdFruit = fruitService.createFruit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFruit);
    }

    @PutMapping("/fruits/{id}")
    public ResponseEntity<FruitResponse> updateFruit(@PathVariable("id") Long id, @RequestBody @Valid FruitUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(fruitService.updateFruit(id, request));
    }

    @DeleteMapping("/fruits/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable("id") Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
