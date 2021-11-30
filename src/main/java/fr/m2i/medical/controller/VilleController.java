package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.VilleService;
import org.aspectj.weaver.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ville")
public class VilleController {

    @Autowired
    private VilleService vservice;

    //param page : numéro de la page actuelle
    // size : nbre d'élements par page
    @GetMapping(value = "")
    public String list( Model model, HttpServletRequest request , @RequestParam(name = "page", defaultValue = "0") int page , @RequestParam(name="size", defaultValue="5")int size ){
        String search = request.getParameter("search");


        Page<VilleEntity> villes = vservice.findAllByPage(page , size, search);

        model.addAttribute("villes" , villes );
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );
        model.addAttribute( "search" , search );

        model.addAttribute("pageCurrent", page);

        model.addAttribute("pages", new int[villes.getTotalPages()]);

        return "ville/list_ville";
    }

    // http://localhost:8080/ville/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("ville" , new VilleEntity() );
        return "ville/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){

        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        int codePostal = Integer.parseInt( request.getParameter("codePostal") );
        String pays = request.getParameter("pays");

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity( nom , codePostal , pays );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            vservice.addVille( v );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute("ville" , v );
            model.addAttribute("error" , e.getMessage() );
            return "ville/add_edit";
        }

        return "redirect:/ville?success=true";
    }

    public VilleService getVservice() {
        return vservice;
    }

    @RequestMapping( method = { RequestMethod.GET , RequestMethod.POST} , value = "/edit/{id}" )
    public String editGetPost( Model model , @PathVariable int id ,  HttpServletRequest request ){
        System.out.println( "Add Edit Ville" + request.getMethod() );

        if( request.getMethod().equals("POST") ){
            // Récupération des paramètres envoyés en POST
            String nom = request.getParameter("nom");
            int codePostal = Integer.parseInt( request.getParameter("codePostal") );
            String pays = request.getParameter("pays");

            // Préparation de l'entité à sauvegarder
            VilleEntity v = new VilleEntity( nom , codePostal , pays );

            // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
            try{
                vservice.editVille( id , v );
            }catch( Exception e ){
                v.setId(  -1 ); // hack
                System.out.println( e.getMessage() );
                model.addAttribute("ville" , v );
                model.addAttribute("error" , e.getMessage() );
                return "ville/add_edit";
            }
            return "redirect:/ville?success=true";
        }else{
            try{
                model.addAttribute("ville" , vservice.findVille( id ) );
            }catch ( NoSuchElementException e ){
                return "redirect:/ville?error=Ville%20introuvalble";
            }

            return "ville/add_edit";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            vservice.delete(id);
        }catch ( Exception e ){
            message = "?error=Ville%20introuvalble";
        }
        return "redirect:/ville"+message;
    }

    public void setVservice(VilleService vservice) {
        this.vservice = vservice;
    }
}