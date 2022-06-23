package ru.lanya.recipeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanya.recipeservice.dto.RecipeDto;
import ru.lanya.recipeservice.models.Recipe;
import ru.lanya.recipeservice.models.User;
import ru.lanya.recipeservice.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Recipe> findAllByAuthor(Long authorId) {
        return recipeRepository.getAllByAuthor(authorId);
    }

    @Override
    public Recipe findById(Long recipeId) {
        return recipeRepository.findById(recipeId).get();
    }

    @Override
    public RecipeDto createRecipe(Long userId, RecipeDto recipeDto) {
        Optional<User> user = userService.findById(userId);
        recipeRepository.save(Recipe.builder()
                .author(user.get())
                .name(recipeDto.getName())
                .ingredients(recipeDto.getIngredients())
                .steps(recipeDto.getSteps())
                .build());
        return recipeDto;
    }

    @Override
    public String deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        recipeRepository.delete(recipe);
        return recipe.getName();
    }

    @Override
    public String editRecipe(RecipeDto recipeDto, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        recipeRepository.delete(recipe);
        recipe.setName(recipeDto.getName());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setSteps(recipeDto.getSteps());
        recipeRepository.save(recipe);
        return recipeDto.getName();
    }

    @Override
    public List<Recipe> getFavorites(User user) {
        return recipeRepository.getAllByAuthor(user.getId());
    }
}
