package com.pap.session;

import com.pap.database.user.LastLogin;
import com.pap.database.user.User;

public interface AlterUserSession {
    void addUserSession(final LastLogin lastLogin);
    void createNewUser(final User user);
}
