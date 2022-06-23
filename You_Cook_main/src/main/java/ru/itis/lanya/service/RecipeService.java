package ru.itis.lanya.service;


import ru.itis.lanya.dto.RecipeDto;
import ru.itis.lanya.entity.Recipe;
import ru.itis.lanya.entity.User;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAllByAuthor(Long authorId);
    Recipe findById(Long recipeId);
    RecipeDto createRecipe(Long userId, RecipeDto recipeDto);
    String deleteRecipe(Long recipeId);
    String editRecipe(RecipeDto recipeDto, Long recipeId);
    List<Recipe> getFavorites(User user);
}
