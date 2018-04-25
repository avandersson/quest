package se.viia.quest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author affe 2018-04-13
 */
@Document
public class Quest {

    @Id
    UUID id;
    String title;
    QuestCategory category;
    Account assignee;
    Account requester;
    int reward;
    QuestState state;
    LocalDateTime created;
    LocalDateTime updated;

    public Quest(UUID id) {
        this.id = id;
    }

    /**
     * @return value of id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title
     *
     * @param title The value to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return value of category
     */
    public QuestCategory getCategory() {
        return category;
    }

    /**
     * Sets category
     *
     * @param category The value to set
     */
    public void setCategory(QuestCategory category) {
        this.category = category;
    }

    /**
     * @return value of assignee
     */
    public Account getAssignee() {
        return assignee;
    }

    /**
     * Sets assignee
     *
     * @param assignee The value to set
     */
    public void setAssignee(Account assignee) {
        this.assignee = assignee;
    }

    /**
     * @return value of requester
     */
    public Account getRequester() {
        return requester;
    }

    /**
     * Sets requester
     *
     * @param requester The value to set
     */
    public void setRequester(Account requester) {
        this.requester = requester;
    }

    /**
     * @return value of reward
     */
    public int getReward() {
        return reward;
    }

    /**
     * Sets reward
     *
     * @param reward The value to set
     */
    public void setReward(int reward) {
        this.reward = reward;
    }

    /**
     * @return value of state
     */
    public QuestState getState() {
        return state;
    }

    /**
     * Sets state
     *
     * @param state The value to set
     */
    public void setState(QuestState state) {
        this.state = state;
    }

    /**
     * @return value of created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets created
     *
     * @param created The value to set
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * @return value of updated
     */
    public LocalDateTime getUpdated() {
        return updated;
    }

    /**
     * Sets updated
     *
     * @param updated The value to set
     */
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
