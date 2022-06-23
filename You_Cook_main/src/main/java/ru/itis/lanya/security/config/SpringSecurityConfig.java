package ru.itis.lanya.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import ru.itis.lanya.security.token.AccessTokenFilter;
import ru.itis.lanya.security.token.JwtLogoutFilter;
import ru.itis.lanya.security.token.RefreshTokenFilter;
import ru.itis.lanya.security.token.TokenAuthenticationProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtLogoutFilter jwtLogoutFilter;

  @Autowired
  private AccessTokenFilter accessTokenFilter;

  @Autowired
  private RefreshTokenFilter refreshTokenFilter;

  @Autowired
  private TokenAuthenticationProvider tokenAuthenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .addFilterAt(jwtLogoutFilter, LogoutFilter.class)
        .addFilterAt(accessTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(refreshTokenFilter, AccessTokenFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(tokenAuthenticationProvider);
  }
}
