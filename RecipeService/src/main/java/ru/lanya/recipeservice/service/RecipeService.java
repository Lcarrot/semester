package ru.lanya.recipeservice.service;

import ru.lanya.recipeservice.dto.RecipeDto;
import ru.lanya.recipeservice.models.Recipe;
import ru.lanya.recipeservice.models.User;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAllByAuthor(Long authorId);
    Recipe findById(Long recipeId);
    RecipeDto createRecipe(Long userId, RecipeDto recipeDto);
    String deleteRecipe(Long recipeId);
    String editRecipe(RecipeDto recipeDto, Long recipeId);
    List<Recipe> getFavorites(User user);
}
