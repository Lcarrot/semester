package ru.lanya.recipeservice.models;


import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinTable(name = "author_id")
    private User author;

    @OneToMany()
    private List<Step> steps;

    @ManyToMany(mappedBy = "recipeList")
    private List<Ingredient> ingredients;
}
