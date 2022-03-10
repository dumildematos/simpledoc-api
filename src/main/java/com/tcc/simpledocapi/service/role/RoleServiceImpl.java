package com.tcc.simpledocapi.service.role;

import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> getRoles(int offset, int pageSize) {
        return roleRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public void addRoleToUser(String roleName, String userName) {

    }
}
