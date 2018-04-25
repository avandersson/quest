package se.viia.quest.repo;

import org.springframework.data.repository.CrudRepository;
import se.viia.quest.domain.Account;

import java.util.Optional;
import java.util.UUID;

/**
 * @author affe 2018-04-24
 */
public interface AccountRepo extends CrudRepository<Account, UUID> {

    Optional<Account> findByUsername(String username);
}
