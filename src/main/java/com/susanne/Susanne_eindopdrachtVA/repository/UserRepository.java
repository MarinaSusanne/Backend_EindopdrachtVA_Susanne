package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.authority = 'ROLE_ADMIN'")
    List<User> findUsersByAdminAuthority();

}

