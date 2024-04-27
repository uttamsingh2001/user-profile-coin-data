package com.example.userprofilecoindata.repository;

import com.example.userprofilecoindata.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String username);
    UserEntity findByEmail(String email);
}
