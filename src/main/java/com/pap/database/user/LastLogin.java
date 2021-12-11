package com.pap.database.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document
@Builder(setterPrefix = "with")
public class LastLogin {
    @Id
    private String id;

    // Name that identifies the user
    @NonNull
    @Indexed(unique = true)
    private String username;

    // Time when the user started its last session
    @NonNull
    private LocalDateTime lastLoginTime;

    // Indicates if the session is still going on
    @Setter
    @Builder.Default
    private boolean login = true;
}

