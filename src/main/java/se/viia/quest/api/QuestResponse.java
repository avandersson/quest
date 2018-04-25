package se.viia.quest.api;

import se.viia.quest.domain.Account;
import se.viia.quest.domain.Quest;
import se.viia.quest.domain.QuestCategory;
import se.viia.quest.domain.QuestState;

import java.time.LocalDateTime;

/**
 * @author affe 2018-04-17
 */
public class QuestResponse {
    String title;
    QuestCategory category;
    Account assignee;
    Account requester;
    int reward;
    QuestState state;
    LocalDateTime created;
    LocalDateTime updated;

    public QuestResponse(Quest quest) {
        this.title = quest.getTitle();
        this.category = quest.getCategory();
        this.assignee = quest.getAssignee();
        this.requester = quest.getRequester();
        this.reward = quest.getReward();
        this.state = quest.getState();
        this.created = quest.getCreated();
        this.updated = quest.getUpdated();
    }

    /**
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return value of category
     */
    public QuestCategory getCategory() {
        return category;
    }

    /**
     * @return value of assignee
     */
    public Account getAssignee() {
        return assignee;
    }

    /**
     * @return value of requester
     */
    public Account getRequester() {
        return requester;
    }

    /**
     * @return value of reward
     */
    public int getReward() {
        return reward;
    }

    /**
     * @return value of state
     */
    public QuestState getState() {
        return state;
    }

    /**
     * @return value of created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * @return value of updated
     */
    public LocalDateTime getUpdated() {
        return updated;
    }
}
