package com.tcc.simpledocapi.service.role;

import com.tcc.simpledocapi.dto.RoleDTO;
import com.tcc.simpledocapi.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role save(Role role);
    Page<Role> getRoles(int offset, int pagesize);
    void addRoleToUser(String roleName, String userName);
}
