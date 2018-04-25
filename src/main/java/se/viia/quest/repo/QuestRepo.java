package se.viia.quest.repo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import se.viia.quest.domain.Quest;

import java.util.List;
import java.util.UUID;

/**
 * @author affe 2018-04-24
 */
public interface QuestRepo extends CrudRepository<Quest, UUID> {

    List<Quest> findAll(Pageable pageable);
}
