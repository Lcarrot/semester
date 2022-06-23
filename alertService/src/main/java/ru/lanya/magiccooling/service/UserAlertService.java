package ru.lanya.magiccooling.service;

import java.util.List;
import java.util.Set;

import jakarta.inject.Singleton;
import ru.lanya.magiccooling.model.User;
import ru.lanya.magiccooling.repository.UserRepository;
import ru.lanya.magiccooling.service.alert.AlertService;

@Singleton
public class UserAlertService {

  private final Set<AlertService> alertServices;
  private final UserRepository userRepository;

  public UserAlertService(
      Set<AlertService> alertServices,
      UserRepository userRepository) {
    this.alertServices = alertServices;
    this.userRepository = userRepository;
  }

  public void alertUsers(Long chefId, String message) {
    List<User> users = userRepository.getSubscribersById(chefId);
    for (User user : users) {
      alertServices.forEach(alertService -> alertService.alertUser(user, message));
    }
  }
}
