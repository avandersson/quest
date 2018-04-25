package se.viia.quest.quest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import se.viia.quest.domain.Account;
import se.viia.quest.domain.Quest;
import se.viia.quest.domain.QuestCategory;
import se.viia.quest.domain.QuestState;
import se.viia.quest.repo.QuestRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author affe 2018-04-24
 */
@Service
public class QuestService {

    private final QuestRepo questRepo;

    @Autowired
    public QuestService(QuestRepo questRepo) {
        this.questRepo = questRepo;

        UUID id = UUID.randomUUID();
        Quest quest = new Quest(id);
        quest.setTitle("Clean bathroom #1.");
        quest.setCategory(QuestCategory.CLEANING);
        quest.setRequester(new Account("Admin Admin", "admin", "admin", Lists.newArrayList(new SimpleGrantedAuthority("ADMIN")), true));
        quest.setReward(200);
        quest.setState(QuestState.UNASSIGNED);
        quest.setCreated(LocalDateTime.now());
        quest.setUpdated(LocalDateTime.now());

        questRepo.save(quest);
    }

    public Iterable<Quest> getQuests() {
        return questRepo.findAll();
    }

    public List<Quest> getQuests(int page, int limit) {
        return questRepo.findAll(PageRequest.of(page, limit));
    }

    public Optional<Quest> getQuest(UUID id) {
        return questRepo.findById(id);
    }

    public Quest saveQuest(Quest quest) {
        return questRepo.save(quest);
    }

    public Quest startQuest(UUID id, Account account) {
        Preconditions.checkArgument(id != null, "Param: id can not be null!");
        Preconditions.checkArgument(account != null, "Param: account can not be null!");
        Quest quest = getQuest(id).orElseThrow(() -> new IllegalStateException(String.format("Can't start the quest with id: %s as it doesn't exist.", id)));
        if (quest.getState() != QuestState.UNASSIGNED) {
            throw new IllegalStateException(String.format("Can't start the quest with id: %s as it is in state: %s", id, quest.getState()));
        } else {
            quest.setState(QuestState.STARTED);
            quest.setAssignee(account);
            quest.setUpdated(LocalDateTime.now());
            return saveQuest(quest);
        }
    }
}
