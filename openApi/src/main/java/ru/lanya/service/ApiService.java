package ru.lanya.service;

import graphql.schema.DataFetcher;
import ru.lanya.model.Recipe;

public interface ApiService {

  DataFetcher<Recipe> getRecipesByName();
}
