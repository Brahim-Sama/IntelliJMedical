package fr.m2i.medical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController{

    @GetMapping(value="")
    public String dashboard(){
        return "dashboard";
    }
}