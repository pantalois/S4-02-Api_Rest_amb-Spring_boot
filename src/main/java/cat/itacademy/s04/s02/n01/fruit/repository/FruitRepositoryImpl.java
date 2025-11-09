package cat.itacademy.s04.s02.n01.fruit.repository;


import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import org.springframework.stereotype.Service;

@Service
public class FruitRepositoryImpl implements FruitRepository {


    @Override
    public Fruit save(Fruit fruit){
        return fruit;
    }
}
