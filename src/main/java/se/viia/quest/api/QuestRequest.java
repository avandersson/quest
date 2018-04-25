package se.viia.quest.api;


import se.viia.quest.domain.Quest;
import se.viia.quest.domain.QuestCategory;
import se.viia.quest.domain.QuestState;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author affe 2018-04-17
 */
public class QuestRequest {
    String title;
    QuestCategory category;
    String assignee;
    String requester;
    int reward;
    QuestState state;
    LocalDateTime created;
    LocalDateTime updated;

    public Quest toQuest(UUID id) {
        return new Quest(Optional.ofNullable(id).orElse(UUID.randomUUID()));
    }
}
