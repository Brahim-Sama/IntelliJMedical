package fr.m2i.medical.helpers;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* fr.m2i.medical.service.PatientService.*(..) )")
    public void callOnPatientServiceCall() {
        System.out.println("Je suis un aspect, je m'execute avant chaque méthode de patient Service");
    }

    @After("execution(* fr.m2i.medical.controller.VilleController.*(..) )")
    public void callAfterVilleService() {
        System.out.println("Je suis un aspect, je m'execute après chaque méthode de ville Controller");
    }

    @Around("execution(* fr.m2i.medical.controller.PatientController.editPost(..) )")
    public Object callOnPatientPOST( ProceedingJoinPoint proceedingJoinPoint ) throws IOException {
        System.out.println("edit Post Avant");
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // Appel editPost
        } catch (Throwable e) {
            System.out.println("edit Post Problem");
            e.printStackTrace();
        }
        System.out.println("edit Post Après");
        return value;
    }
}