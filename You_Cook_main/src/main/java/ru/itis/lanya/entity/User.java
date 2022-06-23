package ru.itis.lanya.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.lanya.validation.ValidPassword;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Builder
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Email(message = "{errors.incorrect.email}")
    private String email;
    @ValidPassword(message = "{errors.incorrect.password}")
    private String password;

    private String redisId;

    private Long telegramId;

    @OneToMany()
    @JoinColumn(name = "subscribers_id")
    private List<User> subscribers;

    @OneToMany()
    @JoinColumn(name = "subscription_id")
    private List<User> subscriptions;

    @OneToMany()
    private List<Recipe> recipes;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role{
        USER("USER"), ADMIN("ADMIN");

        Role(String user) {

        }
    }

}
