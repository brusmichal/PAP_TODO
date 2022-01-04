package com.pap.database.testcontainer;

import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static com.pap.common.Profiles.DOCKER_TEST;

@Profile(DOCKER_TEST)
public abstract class TestContainersConfiguration {

    private static final MongoDBContainer mongoDBContainer;

    @DynamicPropertySource
    public static void overrideProps(final DynamicPropertyRegistry registry)
    {
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    static
    {
        mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));
        mongoDBContainer.start();
    }
}
