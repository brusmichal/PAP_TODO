package com.pap.database.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User of an application
 */
@Getter
@Document
@Builder(setterPrefix = "with")
public class User {
    @Id
    private String id;

    // Name that identifies the user
    @NonNull
    @Indexed(unique = true)
    private String username;

    // Password used for authentication of a user
    @Setter
    @NonNull
    private String password;

    // Address on which the notifications will be sent
    @Setter
    @NonNull
    private String email;
}
