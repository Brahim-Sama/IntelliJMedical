package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.VilleRepository;
import fr.m2i.medical.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatientService {

    private VilleRepository vr;
    private PatientRepository pr;

    public PatientService(PatientRepository pr, VilleRepository vr) {
        this.pr = pr;
        this.vr = vr;
    }

    private void checkPatient(PatientEntity p) throws InvalidObjectException {
        if (p.getNom().length() <= 2) {
            throw new InvalidObjectException("Nom du patient invalide");
        }
        if (p.getPrenom().length() <= 2) {
            throw new InvalidObjectException("Prenom du patient invalide");
        }
        if (p.getAdresse().length() < 10) {
            throw new InvalidObjectException("Adresse Non valide");
        }
        if (p.getTelephone().length() <= 8) {
            throw new InvalidObjectException("Téléphone invalide");
        }

        if (p.getEmail().length() <= 5 || !validate(p.getEmail())) {
            throw new InvalidObjectException("Email invalide");
        }

        try {
            VilleEntity ve = vr.findById(p.getVille().getId()).get();
        } catch (Exception e) {
            throw new InvalidObjectException("Ville non Valide");
        }
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public static boolean validate(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    public void addPatient(PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }

    public void editPatient(int id, PatientEntity p) throws InvalidObjectException {
        checkPatient(p);

        /*Optional<PatientEntity> pe = pr.findById(id);
        PatientEntity pp = pe.get();*/
        try {
            PatientEntity pExistant = pr.findById(id).get();
            VilleEntity vExistant = vr.findById(id).get();

            pExistant.setNom(p.getNom());
            pExistant.setPrenom(p.getPrenom());
            pExistant.setAdresse(p.getAdresse());
            pExistant.setDateNaissance(p.getDateNaissance());
            pExistant.setVille(p.getVille());
            pExistant.setTelephone(p.getTelephone());
            pExistant.setEmail(p.getEmail());

            pr.save(pExistant);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}