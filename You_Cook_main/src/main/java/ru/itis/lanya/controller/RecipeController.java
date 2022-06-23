package ru.itis.lanya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.lanya.dto.RecipeDto;
import ru.itis.lanya.entity.Recipe;
import ru.itis.lanya.entity.Response;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.service.RecipeService;
import ru.itis.lanya.service.UserService;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@PreAuthorize("isAuthenticated()")
public class RecipeController {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{user-id}/recipes")

    public Response getAllRecipes(@PathVariable("user-id") Long userId){
        String message = String.valueOf(userId);
        CorrelationData data = new CorrelationData();
        List<Recipe> recipes = (List<Recipe>) rabbitTemplate.convertSendAndReceive("recipes", (Object) message, data);
        return Response.builder()
                .success(true)
                .response(recipes)
                .build();
//        return Response.builder()
//                .success(true)
//                .response(recipeService.findAllByAuthor(userId))
//                .build();
    }

    @GetMapping("/profile/{user-id}/recipes/{recipe-id}")
    public Response getRecipe(@PathVariable("recipe-id") Long recipeId){
        String message = String.valueOf(recipeId);
        CorrelationData data = new CorrelationData();
        Recipe recipe = (Recipe) rabbitTemplate.convertSendAndReceive("recipe", (Object) message, data);
        return Response.builder()
                .success(true)
                .response(recipe)
                .build();
//        return Response.builder()
//                .success(true)
//                .response(recipeService.findById(recipeId))
//                .build();
    }

    @PostMapping("/profile/{user-id}/recipes/create")
    public Response createRecipe(@PathVariable("user-id") Long userId,
                                 RecipeDto recipeDto){
        String dto = gson.toJson(recipeDto);
        String message = userId + "~~~" + dto;
        CorrelationData data = new CorrelationData();
        RecipeDto recipe = (RecipeDto) rabbitTemplate.convertSendAndReceive("create.recipe", (Object) message, data);
        return Response.builder()
                .success(true)
                .response(recipe)
                .build();
//        return Response.builder()
//                .success(true)
//                .response(recipeService.createRecipe(userId, recipeDto))
//                .build();
    }

    @PostMapping("/profile/{user-id}/recipes/{recipe-id}/delete")
    public Response deleteRecipe(@PathVariable("user-id") Long userId,
                                 @PathVariable("recipe-id") Long recipeId,
                                 @RequestHeader("A-TOKEN") String accessToken){
        User user = userService.findByAccessToken(accessToken).get();
        if (Objects.equals(user.getId(), userId)){
            String message = String.valueOf(recipeId);
            CorrelationData data = new CorrelationData();
            String result = (String) rabbitTemplate.convertSendAndReceive("delete.recipe", (Object) message, data);
            return Response.builder()
                    .success(true)
                    .response("Рецепт " + result + " успешно удален!")
                    .build();
//            return Response.builder()
//                    .success(true)
//                    .response("Рецепт " + recipeService.deleteRecipe(recipeId) + " успешно удален!")
//                    .build();
        }
        return Response.builder()
                .success(false)
                .response("Вы не можете удалить этот рецепт, потому что он создан не Вами!")
                .build();
    }

    @PostMapping("/profile/{user-id}/recipes/{recipe-id}/edit")
    public Response editRecipe(@PathVariable("user-id") Long userId,
                               @PathVariable("recipe-id") Long recipeId,
                               @RequestHeader("A-TOKEN") String accessToken,
                               RecipeDto recipeDto){
        User user = userService.findByAccessToken(accessToken).get();
        if (Objects.equals(user.getId(), userId)){
            String dto = gson.toJson(recipeDto);
            String message = dto + "~~~" + recipeId;
            CorrelationData data = new CorrelationData();
            String result = (String) rabbitTemplate.convertSendAndReceive("edit.recipe", (Object) message, data);
            return Response.builder()
                    .success(true)
                    .response("Рецепт " + result + " успешно изменен!")
                    .build();
//            return Response.builder()
//                    .success(true)
//                    .response("Рецепт " + recipeService.editRecipe(recipeDto, recipeId) + " успешно изменен!")
//                    .build();
        }
        return Response.builder()
                .success(false)
                .response("Вы не можете изменять этот рецепт, потому что он создан не Вами!")
                .build();
    }

    @GetMapping("/favorites")
    public Response getMyFavorites(@RequestHeader("A-TOKEN") String accessToken){
        User user = userService.findByAccessToken(accessToken).get();
        String dto = gson.toJson(user);
        CorrelationData data = new CorrelationData();
        List<Recipe> favorites = (List<Recipe>) rabbitTemplate.convertSendAndReceive("favorites", (Object) dto, data);
        if (favorites.isEmpty()){
            return Response.builder()
                    .success(true)
                    .response("Список ваших избранных рецептов пуст!")
                    .build();
        }
        return Response.builder()
                .success(true)
                .response(favorites)
                .build();
    }
}
