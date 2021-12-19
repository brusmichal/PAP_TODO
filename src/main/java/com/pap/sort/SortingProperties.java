package com.pap.sort;

import com.pap.view.task.TaskInstance;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ConfigurationProperties(prefix = "sorting")
public class SortingProperties {

    @Setter
    private Map<String, String> classes;

    @Getter
    private final Map<String, Comparator<TaskInstance>> comparators = new HashMap<>();


    @PostConstruct
    void init() throws Exception
    {
        // Generate object from class name
        for (final var key : classes.keySet())
        {
            final var unknownClass = Class.forName(key);
            final var constructor = unknownClass.getConstructor();
            var newComparator = constructor.newInstance();
            comparators.put(classes.get(key), (Comparator<TaskInstance>) newComparator);
        }
        log.info("Loaded {} taks comparators to use in Done page",comparators.size());
    }
}

