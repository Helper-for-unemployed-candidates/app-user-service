package com.jobhunter.appuserservice.client;

import com.jobhunter.appuserservice.service.TokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final TokenProviderService tokenProviderService;
    private static final long TWENTY_EIGHT_DAYS = 28L * 24 * 60 * 60 * 1000;

    @Override
    public void run(String... args) {
        try {
            tokenProviderService.authorize();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelay = TWENTY_EIGHT_DAYS, initialDelay = TWENTY_EIGHT_DAYS)
    public void refresh() {
        try {
            tokenProviderService.refresh();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
