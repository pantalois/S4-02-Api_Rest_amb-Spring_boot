package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Override
    public Fruit createFruit(Fruit fruit) {

        return fruitRepository.save(fruit);
    }

    @Override
    public List<Fruit> listAllFruits(String name) {
        if (name == null || name.isEmpty()) {
            return fruitRepository.findAll();
        }
        return fruitRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Fruit> getFruitById(Long id) {
        return fruitRepository.findById(id);
    }

    @Override
    public Fruit updateFruit(Long id, Fruit fruit) {
        Fruit existing = fruitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"));

        existing.setName(fruit.getName());
        existing.setWeightInKilos(fruit.getWeightInKilos());

        return fruitRepository.save(existing);
    }
}
