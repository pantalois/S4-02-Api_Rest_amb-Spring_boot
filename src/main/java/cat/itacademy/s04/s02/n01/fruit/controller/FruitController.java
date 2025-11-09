package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class FruitController {

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit addFruit(@Valid @RequestBody Fruit fruit) {
        return fruit;
    }

}
