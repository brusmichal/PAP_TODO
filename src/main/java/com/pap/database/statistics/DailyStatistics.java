package com.pap.database.statistics;

import com.pap.database.Status;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Statistics for the given day and user that will be displayed in the report window
 */
@Document
@Builder(setterPrefix = "with")
@CompoundIndex(unique = true, name = "{'year' : 1, 'dayOfYear' : 1, 'user' : 1, 'status' : 1}")
public class DailyStatistics {
    @Id
    private String id;

    // Task owner
    private String user;

    // Year of the statistic
    private int year;

    // Day of the year of the statistic
    private int dayOfYear;

    // Status of group of tasks
    @NonNull
    private Status status;

    // Amount of tasks with given status
    private int amount;
}
