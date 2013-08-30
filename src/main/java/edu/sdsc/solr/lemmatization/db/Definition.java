package edu.sdsc.solr.lemmatization.db;

public class Definition {

  String term;
  String pos;
  String definition;
  String language;

  public Definition(String term, String pos, String definition, String language) {
    this.term = term;
    this.pos = pos;
    this.definition = definition;
    this.language = language;
  }

  public String getTerm() {
    return term;
  }

  public String getPos() {
    return pos;
  }

  public String getDefinition() {
    return definition;
  }

  public String getLanguage() {
    return language;
  }

  @Override
  public String toString() {
    return term;
  }

}
