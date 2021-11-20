package com.pap.consts;

import com.pap.util.yaml.YamlPropertySourceFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource(value = "classpath:javafx/stageDefault.yml", factory = YamlPropertySourceFactory.class)
public class StageDefault {

    private final String title;
    private final int width;
    private final int height;

    public StageDefault(@Value("application.name")  String title,
                        @Value("${application.width}")  int width,
                        @Value("${application.height}")  int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
    }
}
