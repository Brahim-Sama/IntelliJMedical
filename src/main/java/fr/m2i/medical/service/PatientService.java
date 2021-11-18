package fr.m2i.medical.service;

import fr.m2i.medical.api.PatientAPIController;
import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepository;
import fr.m2i.medical.repositories.VilleRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatientService {

    private PatientRepository pr;
    private VilleRepository vr;

    public PatientService(PatientRepository pr, VilleRepository vr ){
        this.pr = pr;
        this.vr = vr;
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public Iterable<PatientEntity> findAll( String search ) {
        if( search != null && search.length() > 0 ){
            return pr.findByNomContainsOrPrenomContains( search , search );
        }
        return pr.findAll();
    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    public static boolean validate(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private void checkPatient( PatientEntity p ) throws InvalidObjectException {

        if( p.getPrenom().length() <= 2 ){
            throw new InvalidObjectException("Prénom invalide");
        }

        if( p.getNom().length() <= 2 ){
            throw new InvalidObjectException("Nom invalide");
        }

        if( p.getAdresse().length() <= 10 ){
            throw new InvalidObjectException("Adresse invalide");
        }

        if( p.getTelephone().length() <= 8 ){
            throw new InvalidObjectException("Téléphone invalide");
        }

        if( p.getEmail().length() <= 5 || !validate( p.getEmail() ) ){
            throw new InvalidObjectException("Email invalide");
        }

        //System.out.println( "Ville passée en param " + p.getVille().getId() );

        /* try{
            VilleEntity ve = vr.findById(p.getVille().getId()).get();
        }catch( Exception e ){
            throw new InvalidObjectException("Ville invalide");
        } */

        VilleEntity ve = vr.findById(p.getVille().getId()).orElseGet( null );
        if( ve == null ){
            throw new InvalidObjectException("Ville invalide");
        }
    }

    public void addPatient(PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }

    public void editPatient(int id, PatientEntity p) throws InvalidObjectException {
        checkPatient(p);

        /*Optional<PatientEntity> pe = pr.findById(id);
        PatientEntity pp = pe.orElse( null );*/

        try{
            PatientEntity pExistant = pr.findById(id).get();

            pExistant.setPrenom( p.getPrenom() );
            pExistant.setNom( p.getNom() );
            pExistant.setAdresse( p.getAdresse() );
            pExistant.setDateNaissance( p.getDateNaissance() );
            pExistant.setVille( p.getVille() );
            pExistant.setTelephone( p.getTelephone() );
            pExistant.setEmail( p.getEmail() );

            pr.save( pExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }
}