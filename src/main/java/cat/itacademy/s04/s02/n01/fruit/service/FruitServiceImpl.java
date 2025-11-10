package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

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
}
