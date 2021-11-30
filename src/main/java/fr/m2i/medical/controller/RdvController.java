package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/rdv")
@Secured("ROLE_ADMIN")
public class RdvController {

    @Autowired
    private RdvService rdvService;

    @Autowired
    private PatientService ps;

    //param page : numéro de la page actuelle
    // size : nbre d'élements par page
    @GetMapping(value = "")
    public String list( Model model, HttpServletRequest request ){
        Integer patientId = Integer.parseInt(request.getParameter("patient") != null && request.getParameter("patient").length() > 0 ? request.getParameter("patient") : "0" );
        String datesearch = request.getParameter("datesearch" );

        Date dateRecherche = null ;
        Iterable<RdvEntity> rdvs;

        if( datesearch != null && datesearch.length() == 10 ){
            rdvs = rdvService.findAll( patientId , datesearch );
        }else{
            rdvs = rdvService.findAll( patientId , "" );
        }

        model.addAttribute("rdvs" , rdvs );
        model.addAttribute("patients" , ps.findAll() );

        model.addAttribute("patientId" , patientId );
        model.addAttribute("dateSearch" , dateRecherche );

        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );

        return "rdv/list_rdv";
    }

    // http://localhost:8080/rdv/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("rdv" , new RdvEntity() );
        model.addAttribute("patients" , ps.findAll() );
        return "rdv/add_edit";
    }

    private RdvEntity createRDV( HttpServletRequest request ){
        String dateheure = request.getParameter("dateheure");
        dateheure = dateheure.replace("T" , " ");
        Integer duree = Integer.parseInt(request.getParameter("duree") );
        String note = request.getParameter("note");
        String type = request.getParameter("type");
        int patientId = Integer.parseInt( request.getParameter("patient") ) ;

        PatientEntity pe = new PatientEntity();
        pe.setId( patientId );

        System.out.println( "Date et heure passés  : " + dateheure );

        // Timestamp dateheure, Integer duree, String note, String type, PatientEntity patient
        // Préparation de l'entité à sauvegarder
        RdvEntity u = new RdvEntity( Timestamp.valueOf( dateheure + ":00" ) , duree , note , type , pe );
        return u;
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            rdvService.addRdv( createRDV( request ) );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        return "redirect:/rdv?success=true";
    }

    @RequestMapping( method = { RequestMethod.GET , RequestMethod.POST} , value = "/edit/{id}" )
    public String editGetPost( Model model , @PathVariable int id ,  HttpServletRequest request ) throws Exception {

        if( request.getMethod().equals("POST") ){
            rdvService.editRdv( id, createRDV( request ) );
            return "redirect:/rdv?success=true";
        }else{
            try{
                model.addAttribute("patients" , ps.findAll() );
                model.addAttribute("rdv" , rdvService.findRdv( id ) );
            }catch ( NoSuchElementException e ){
                return "redirect:/rdv?error=rdv%20introuvalble";
            }

            return "rdv/add_edit";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            rdvService.delete(id);
        }catch ( Exception e ){
            message = "?error=rdv%20introuvalble";
        }
        return "redirect:/rdv"+message;
    }

}