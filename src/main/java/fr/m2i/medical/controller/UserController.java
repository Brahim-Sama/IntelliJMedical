package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.UserService;
import org.aspectj.weaver.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/user")
@Secured("ROLE_ADMIN")
public class UserController {

    @Autowired
    private UserService uservice;

    //param page : numéro de la page actuelle
    // size : nbre d'élements par page
    @GetMapping(value = "")
    public String list( Model model, HttpServletRequest request ){
        Iterable<UserEntity> users = uservice.findAll();

        model.addAttribute("users" , users );
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );

        return "user/list_user";
    }

    // http://localhost:8080/user/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("user" , new UserEntity() );
        return "user/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Récupération des paramètres envoyés en POST
        String titi = request.getParameter("tata");
        String email = request.getParameter("email");
        String usertype = request.getParameter("roles");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // String username, String email, String roles, String password, String name
        // Préparation de l'entité à sauvegarder
        UserEntity u = new UserEntity( username, email, usertype, password, titi );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            uservice.addUser( u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        return "redirect:/user?success=true";
    }

    @RequestMapping( method = { RequestMethod.GET , RequestMethod.POST} , value = "/edit/{id}" )
    public String editGetPost( Model model , @PathVariable int id ,  HttpServletRequest request ){

        if( request.getMethod().equals("POST") ){
            // Récupération des paramètres envoyés en POST
            String titi = request.getParameter("tata");
            String email = request.getParameter("email");
            String usertype = request.getParameter("roles");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // String username, String email, String roles, String password, String name
            // Préparation de l'entité à sauvegarder
            UserEntity u = new UserEntity( username, email, usertype, password, titi );

            // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
            //try{
            uservice.editUser( id, u );
            /* }catch( Exception e ){
                System.out.println( e.getMessage() );
            } */

            return "redirect:/user?success=true";
        }else{
            try{
                model.addAttribute("user" , uservice.findUser( id ) );
            }catch ( NoSuchElementException e ){
                return "redirect:/user?error=User%20introuvalble";
            }

            return "user/add_edit";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            uservice.delete(id);
        }catch ( Exception e ){
            message = "?error=User%20introuvalble";
        }
        return "redirect:/user"+message;
    }

    @PostMapping(value = "/profil/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER" })
    public String editProfil( @PathVariable int id , HttpServletRequest request ){
        // Récupération des paramètres envoyés en POST
        String titi = request.getParameter("tata");
        String email = request.getParameter("email");
        String usertype = request.getParameter("roles");
        String username = request.getParameter("username");

        // String username, String email, String roles, String password, String name
        // Préparation de l'entité à sauvegarderpassword
        UserEntity u = new UserEntity( username, email, usertype, "", titi );
        u.setId( id );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            uservice.editProfil( id, u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        // Mettre à jour l'utilisateur ????


        return "redirect:/patient?success=true";
    }

}