package ru.lanya.magiccooling.listener;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import ru.lanya.magiccooling.dto.RecipeDto;
import ru.lanya.magiccooling.service.UserAlertService;
import ru.lanya.magiccooling.service.alert.TelegramAlertService;

@RabbitListener
public class AlertRabbitListener {

  private final UserAlertService userAlertService;
  private final TelegramAlertService telegramAlertService;

  public AlertRabbitListener(UserAlertService userAlertService,
      TelegramAlertService telegramAlertService) {
    this.userAlertService = userAlertService;
    this.telegramAlertService = telegramAlertService;
  }


  @Queue("alertQueue")
  void alertUsers(RecipeDto recipeDto) {
    userAlertService.alertUsers(recipeDto.getChefId(), "some interesting message");
  }

  @Queue("registrationAlertQueue")
  Long getTelegramChatId(String name) {
    return telegramAlertService.getChatId(name);
  }

}
