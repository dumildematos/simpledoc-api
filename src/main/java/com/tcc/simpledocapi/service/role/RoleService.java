package com.tcc.simpledocapi.service.role;

import com.tcc.simpledocapi.dto.RoleDTO;
import com.tcc.simpledocapi.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    public void save(Role role);
}
