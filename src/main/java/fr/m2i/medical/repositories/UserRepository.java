package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username); //username

    public UserEntity findByUsernameOrEmail(String username, String email); //username or email

    public UserEntity findByUsernameOrEmailAndPassword(String username, String email, String pass); //username or email

    @Query( value="SELECT * FROM user WHERE ( username = ?1 OR email = ?1 ) AND  password = ?2", nativeQuery=true )
    public UserEntity findByCustom(String username , String pass); // , Date rdvDate

}