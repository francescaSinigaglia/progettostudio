package it.francesca.progettostudio.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDao {

    private static final Pattern pattern = Pattern.compile(":\\w+");

    protected AbstractDao(){
        initializeDrivers();
    }

    private void initializeDrivers(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (Exception e){
            throw new RuntimeException("Error while loading drivers", e);
        }
    }
    private Connection getConnection(){
        try{
            Connection con = DriverManager.getConnection("url","utente","password");
            return con;
        } catch (Exception e){
            throw new RuntimeException("Error connection DB", e);
        }
    }

    public <OUT> List<OUT> executeQuery(String query, Map<String, Object> params, MappingFunction<OUT> mappingFunction){
        try (Connection connection = getConnection();
             PreparedStatement ps = prepareQuery(query, params, connection)){

            ResultSet resultSet = ps.executeQuery();
            List<OUT> results = new ArrayList<>();
            while (resultSet.next()){
                results.add(mappingFunction.apply(resultSet));
            }
            return results;
        } catch (Exception e){
            throw new RuntimeException("Error in executeQuery", e);
        }
    }
    public <OUT> List<OUT> executeQuery(String query, MappingFunction<OUT> mappingFunction){
        return executeQuery(query, null, mappingFunction);
    }

    public <OUT> OUT executeSingleResultQuery(String query, Map<String, Object> params, MappingFunction<OUT> mappingFunction){
        List<OUT> outs = executeQuery(query, params, mappingFunction);
        if(outs.isEmpty()){
            return  null;
        }
        if(outs.size() > 1){
            throw new RuntimeException("Query: " + query + " returns more values not only one");
        }
        return executeQuery(query, params, mappingFunction).get(0);

    }

    public <OUT> OUT executeSingleResultQuery(String query, MappingFunction<OUT> mappingFunction){
        return executeSingleResultQuery(query, null, mappingFunction);
    }

    public long executeUpdate(String query, Map<String, Object> params){
        try(Connection connection = getConnection();
            PreparedStatement ps = prepareQuery(query, params, connection)){

            long rows = ps.executeUpdate();
            return rows;

        } catch (Exception e){
            throw new RuntimeException("Error during executione query : " + query);
        }

    }

    private PreparedStatement prepareQuery(String query, Map<String, Object> params, Connection connection){
        try{
            if(params == null){
                return connection.prepareStatement(query);
            }
            Matcher matcher = pattern.matcher(query);
            Map<Integer, Object> paramMap = new HashMap<>();
            int posizione = 1;

            while(matcher.find()){
                query = query.replaceFirst(matcher.group(), "?");
                paramMap.put(posizione, params.get(matcher.group().replaceFirst(":", "")));
                posizione++;
            }

            PreparedStatement ps = connection.prepareStatement(query);
            paramMap.forEach((key, value) -> {
                try {
                    ps.setObject(key, value);
                } catch (SQLException e) {
                    throw new RuntimeException("Error setting value in PrepareStatement", e);
                }
            });
            return ps;
        } catch (Exception e){
            throw new RuntimeException("Error in prepareQuery", e);
        }

    }

    public long getIdFromSequence(String nameSequence){
        String query = "SELECT " + nameSequence + ".NEXTVAL FROM DUAL";
        return executeSingleResultQuery(query, SEQUENCE_MAPPER);
    }
    public static final MappingFunction<Long> SEQUENCE_MAPPER = rs -> {
        Long id = rs.getLong("NEXTVAL");
        return  id;
    };



}
