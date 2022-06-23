package ru.itis.lanya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.lanya.entity.Recipe;
import ru.itis.lanya.entity.User;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDtoForMyself {

    private String username;
    private String email;
    private Long telegram;
    private Long subscribersCount;
    private List<User> subscribers;
    private List<User> subscriptions;
    private List<Recipe> recipes;
}
