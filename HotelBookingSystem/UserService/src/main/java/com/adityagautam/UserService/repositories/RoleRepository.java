package com.adityagautam.UserService.repositories;

import com.adityagautam.UserService.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
