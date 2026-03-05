package com.example.dag.baggage;

import static org.junit.jupiter.api.Assertions.*;

import com.example.dag.Application;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

class BaggageAutoConfigurationTest {

  @Nested
  @SpringBootTest(
      classes = Application.class,
      properties = {"baggage.enabled=false"})
  class WhenDisabled {

    @Autowired private ApplicationContext ctx;

    @Test
    void baggageBeansAreAbsent() {
      assertFalse(ctx.containsBean("baggageContextFilter"));
      assertFalse(ctx.containsBean("baggageRestTemplateInterceptor"));
      assertFalse(ctx.containsBean("baggageRestTemplateCustomizer"));
    }
  }

  @Nested
  @SpringBootTest(
      classes = Application.class,
      properties = {"baggage.enabled=true", "baggage.role=FORWARDER"})
  class WhenEnabled {

    @Autowired private ApplicationContext ctx;

    @Test
    void baggageBeansArePresent() {
      assertTrue(ctx.containsBean("baggageContextFilter"));
      assertTrue(ctx.containsBean("baggageRestTemplateInterceptor"));
      assertTrue(ctx.containsBean("baggageRestTemplateCustomizer"));
    }
  }

  @Nested
  @SpringBootTest(classes = Application.class)
  class WhenPropertyMissing {

    @Autowired private ApplicationContext ctx;

    @Test
    void baggageBeansAreAbsentByDefault() {
      assertFalse(ctx.containsBean("baggageContextFilter"));
      assertFalse(ctx.containsBean("baggageRestTemplateInterceptor"));
    }
  }
}
