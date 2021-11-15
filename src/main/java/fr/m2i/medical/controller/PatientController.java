package fr.m2i.medical.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient")
public class PatientController {

    // http://localhost:8080/patient/test
    //@RequestMapping(value = "/test", method = RequestMethod.GET )
    @GetMapping(value = "/test")
    @ResponseBody
    public String testme( ){
        return "<h1>Bonjour</h1>";
    }


    /*@GetMapping(value = "/delete/{id}")
    public void delete(@PathVariable int id) {
        pr.deleteById(id);
    }*/

}