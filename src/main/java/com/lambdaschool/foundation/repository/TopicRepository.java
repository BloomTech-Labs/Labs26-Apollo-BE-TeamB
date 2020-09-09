package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Topic;
import com.lambdaschool.foundation.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TopicRepository extends CrudRepository<Topic, Long> {

    /**
     * Counts the number of topic user combinations for the given topicId and userId. Answer should be only 0 or 1.
     *
     * @param topicId The topicId of the topic of the topic user combination to check
     * @param userId  The userId of the user of the topic user combination to check
     * @return A single number, a count
     */
    @Query(value = "SELECT COUNT(*) as count FROM topicusers WHERE topicId = :topicId AND userId = :userId", nativeQuery = true)
    JustTheCount checkTopicUsersCombo(long topicId, long userId);

    /**
     * Deletes the given topic, user combination
     *
     * @param topicId The topic id of the topic of this topic user combination
     * @param userId  The user id of the user of this topic user combination
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM topicusers WHERE topicId = :topicId AND userId = :userId", nativeQuery = true)
    void deleteTopicUsers(
            long topicId,
            long userId);

    /**
     * Inserts the new topic user combination
     *
     * @param uname   The username (String) of the user adding the record
     * @param topicId The topic id of the topic of this topic user combination
     * @param userId  The user id of the user of this topic user combination
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO topicusers(topicId, userId, created_by, created_date, last_modified_by, last_modified_date) VALUES (:topicId, :userId, :uname, CURRENT_TIMESTAMP, :uname, CURRENT_TIMESTAMP)",
            nativeQuery = true)
    void insertTopicUsers(
            String uname,
            long topicId,
            long userId);
}
