package fr.m2i.medical.service;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.UserRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository ur;

    public UserService(UserRepository ur ){
        this.ur = ur;
    }

    public Iterable<UserEntity> findAll(  ) {
        return ur.findAll();
    }

}