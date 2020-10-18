package edu.mum.cs.onlinemarketplace.repository;

import edu.mum.cs.onlinemarketplace.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name <> 'ADMIN'")
    List<Role> findAllCommonRoles();
}
