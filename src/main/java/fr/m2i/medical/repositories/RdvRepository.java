package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.RdvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface RdvRepository extends JpaRepository<RdvEntity, Integer> {

    public List<RdvEntity> findAll();

    public Iterable<RdvEntity> findByPatient_Id( int p );

    @Query( value="SELECT * FROM rdv WHERE date(dateheure) = ?1", nativeQuery=true )
    public Iterable<RdvEntity> findByCustomByDate(Date dh); // , Date rdvDate

    @Query( value="SELECT * FROM rdv WHERE patient = ?1 AND  date(dateheure) = ?2", nativeQuery=true )
    public Iterable<RdvEntity> findByCustomByDateEtPatient(int patientId , Date dh); // , Date rdvDate

    @Query(value=" SELECT month(dateheure) as mois , year(dateheure) as annee , count(*) as nbre FROM rdv GROUP BY month(dateheure) , year(dateheure)", nativeQuery=true)
    List<Object> getRdvStats();

    public Iterable<RdvEntity> findByDateheure(Timestamp d );

    public Iterable<RdvEntity> findByDateheureBetween(Timestamp dateheure, Timestamp dateheure2);

    // select * from rdv where patient = :1 AND date(dateheure) = ':2'

}