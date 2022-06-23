package ru.lanya.recipeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lanya.recipeservice.models.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> getAllByAuthor(Long authorId);
}
