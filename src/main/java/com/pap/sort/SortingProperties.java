package com.pap.sort;

import com.pap.util.yaml.YamlPropertySourceFactory;
import com.pap.view.task.TaskInstance;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@PropertySource(value = "classpath:sorting/sorting.yml", factory = YamlPropertySourceFactory.class)
public class SortingProperties {

    @Setter
    @Value("#{'${sorting.classes}'.split(',')}")
    private List<String> values;

    @Getter
    private final Map<String, Comparator<TaskInstance>> comparators = new HashMap<>();

    @PostConstruct
    void init() throws Exception
    {
        final var classes = getDescriptionsAndClasses();

        // Generate object from class name
        for (final var key : classes.keySet())
        {
            final var unknownClass = Class.forName(classes.get(key));
            final var constructor = unknownClass.getConstructor();
            var newComparator = constructor.newInstance();
            comparators.put(key, (Comparator<TaskInstance>) newComparator);
        }
        log.info("Loaded {} taks comparators to use in Done page",comparators.size());
    }

    private Map<String, String> getDescriptionsAndClasses()
    {
        final var classes = new HashMap<String,String>();
        values.forEach(value ->{
            final var keyValue = value.split(";");
            classes.put(keyValue[0], keyValue[1]);
        });
        return classes;
    }

}
