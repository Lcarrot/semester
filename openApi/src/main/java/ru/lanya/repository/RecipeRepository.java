package ru.lanya.repository;

import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import ru.lanya.model.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

  @Executable
  Recipe getByName(String name);
}
