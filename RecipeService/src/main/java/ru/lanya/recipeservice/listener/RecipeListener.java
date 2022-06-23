package ru.lanya.recipeservice.listener;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lanya.recipeservice.dto.RecipeDto;
import ru.lanya.recipeservice.models.User;
import ru.lanya.recipeservice.service.RecipeService;

@Component
public class RecipeListener {

    @Autowired
    private Gson gson;

    @Autowired
    private RecipeService recipeService;

    @RabbitListener(queues = "recipeQueue", containerFactory = "containerFactory")
    public Object recipe(String message){
        return recipeService.findById(Long.parseLong(message));
    }

    @RabbitListener(queues = "recipesQueue", containerFactory = "containerFactory")
    public Object recipes(String message){
        return recipeService.findAllByAuthor(Long.parseLong(message));
    }

    @RabbitListener(queues = "createRecipeQueue", containerFactory = "containerFactory")
    public Object createRecipe(String message){
        String[] data = message.split("~~~");
        RecipeDto recipeDto = gson.fromJson(data[1], RecipeDto.class);
        return recipeService.createRecipe(Long.parseLong(data[0]), recipeDto);
    }

    @RabbitListener(queues = "deleteRecipeQueue", containerFactory = "containerFactory")
    public Object deleteRecipe(String message){
        return recipeService.deleteRecipe(Long.parseLong(message));
    }

    @RabbitListener(queues = "editRecipeQueue", containerFactory = "containerFactory")
    public Object editRecipe(String message){
        String[] data = message.split("~~~");
        RecipeDto recipeDto = gson.fromJson(data[0], RecipeDto.class);
        return recipeService.editRecipe(recipeDto, Long.parseLong(data[1]));
    }

    @RabbitListener(queues = "favoritesQueue", containerFactory = "containerFactory")
    public Object getFavorites(String message){
        User user = gson.fromJson(message, User.class);
        return recipeService.getFavorites(user);
    }
}
