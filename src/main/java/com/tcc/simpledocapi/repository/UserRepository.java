package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.dto.UserDetailDTO;
import com.tcc.simpledocapi.entity.Team;
import com.tcc.simpledocapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    //@Query("SELECT u.teams FROM User as u WHERE u.id =: userId")
    // Page<Team> findUserTeamsByUserId(@Param("userId") Long userId);

    //@Query(value ="SELECT u.id, u.username ,u.firstname, u.lastname, u.avatar , u.birthdate, u.auth_provider, u.roles FROM User u WHERE u.username =?1", nativeQuery = true)
    /*@Query(value = "SELECT u.id, u.username, u.firstname, u.lastname, u.birthdate, u.avatar, u.auth_provider, r.name\n" +
            " from User as u inner join UserRoles as ur on u.id = ur.user_id  inner join Role as r on r.id = ur.roles_id where u.username=\"dumilde.matos@mailinator.com\"")*/
    //UserDetailDTO findByUsernameDetail(String username);

}

