package com.pap.view.stage;

import com.pap.database.user.LastLogin;
import com.pap.session.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FirstPageLoader {

    @Value("classpath:/javafx/loginPage.fxml")
    private Resource loginPage;

    @Value("classpath:/javafx/mainStage.fxml")
    private Resource mainApplication;

    private final UserSession userSession;

    public URL getStartingPageURL() throws IOException
    {
        final var lastLogin = userSession.getLastLoginRepository().findFirstByOrderByLastLoginTimeDesc();
        if(lastLogin != null && lastLogin.isLogin())
        {
            log.warn("User {} was logged in without authentication",lastLogin.getUsername());
            final var lastUserLogin = LastLogin.builder()
                    .withUsername(lastLogin.getUsername())
                    .withLastLoginTime(LocalDateTime.now())
                    .build();
            userSession.addUserSession(lastUserLogin);
            return mainApplication.getURL();
        }
        return  loginPage.getURL();
    }
}
