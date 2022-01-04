package com.pap.database.task;

import com.pap.database.Status;
import com.pap.database.task.repository.TaskRepository;
import com.pap.database.testcontainer.TestContainersConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

import static com.pap.common.Profiles.DOCKER_TEST;
import static com.pap.common.TestTag.MONGO_TAG;
import static org.assertj.core.api.Assertions.assertThat;

@Tag(MONGO_TAG)
@Profile(DOCKER_TEST)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class TaskRepositoryTest extends TestContainersConfiguration {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeAll
    void init()
    {
        new TaskProvider().populateDatabase(taskRepository);
    }

    @Test
    void givenCorrectRepository_shouldReturnAllTasks()
    {
        // when
        final var tasks = taskRepository.findAll();

        // then

    }

    @Test
    void givenCorrectUser_shouldReturnAllUsersTasks()
    {
        //given
        final var user = "adam";

        // when
        final var tasks = taskRepository.getTasksByUser(user);

        // then
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).getId()).isEqualTo("1");
        assertThat(tasks.get(0).getUser()).isEqualTo("adam");
        assertThat(tasks.get(0).getTitle()).isEqualTo("Title");
        assertThat(tasks.get(0).getStatus()).isEqualTo(Status.TO_DO);
        assertThat(tasks.get(0).reminded()).isFalse();
    }

    @Test
    void givenIncorrectUser_shouldReturnNoTasks()
    {

        // when
        final var tasks = taskRepository.getTasksByUser(null);

        // then
        assertThat(tasks.size()).isEqualTo(0);

    }

    @Test
    void givenCorrectUserAndStatus_shouldReturnCorrectTasks()
    {
        //given
        final var user = "adam";
        final var status = Status.TO_DO;

        // when
        final var tasks = taskRepository.getTasksByUserAndStatus(user,status);

        // then
        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0).getId()).isEqualTo("1");
        assertThat(tasks.get(0).getUser()).isEqualTo("adam");
        assertThat(tasks.get(0).getStatus()).isEqualTo(Status.TO_DO);
    }

    @Test
    void givenCorrectUserAndStatusAndReminder_shouldReturnCorrectTasks()
    {
        //given
        final var user = "bogdan";
        final var status = Status.TO_DO;

        // when
        final var remindedTasks = taskRepository.getTasksByUserAndStatusAndReminded(user,status,true);
        final var unRemindedTasks = taskRepository.getTasksByUserAndStatusAndReminded(user,status,false);
        final var allTasks = taskRepository.getTasksByUser(user);

        // then
        assertThat(remindedTasks.size()).isEqualTo(3);
        assertThat(remindedTasks.get(0).reminded()).isTrue();
        assertThat(unRemindedTasks.size()).isEqualTo(1);
        assertThat(unRemindedTasks.get(0).reminded()).isFalse();
        assertThat(allTasks.size()).isEqualTo(remindedTasks.size() + unRemindedTasks.size());
    }

    @Test
    void givenCorrectUsernameAndTime_shouldReturnCorrectTasks()
    {
        //given
        final var user = "bogdan";
        final var time = LocalDateTime.now().plusHours(5);

        // when
        final var taskAfter = taskRepository.getTasksByUserAndCreationTimeAfter(user,time);
        final var allTasks = taskRepository.getTasksByUser(user);

        // then
        assertThat(taskAfter.size()).isEqualTo(1);
        assertThat(taskAfter.get(0).getCreationTime().isAfter(time)).isTrue();
        assertThat(taskAfter.size()).isNotEqualTo(allTasks.size());
    }
}
