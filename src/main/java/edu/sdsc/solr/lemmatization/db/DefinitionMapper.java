package edu.sdsc.solr.lemmatization.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;


public class DefinitionMapper implements ResultSetMapper<Definition>{

  @Override
  public Definition map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
    return new Definition(rs.getString("term"), rs.getString("pos"), rs.getString("definition"), rs.getString("language"));
  }

}
