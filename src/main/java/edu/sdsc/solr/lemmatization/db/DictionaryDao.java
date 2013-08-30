package edu.sdsc.solr.lemmatization.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


@RegisterMapper(DefinitionMapper.class)
public interface DictionaryDao {

  @SqlQuery("select * from definitions where term = :term")
  List<Definition> getDefinition(@Bind("term") String term);

  @SqlQuery("select count(*) from definitions")
  int getCount();

  @SqlQuery("select distinct(language) from definitions")
  List<String> getLanguages();

}
