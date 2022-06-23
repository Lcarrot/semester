package ru.lanya.magiccooling.repository;

import java.util.List;

import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import ru.lanya.magiccooling.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  @Executable
  @Query("select user.subscribers from User as user where user.id=:chefId")
  List<User> getSubscribersById(Long chefId);
}
