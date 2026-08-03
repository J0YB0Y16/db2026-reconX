package com.dbtraining.reconx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main entry point for the ReconX trade reconciliation service.
 *
 * <p>Activated capabilities:
 * <ul>
 *   <li>{@link EnableJpaAuditing} — ADV050 @CreatedDate / @LastModifiedDate population.</li>
 *   <li>{@link EnableCaching}    — ADV081 @Cacheable on InstrumentService.</li>
 *   <li>{@link EnableKafka}      — ADV128–ADV133 Kafka producers and @KafkaListener consumers.</li>
 *   <li>{@link EnableAsync}      — ADV037 CompletableFuture-based parallel reconciliation.</li>
 * </ul>
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableKafka
@EnableAsync
public class ReconxApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReconxApplication.class, args);
    }
}

