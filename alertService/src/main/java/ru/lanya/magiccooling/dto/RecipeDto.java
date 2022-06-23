package ru.lanya.magiccooling.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class RecipeDto {

  private Long recipeId;
  private Long chefId;

  public Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Long recipeId) {
    this.recipeId = recipeId;
  }

  public Long getChefId() {
    return chefId;
  }

  public void setChefId(Long chefId) {
    this.chefId = chefId;
  }
}
