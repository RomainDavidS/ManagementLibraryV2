package com.library.service.users;

import com.library.beans.musers.user.UsersBean;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsersService extends UserDetailsService {

    UsersBean getCurrentUser();
    String currentUserNameSimple();

    String getCurrentUserFirstName();
    String getCurrentUserLastName();
    String getCurrentUserFullName();
    String getCurrentUserEmail();
    String getCurrentUserPhone();
    Long getCurrentUserId();
    String getUser(Long id);
}
