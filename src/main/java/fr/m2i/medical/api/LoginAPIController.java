package fr.m2i.medical.api;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.UserRepository;
import fr.m2i.medical.security.UserDetailsImpl;
import fr.m2i.medical.service.StorageServiceImpl;
import fr.m2i.medical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class LoginAPIController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private StorageServiceImpl storageService;

    @Autowired
    private UserService uservice;

    @PostMapping( value = "/api/login" ,  consumes = "application/json" ,  produces = "application/json")
    public ResponseEntity<UserEntity> get( @RequestBody  UserEntity u ) {

        UserEntity user = userRepository.findByUsernameOrEmail( u.getUsername(), u.getUsername() );

        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println( "encoded pass : " + u.getPassword() );
            System.out.println( "pass en bd : " + user.getPassword() );

            // user exists
            if( encoder.matches( u.getPassword() , user.getPassword() ) ){
                user.setPassword("****");
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping( value = "/api/profil/{id}" ,  produces = "application/json")
    public ResponseEntity<UserEntity> editProfil(@PathVariable int id ,  @RequestParam("photouser") MultipartFile file , HttpServletRequest request ) throws IOException {


        // Récupération des paramètres envoyés en POST
        String titi = request.getParameter("name");
        String email = request.getParameter("email");
        String usertype = request.getParameter("roles");
        String username = request.getParameter("username");


        String photo = storageService.store(file , "src\\main\\resources\\static\\images\\uploads");

        // String username, String email, String roles, String password, String name
        // Préparation de l'entité à sauvegarderpassword
        UserEntity u = new UserEntity( username, email, usertype, "", titi , photo);
        u.setId( id );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            uservice.editProfil( id, u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        return ResponseEntity.ok(u);
    }
}