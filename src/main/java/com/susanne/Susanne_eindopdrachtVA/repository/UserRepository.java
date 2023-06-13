package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByUsername(String username);


}

