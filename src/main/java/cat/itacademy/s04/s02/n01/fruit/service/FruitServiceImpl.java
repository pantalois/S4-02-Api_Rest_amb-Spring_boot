package cat.itacademy.s04.s02.n01.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.dto.FruitCreateRequest;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.dto.FruitUpdateRequest;
import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.mapper.FruitMapper;
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
    private final FruitMapper fruitMapper;

    public FruitServiceImpl(FruitRepository fruitRepository, FruitMapper fruitMapper) {
        this.fruitRepository = fruitRepository;
        this.fruitMapper = fruitMapper;
    }

    @Override
    public FruitResponse createFruit(FruitCreateRequest request) {
        Fruit saved = fruitRepository.save(fruitMapper.toEntity(request));
        return fruitMapper.toResponse(saved);
    }

    @Override
    public List<FruitResponse> listAllFruits(String name) {
        if (name == null || name.isEmpty()) {
            return fruitRepository.findAll()
                    .stream()
                    .map(fruitMapper::toResponse)
                    .toList();
        }
        return fruitRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(fruitMapper::toResponse)
                .toList();
    }

    @Override
    public FruitResponse getFruitById(Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"));
        return fruitMapper.toResponse(fruit);
    }

    @Override
    public FruitResponse updateFruit(Long id, FruitUpdateRequest request) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException("Fruit not found"));
        fruitMapper.updateEntity(request, fruit);
        return fruitMapper.toResponse(fruitRepository.save(fruit));
    }

    @Override
    public void deleteFruit(Long id) {
        fruitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"));
        fruitRepository.deleteById(id);
    }
}
