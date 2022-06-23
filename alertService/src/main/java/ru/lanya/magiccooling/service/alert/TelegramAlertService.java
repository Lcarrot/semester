package ru.lanya.magiccooling.service.alert;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.lanya.magiccooling.model.User;

@Singleton
public class TelegramAlertService implements AlertService {
  @Value("${telegram.api.endpoint}")
  private String apiEndpoint;

  private final OkHttpClient httpClient;
  private final ObjectMapper objectMapper;

  public TelegramAlertService(OkHttpClient httpClient,
      ObjectMapper objectMapper) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
  }

  @Override
  public void alertUser(User user, String message) {
    String query =
        apiEndpoint + "/sendMessage?chat_id=" + user.getTelegramId() + "&text=" + message;
    Request request = new Request.Builder().url(query).build();
    Call call = httpClient.newCall(request);
    try {
      call.execute().close();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Long getChatId(String name) {
    String query = apiEndpoint + "/getUpdates";
    Request request = new Request.Builder().url(query).build();
    Call call = httpClient.newCall(request);
    try (Response response = call.execute()) {
      String message = Objects.requireNonNull(response.body()).string();
      JsonNode tree = objectMapper.readTree(message);
      JsonNode array = tree.get("result");
      for (Iterator<JsonNode> it = array.elements(); it.hasNext(); ) {
        JsonNode node = it.next();
        JsonNode user = node.get(("message")).get("chat");
        if (name.equals(user.get("username").asText())) {
          return user.get("id").asLong();
        }
      }
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    throw new RuntimeException("Can't find chat id");
  }
}
