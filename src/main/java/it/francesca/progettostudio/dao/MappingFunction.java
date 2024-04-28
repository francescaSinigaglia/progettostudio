package it.francesca.progettostudio.dao;

import java.sql.ResultSet;

public interface MappingFunction<OUT> {
    OUT apply(ResultSet resulset) throws Exception;
}
