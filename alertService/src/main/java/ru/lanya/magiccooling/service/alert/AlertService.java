package ru.lanya.magiccooling.service.alert;

import ru.lanya.magiccooling.model.User;

public interface AlertService {

  void alertUser(User user, String message);
}
