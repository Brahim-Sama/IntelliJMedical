package fr.m2i.medical.controller;


import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService ps;

    @Autowired
    private VilleService vservice;

    // http://localhost:8080/patient
    @GetMapping(value = "")
    public String list( Model model ){
        model.addAttribute("patients" , ps.findAll() );
        return "list_patient";
    }

    // http://localhost:8080/patient/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("villes" , vservice.findAll() );
        return "add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request){
        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String naissance = request.getParameter("naissance");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int ville = Integer.parseInt(request.getParameter("ville"));

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity();
        v.setId(ville);
        PatientEntity p = new PatientEntity( 0 , nom , prenom , Date.valueOf( naissance ) , email , telephone , adresse , v );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            ps.addPatient( p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/patient";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute("villes" , vservice.findAll() );
        //... récupérer le patient à modifier et le passer à la vue
        return "add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost( Model model , @PathVariable int id ){
        //... récupérer le patient envoyé depuis le form et enregistrer en bd
        return "add_edit";
    }

    public PatientService getPs() {
        return ps;
    }

    public void setPs(PatientService ps) {
        this.ps = ps;
    }

}