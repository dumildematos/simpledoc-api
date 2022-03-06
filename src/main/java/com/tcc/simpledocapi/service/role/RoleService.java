package com.tcc.simpledocapi.service.role;

import com.tcc.simpledocapi.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface RoleService {
    Role save(Role role);
    Page<Role> getRoles(int offset, int pageSize);
    void addRoleToUser(String roleName, String userName);
}
