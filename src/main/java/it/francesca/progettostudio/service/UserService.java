package it.francesca.progettostudio.service;

import it.francesca.progettostudio.dao.UserDao;
import it.francesca.progettostudio.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public long insertUtente(Utente utente){
        return userDao.insert(utente);
    }

    public List<Utente> getUsers(){
        return userDao.getUtenti();
    }
    public Utente getUserById(long idUtente){
        return userDao.getUtenteById(idUtente);
    }

    public long updateUtente(Utente utente){
        return userDao.updateUtente(utente);
    }

    public long deleteUtente(long idUtente){
        return  userDao.deleteUtente(idUtente);
    }
}
