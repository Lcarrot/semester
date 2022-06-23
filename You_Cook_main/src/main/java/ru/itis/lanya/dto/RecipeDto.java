package ru.itis.lanya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.lanya.entity.Ingredient;
import ru.itis.lanya.entity.Step;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeDto {

    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
}
