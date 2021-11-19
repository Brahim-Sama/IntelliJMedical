package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username); //username

    public UserEntity findByUsernameOrEmail(String username, String email); //username or email

}