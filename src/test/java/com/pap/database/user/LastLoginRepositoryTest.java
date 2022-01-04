package com.pap.database.user;

import com.pap.database.testcontainer.TestContainersConfiguration;
import com.pap.database.user.repository.LastLoginRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Profile;

import static com.pap.common.Profiles.DOCKER_TEST;
import static com.pap.common.TestTag.MONGO_TAG;
import static org.assertj.core.api.Assertions.assertThat;

@Tag(MONGO_TAG)
@Profile(DOCKER_TEST)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class LastLoginRepositoryTest extends TestContainersConfiguration {

    @Autowired
    private LastLoginRepository lastLoginRepository;

    @BeforeAll
    void init()
    {
        new LastLoginProvider().populateDatabase(lastLoginRepository);
    }

    @Test
    void givenCorrectData_shouldReturnCorrectDocuments()
    {
        // when
        final var lastUsersLoggedIn = lastLoginRepository.findAll();

        // then
        assertThat(lastUsersLoggedIn.size()).isEqualTo(3);
        assertThat(lastUsersLoggedIn.get(0).getId()).isEqualTo("1");
        assertThat(lastUsersLoggedIn.get(0).getUsername()).isEqualTo("bob");
        assertThat(lastUsersLoggedIn.get(0).getLastLoginTime().isBefore(lastUsersLoggedIn.get(1).getLastLoginTime())).isTrue();
    }
    @Test
    void givenCorrectData_shouldReturnCorrectlyLastLoginUser()
    {
        // when
        final var lastUserLoggedIn = lastLoginRepository.findFirstByOrderByLastLoginTimeDesc();

        // then
        assertThat(lastUserLoggedIn).isNotNull();
        assertThat(lastUserLoggedIn.getId()).isEqualTo("2");
        assertThat(lastUserLoggedIn.getUsername()).isEqualTo("adam");
    }
}
