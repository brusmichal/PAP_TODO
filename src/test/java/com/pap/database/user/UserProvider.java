package com.pap.database.user;

import com.pap.database.user.repository.UserRepository;

class UserProvider {

    public void populateDatabase(final UserRepository userRepository)
    {
        final var user1 = User.builder()
                .withId("1")
                .withUsername("Bob")
                .withPassword("Secret")
                .withEmail("bob@dylan.com")
                .build();

        final var user2 = User.builder()
                .withId("2")
                .withUsername("Mark")
                .withPassword("Pass")
                .withEmail("mark@newton.com")
                .build();

        final var user3 = User.builder()
                .withId("3")
                .withUsername("May")
                .withPassword("Flow2er")
                .withEmail("may@four.com")
                .build();

        final var user4 = User.builder()
                .withId("4")
                .withUsername("Ann")
                .withPassword("Secret")
                .withEmail("ann@hat.com")
                .build();

        userRepository.insert(user1);
        userRepository.insert(user2);
        userRepository.insert(user3);
        userRepository.insert(user4);
    }
}
