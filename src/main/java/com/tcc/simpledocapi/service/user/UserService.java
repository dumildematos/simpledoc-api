package com.tcc.simpledocapi.service.user;

import com.tcc.simpledocapi.entity.User;
import org.springframework.data.domain.Page;

import java.net.MalformedURLException;
import java.util.Optional;

public interface UserService {
    User saveUser(User user, String roleName) throws MalformedURLException;
    void updateUserWithoutPassword(User user);
    User getUser(String username);

    Optional<User> getUserById(Long id);
    void addRoleToUser(String userName, String roleName);
    Page<User> getUsers(int offset, int size);
}
