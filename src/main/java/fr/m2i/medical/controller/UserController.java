package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.service.UserService;
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
@RequestMapping("/user")
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
}