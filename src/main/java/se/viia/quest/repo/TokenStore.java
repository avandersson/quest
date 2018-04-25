package se.viia.quest.repo;

import org.springframework.data.repository.CrudRepository;
import se.viia.quest.auth.token.RefreshToken;

import java.util.UUID;

/**
 * @author affe 2018-04-25
 */
public interface TokenStore extends CrudRepository<RefreshToken, UUID> {

}
