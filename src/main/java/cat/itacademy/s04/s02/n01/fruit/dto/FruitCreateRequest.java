package cat.itacademy.s04.s02.n01.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record FruitCreateRequest(
        @NotBlank
        @Pattern(regexp = "^[^0-9]*$", message = "El nombre no puede contener números")
        String name,
        @Positive int weightInKilos
) {}
