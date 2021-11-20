package com.pap.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum Status {
    TO_DO("Work on this task did not start"),
    IN_PROGRESS("Task is during execution"),
    DONE("Task was successfully completed"),
    TRASHED("Task was marked as to be deleted, but it can be recovered"),
    DELETED("Task is marked to be deleted from the memory");

    @Getter
    @Accessors(fluent = true)
    private String description;
}
