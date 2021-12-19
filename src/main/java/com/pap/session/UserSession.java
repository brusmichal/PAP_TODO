package com.pap.session;

import com.pap.database.user.LastLogin;
import com.pap.database.user.User;
import com.pap.database.user.repository.LastLoginRepository;
import com.pap.database.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
@DependsOn({"userRepository","lastLoginRepository"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserSession implements AlterUserSession{
    private final UserRepository userRepository;
    private final LastLoginRepository lastLoginRepository;
    private String username = null;

    @Override
    public void addUserSession(final LastLogin lastLogin)
    {
        lastLoginRepository.insert(lastLogin);
        setUser(lastLogin.getUsername());
    }

    @Override
    public void createNewUser(final User user)
    {
        userRepository.insert(user);
        setUser(user.getUsername());
    }

    private void setUser(final String username)
    {
        if(this.username == null)
        {
            this.username = username;
            log.info("User {} has started session", username);
        }
        else
        {
            log.warn("Session user is already set!!!");
        }
    }
}
