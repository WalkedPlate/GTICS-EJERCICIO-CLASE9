package com.example.gticsejercicioclase7.repository;

import com.example.gticsejercicioclase7.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

}
