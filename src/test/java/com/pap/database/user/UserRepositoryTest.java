package com.pap.database.user;

import com.pap.database.testcontainer.TestContainersConfiguration;
import com.pap.database.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
class UserRepositoryTest extends TestContainersConfiguration {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void initTestData()
    {
        new UserProvider().populateDatabase(userRepository);
    }

    @Test
    void givenNotEmptyDatabase_shouldReturnCorrectDocuments()
    {
        // when
        final var users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(4);
        assertThat(users.get(0).getId()).isEqualTo("1");
        assertThat(users.get(0).getUsername()).isEqualTo("Bob");
        assertThat(users.get(0).getPassword()).isEqualTo("Secret");
        assertThat(users.get(0).getEmail()).isEqualTo("bob@dylan.com");
    }


    @ParameterizedTest
    @CsvSource({
            "Bob, true",
            "Maria, false",
            "null, false"
    })
    void givenUsername_shouldCheckIfExists(final String name, final boolean expectedResult)
    {
        // when
        final var exists = userRepository.existsUserByUsername(name);

        // then
        assertThat(exists).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "Bob, Secret, true",
            "Maria, pass, false",
            "null, pass, false",
            "Bob, null, false",
    })
    void givenUsername(final String name,final String password, final boolean expectedResult)
    {
        // when
        final var exists = userRepository.existsUserByUsernameAndPassword(name,password);

        // then
        assertThat(exists).isEqualTo(expectedResult);
    }
}



