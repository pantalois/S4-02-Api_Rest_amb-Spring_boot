package cat.itacademy.s04.s02.n01.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    List<Fruit> findByNameContainingIgnoreCase(String name);

}
