package com.tcc.simpledocapi.service.user;

import com.tcc.simpledocapi.dto.UserDetailDTO;
import com.tcc.simpledocapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    User getDetailUser(String username);
    void addRoleToUser(String userName, String roleName);
    Page<User> getUsers(int offset, int size);
}
