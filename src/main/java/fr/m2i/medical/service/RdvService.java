package fr.m2i.medical.service;

import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.RdvRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
public class RdvService {

    @Autowired
    private PatientRepository pr;

    @Autowired
    private RdvRepository rdvRepo;

    public Iterable<RdvEntity> findAll(  ) {
        return rdvRepo.findAll();
    }

    public Iterable<RdvEntity> findAll(int patient , String dh) {
        Date dateRecherche;
        if( patient > 0 && dh.toString().length() == 10 ){
            dateRecherche = Date.valueOf(dh); // request.getParameter("datesearch") => "2021-11-22"
            return rdvRepo.findByCustomByDateEtPatient(patient , dateRecherche );
        }else if(  patient > 0 ){
            return rdvRepo.findByPatient_Id(patient);
        }else if(  dh.toString().length() == 10){
            dateRecherche = Date.valueOf(dh); // request.getParameter("datesearch") => "2021-11-22"
            return rdvRepo.findByCustomByDate(dateRecherche);
        }

        return rdvRepo.findAll();
    }

    /* public Page<RdvEntity> findAllByPage(Integer pageNo, Integer pageSize , String search  ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        if( search != null && search.length() > 0 ){
            return rdvRepo.findByNomContains(search, paging );
        }

        return rdvRepo.findAll( paging );
    } */

    private void checkRdv( RdvEntity v ) throws Exception {

        /*if( rdvRepo.findByDateheure( v.getDateheure() ).iterator().hasNext() ){
            throw new Exception("Rdv Existe déjà");
        }*/
        Timestamp after30min = (Timestamp) v.getDateheure().clone();
        after30min.setTime(after30min.getTime() + TimeUnit.MINUTES.toMillis(15));

        Timestamp before30min = (Timestamp) v.getDateheure().clone();
        before30min.setTime(after30min.getTime() - TimeUnit.MINUTES.toMillis(15));

        System.out.println( "Je check les rdv entre " + before30min + " et " + after30min );

        Iterable<RdvEntity> retourCheck = rdvRepo.findByDateheureBetween( before30min , after30min );

        if( retourCheck.iterator().hasNext() ){
            throw new Exception("Rdv en conflit avec d'autres rdv");
        }

    }

    public RdvEntity findRdv(int id) {
        return rdvRepo.findById(id).get();
    }

    public void addRdv( RdvEntity v ) throws Exception {
        checkRdv(v);
        rdvRepo.save(v);
        //email
    }

    public void delete(int id) {
        rdvRepo.deleteById(id);
    }

    public void editRdv( int id , RdvEntity rdv) throws NoSuchElementException, Exception {
        checkRdv(rdv);
        try{
            RdvEntity rdvExistant = rdvRepo.findById(id).get();

            rdvExistant.setPatient( rdv.getPatient() );
            rdvExistant.setDateheure( rdv.getDateheure() );
            rdvExistant.setType( rdv.getType() );
            rdvExistant.setDuree( rdv.getDuree() );
            rdvExistant.setNote( rdv.getNote() );

            rdvRepo.save( rdvExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }

    }
}