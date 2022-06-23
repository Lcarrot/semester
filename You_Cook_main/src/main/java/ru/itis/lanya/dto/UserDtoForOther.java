package ru.itis.lanya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.lanya.entity.Recipe;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDtoForOther {

    private String username;
    private Long subscribers;
    private List<Recipe> recipes;

}
