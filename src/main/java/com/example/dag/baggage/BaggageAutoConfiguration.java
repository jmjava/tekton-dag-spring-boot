package com.example.dag.baggage;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "baggage.enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(BaggageProperties.class)
public class BaggageAutoConfiguration {

  @Bean
  public FilterRegistrationBean<BaggageContextFilter> baggageContextFilter(
      BaggageProperties properties) {
    FilterRegistrationBean<BaggageContextFilter> reg = new FilterRegistrationBean<>();
    reg.setFilter(new BaggageContextFilter(properties));
    reg.setOrder(1);
    reg.addUrlPatterns("/*");
    return reg;
  }

  @Bean
  public BaggageRestTemplateInterceptor baggageRestTemplateInterceptor(
      BaggageProperties properties) {
    return new BaggageRestTemplateInterceptor(properties);
  }

  @Bean
  public RestTemplateCustomizer baggageRestTemplateCustomizer(
      BaggageRestTemplateInterceptor interceptor) {
    return restTemplate -> restTemplate.getInterceptors().add(interceptor);
  }
}
