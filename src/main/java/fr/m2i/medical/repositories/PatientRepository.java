package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<PatientEntity , Integer> {

    Iterable<PatientEntity> findByNom( String nom ); // select * from patient where nom = :nom


}