package dev.nihilncunia.fa_campaign_manager.domains.users;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "app.security.use-discord", havingValue = "true")
public interface UserDiscordRepository extends JpaRepository<UserDiscordEntity, Long> {
  Optional<UserDiscordEntity> findByDiscordId(String discordId);
}
