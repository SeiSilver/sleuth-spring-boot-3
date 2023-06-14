package com.spring.template.silver.app.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ScheduledTask {

  @Scheduled(fixedRate = 10000)
  public void performTask() {
    log.info("Scheduled task is running...");
  }

}
