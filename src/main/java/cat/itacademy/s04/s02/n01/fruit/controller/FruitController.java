package cat.itacademy.s04.s02.n01.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import jakarta.persistence.PostRemove;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class FruitController {

    @PostMapping("/fruits")
    @ResponseStatus(HttpStatus.CREATED)
    public Fruit addFruit(@RequestBody Fruit fruit) {
        return fruit;
    }

}
