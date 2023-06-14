package com.spring.template.silver.app.aspect;

import brave.Span;
import brave.Tracing;
import brave.propagation.TraceContext;
import brave.propagation.TraceContextOrSamplingFlags;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@AllArgsConstructor
public class RabbitMQTracingAspect {

  public static final String TRACE_PARENT = "traceparent";
  private final Tracing tracing;

  @Around("@within(rabbitListener) || @annotation(rabbitListener)")
  public Object tracingMethod(ProceedingJoinPoint pjp, RabbitListener rabbitListener) {
    try {
      Object[] args = pjp.getArgs();

      for (Object arg : args) {
        if (arg instanceof Message message) {
          var traceParentHeader = message.getMessageProperties().getHeaders().get(TRACE_PARENT);
          if (traceParentHeader != null) {
            TraceContext.Extractor<String> extractor = tracing.propagation().extractor((carrier, key) -> carrier);
            TraceContextOrSamplingFlags extracted = extractor.extract(traceParentHeader.toString());
            Span headerSpan = tracing.tracer().nextSpan(extracted);
            var currentTrace = tracing.tracer().joinSpan(headerSpan.context());
            tracing.tracer().withSpanInScope(currentTrace);
          }
        }
      }

      return pjp.proceed();

    } catch (Throwable e) {
      throw new RuntimeException(e);
    } finally {
      tracing.tracer().currentSpan().finish();
    }
  }

}
