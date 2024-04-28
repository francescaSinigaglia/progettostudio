package it.francesca.progettostudio.controller;

import it.francesca.progettostudio.dto.CustomResponse;
import it.francesca.progettostudio.model.Utente;
import it.francesca.progettostudio.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
     @Autowired
    UserService userService;

    @GetMapping("")
    public CustomResponse<List<Utente>> getUsers(){
        List<Utente> users = userService.getUsers();
        return new CustomResponse<>(users,200);
    }

    @GetMapping("/{userId}")
    public CustomResponse<Utente> getUserById(@PathParam("userId") long userId){
        Utente userById = userService.getUserById(userId);
        return new CustomResponse<>(userById, 200);
    }

    @PostMapping
    public CustomResponse<String> insertUtente(@RequestBody Utente utente){
        long rows = userService.insertUtente(utente);
        if(rows > 0){
            return new CustomResponse<>("Utente inserito", 200);
        }
        return new CustomResponse<>("ERRORE nell'inserimento utente", 500);
    }

    @DeleteMapping("/{userId}")
    public CustomResponse<String> deleteUtente(@PathParam("userId") long userId){
        long rows = userService.deleteUtente(userId);
        if(rows > 0){
            return new CustomResponse<>("Utente cancellato", 200);
        }
        return new CustomResponse<>("ERRORE nella cancellazione dell'utente", 500);
    }

    @PutMapping("/{userId}")
    public CustomResponse<String> updateUtente(@PathParam("userId") long userId, @RequestBody Utente utente){
        utente.setId(userId);
        long rows = userService.updateUtente(utente);
        if(rows > 0){
            return new CustomResponse<>("Utente modificato", 200);
        }
        return new CustomResponse<>("ERRORE durante la modifica dell'utente", 500);
    }
}
