package com.pap.view.stage;

import javafx.scene.Scene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StylesheetInitializer {

    private final List<String> cssFiles;

    @Autowired
    public StylesheetInitializer() throws IOException
    {
        cssFiles =  Files.walk(Paths.get(new File("").getAbsolutePath().replace("\\","/") +"/src/main/resources/javafx/css/"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".css"))
                .map(path -> {
                    try
                    {
                        return path.toUri().toURL().toExternalForm();
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                        return null;
                    }})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        log.info("Loaded {} stylesheet files in summary",cssFiles.size());
    }

    public void addFilesToScene(final Scene scene)
    {
        cssFiles.forEach(file -> scene.getStylesheets().add(file));
    }
}
