package com.tcc.simpledocapi.repository;

import com.tcc.simpledocapi.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Query(value = "select distinct * from template t inner join template_category tc on  t.id = tc.template_id and t.price = '0.00' and tc.category_id = ?1", nativeQuery = true)
    Page<Template> findFreeTemplateByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "select t.id, t.content, t.created_at,t.name, t.description, t.price, t.cover from template t left join user_templates ut on t.id = ut.templates_id inner join user u on ut.user_id = u.id and u.id = ?1 and t.name like concat('%', ?2 ,'%')", nativeQuery = true)
    Page<Template> findUserTemplate(@Param("userId") Long userId, @Param("tName") String name, Pageable pageable);

        @Query(value = "select t.id, t.content, t.created_at,t.name, t.price, t.cover, t.description from template t  join template_category tc on tc.template_id = t.id and tc.category_id = ?1  join user_templates ut on t.id = ut.templates_id  join user u on u.id = ut.user_id  join user_roles ur on u.id = ur.user_id join role r on r.id = ur.roles_id and r.name = 'ROLE_ADMIN'  and t.name like concat('%', ?2 ,'%') group by t.id", nativeQuery = true)
    Page<Template> findAdminTemplate(@Param("categoryId") Long categoryId, @Param("tName") String tName, Pageable pageable);

    @Query(value = "select t.id, t.content, t.description, t.created_at,t.name, t.price, t.cover from template t join user_templates ut on t.id = ut.templates_id  join user u on u.id = ut.user_id  join user_roles ur on u.id = ur.user_id join role r on r.id = ur.roles_id and r.name = 'ROLE_ADMIN' and t.name like concat('%', ?1 ,'%') group by t.id", nativeQuery = true)
    Page<Template> findAllAdminTemplate(@Param("tName") String tName, Pageable pageable);

    @Modifying
    @Query(value = "delete from user_templates where user_templates.user_id =?1 and templates_id =?2", nativeQuery = true)
    void deleteUserTemplateRelation(@Param("userId") Long userId, @Param("tempId") Long tempId);

    @Query(value = "select  SUM(t.price) from template t left join user_templates ut on t.id = ut.templates_id inner join user u on ut.user_id = u.id and u.id =?1", nativeQuery = true)
    Long userTotalTemplatePrice(@Param("userId") Long userId);

}
