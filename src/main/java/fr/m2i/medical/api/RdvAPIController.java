package fr.m2i.medical.api;

import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.service.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController()
@RequestMapping("/api/rdv")
public class RdvAPIController {

    @Autowired
    RdvService rs;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<Iterable<RdvEntity>> getallRDVApi(  @RequestParam(name = "patient", required = false, defaultValue = "0") int patient
            ,  @RequestParam(name = "datesearch", required = false, defaultValue = "") String datesearch ){

        System.out.println( "\nVal recherchée = patient =  "+ patient + ", date search = "+datesearch+"\n" );

        return ResponseEntity.ok().body(rs.findAll( patient , datesearch ));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RdvEntity> getRdvByIdApi(@PathVariable(name = "id") int id) {

        try{
            RdvEntity rendezvousGet = rs.findRdv(id);
            return ResponseEntity.ok().body(rendezvousGet);
        }catch( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<RdvEntity> addRdvApi(@RequestBody RdvEntity rdv) {

        // Convertir le format reçu par le format attendu par la méthode addRdv
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String dateFormatStr = formatter.format( rdv.getDateheure() );

        try{
            rs.addRdv( rdv );

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(rdv.getId())
                    .toUri();

            return ResponseEntity.created(uri) // created => HTTP 201
                    .body(rdv);

        }catch ( Exception e ){
            System.out.println(e.getMessage());
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RdvEntity> updateRdvApi(@PathVariable(name = "id") int id, @RequestBody RdvEntity rdv) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String dateFormatStr = formatter.format( rdv.getDateheure() );

        try{
            rs.editRdv( id,  rdv );
            return ResponseEntity.ok(rdv);

        }catch ( Exception e ){
            System.out.println(e.getMessage());
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteRdv(@PathVariable(name = "id")int id) {
        try {
            rs.delete(id);
            return ResponseEntity.ok()
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}