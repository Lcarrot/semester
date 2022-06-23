package ru.itis.lanya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.lanya.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> getAllByAuthor(Long authorId);
}
