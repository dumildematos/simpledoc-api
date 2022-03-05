package com.tcc.simpledocapi.service.role;

import com.tcc.simpledocapi.dto.RoleDTO;
import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleImpl implements RoleService{

    private final RoleRepository roleRepository;


    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
