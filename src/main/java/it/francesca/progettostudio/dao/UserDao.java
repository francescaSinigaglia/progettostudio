package it.francesca.progettostudio.dao;

import it.francesca.progettostudio.model.Utente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao extends AbstractDao{
    private static final String INSERT_QUERY = "INSERT INTO utenti (nome, cognome, mail, password, id_utenti) VALUES (:nome, :cognome, :mail, :password, :idUtente)";

    private static final String SELECT_ALL = "SELECT * FROM utenti";

    private static final String SELECT_BY_ID = "SELECT * FROM utenti WHERE user_id = :id";

    private static final String UPDATE_UTENTE = "UPDATE utenti SET nome = :nome, cognome =: cognome, mail = :mail WHERE id_utenti = :idUtente";

    private static final String DELETE_UTENTE = "DELETE FROM utenti WHERE id_utenti = :idUtente";


    public long insert(Utente utente){
        Map<String, Object> params = new HashMap<>();
        params.put("nome", utente.getNome());
        params.put("cognome", utente.getCognome());
        params.put("mail", utente.getMail());
        params.put("password", utente.getPassword());
        params.put("id_utenti", getIdFromSequence("UTENTI_SEQ"));
        return executeUpdate(INSERT_QUERY, params);
    }
    public List<Utente> getUtenti(){
        return executeQuery(SELECT_ALL, mappingFunction);
    }

    public Utente getUtenteById(long id){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return executeSingleResultQuery(SELECT_BY_ID, params, mappingFunction);
    }

    MappingFunction<Utente> mappingFunction = rs -> {
        Utente utente = new Utente();
        utente.setId(rs.getLong("id"));
        utente.setNome(rs.getString("name"));
        utente.setCognome(rs.getString("cognome"));
        utente.setMail(rs.getString("mail"));
        utente.setPassword(rs.getString("password"));
        return utente;
    };

    public long updateUtente(Utente utente){
        Map<String, Object> params = new HashMap<>();
        params.put("nome", utente.getNome());
        params.put("cognome", utente.getCognome());
        params.put("mail", utente.getMail());
        params.put("password", utente.getPassword());
        params.put("id_utenti", utente.getId());
        return executeUpdate(UPDATE_UTENTE, params);
    }

    public long deleteUtente(long idUtente){
        Map<String, Object> params = new HashMap<>();
        params.put("id_utenti", idUtente);
        return executeUpdate(DELETE_UTENTE, params);
    }


}
