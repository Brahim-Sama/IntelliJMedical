package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.VilleRepository;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class PatientService {

    private VilleRepository vr;
    private PatientRepository pr;


    public PatientService( PatientRepository pr ){
        this.pr = pr;
    }

    private void checkPatient (PatientEntity p) throws InvalidObjectException {


        if(p.getNom().length() <= 2){
            throw new InvalidObjectException("Nom du patient invalide");
        }
        if (p.getPrenom().length()<= 2){
            throw new InvalidObjectException("Prenom du patient invalide");
        }
        if (p.getAdresse().length() < 10) {
            throw new InvalidObjectException("Adresse Non valide");
        }
       if (p.getVille().getId() ==  p.getVille().getId()) {
           throw new InvalidObjectException("Ville non Valide");
       }
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    public void addPatient (PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save( p );
    }

    public void editPatient (int id , PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        try {
            PatientEntity pExistant = pr.findById(id).get();
            //VilleEntity vExistant = vr.findById(id).get();



            pExistant.setNom(p.getNom());
            pExistant.setPrenom(p.getPrenom());
            pExistant.setAdresse(p.getAdresse());
            pExistant.setDatenaissance(p.getDatenaissance());
            pExistant.setVille(p.getVille());

            pr.save(pExistant);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}