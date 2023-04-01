package com.tcc.simpledocapi.service.user;

import com.tcc.simpledocapi.entity.User;
import org.springframework.data.domain.Page;

import java.net.MalformedURLException;

public interface UserService {
    User saveUser(User user, String roleName) throws MalformedURLException;
    void updateUserWithoutPassword(User user);
    User getUser(String username);
    void addRoleToUser(String userName, String roleName);
    Page<User> getUsers(int offset, int size);
}
