package com.pap.database.user;

import com.pap.database.user.repository.LastLoginRepository;

import java.time.LocalDateTime;

class LastLoginProvider {
    public void populateDatabase(final LastLoginRepository lastLoginRepository)
    {
        final var lastLogin = LastLogin.builder()
                .withId("1")
                .withUsername("bob")
                .withLastLoginTime(LocalDateTime.now().plusHours(1))
                .withLogin(true)
                .build();

        final var lastLogin2 = LastLogin.builder()
                .withId("2")
                .withUsername("adam")
                .withLastLoginTime(LocalDateTime.now().plusHours(3))
                .withLogin(false)
                .build();

        final var lastLogin3 = LastLogin.builder()
                .withId("3")
                .withUsername("ann")
                .withLastLoginTime(LocalDateTime.now().plusHours(2))
                .withLogin(true)
                .build();

        lastLoginRepository.insert(lastLogin);
        lastLoginRepository.insert(lastLogin2);
        lastLoginRepository.insert(lastLogin3);
    }
}
