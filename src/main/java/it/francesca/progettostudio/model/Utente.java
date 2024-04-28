package it.francesca.progettostudio.model;

import lombok.Data;

@Data
public class Utente {
    private String nome;
    private String cognome;
    private String mail;
    private String password;
    private long id;
}
