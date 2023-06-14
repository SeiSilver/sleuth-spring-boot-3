package com.spring.template.silver.app.config;

import brave.baggage.BaggageFields;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SleuthConfig {

  @Bean
  public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }

  @Bean
  public CorrelationScopeCustomizer addParentId() {
    return b -> b.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.PARENT_ID));
  }

  @Bean
  public CorrelationScopeCustomizer addSampled() {
    return b -> b.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.SAMPLED));
  }

}
