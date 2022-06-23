package ru.lanya.magiccooling.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;

@Factory
public class ApplicationConfig {

  @Singleton
  OkHttpClient httpClient() {
    return new OkHttpClient();
  }
}
