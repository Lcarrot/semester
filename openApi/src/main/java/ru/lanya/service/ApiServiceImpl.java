package ru.lanya.service;

import graphql.schema.DataFetcher;
import jakarta.inject.Singleton;
import ru.lanya.model.Recipe;
import ru.lanya.repository.RecipeRepository;

@Singleton
public class ApiServiceImpl implements ApiService {

  private final RecipeRepository recipeRepository;

  public ApiServiceImpl(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Override
  public DataFetcher<Recipe> getRecipesByName() {
    return dataFetchingEnvironment -> recipeRepository.getByName(dataFetchingEnvironment.getArgument("name"));
  }
}
