package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VilleRepository extends PagingAndSortingRepository<VilleEntity, Integer> {

    public List<VilleEntity> findByNomContains(String search );

    public Page<VilleEntity> findByNomContains(String search , Pageable pageable);

    public Page<VilleEntity> findAll(Pageable pageable);

}