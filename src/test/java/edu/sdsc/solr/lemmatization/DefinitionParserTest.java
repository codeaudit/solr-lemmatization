package edu.sdsc.solr.lemmatization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefinitionParserTest {

  @Test
  public void testPlurals() {
    assertEquals("Plural should be found", 
        "hippocampus", DefinitionParser.getSynonym("# {{plural of|hippocampus}}").get());
  }

  @Test
  public void testAdjectives() {
    assertEquals("Adjectives should be found", 
        "hippocampus", DefinitionParser.getSynonym("# Pertaining to the [[hippocampus]].").get());
    assertEquals("Adjectives should be found", 
        "zoosporangia", DefinitionParser.getSynonym("# pertaining to [[zoosporangia]].").get());
    assertEquals("Adjectives should be found", 
        "zoosporangia", DefinitionParser.getSynonym("# of or pertaining to [[zoosporangia]].").get());
  }

  @Test
  public void testVerbs() {
    assertEquals("Verbs should be found", 
        "run", DefinitionParser.getSynonym("# {{en-simple past of|run}}").get());
    assertEquals("Verbs should be found", 
        "run", DefinitionParser.getSynonym("# {{present participle of|run}}").get());
    assertEquals("Verbs should be found", 
        "ruralise", DefinitionParser.getSynonym("# {{en-past of|ruralise}}").get());
    assertEquals("Verbs should be found", 
        "operate", DefinitionParser.getSynonym("# {{en-third-person singular of|operate}}").get());
    assertEquals("Language should be parser out", 
        "frist", DefinitionParser.getSynonym("# {{present participle of|frist|lang=en}}").get());
  }

  @Test
  public void testAlternateForms() {
    assertEquals("Alternate forms should be found", 
        "ophiuroid", DefinitionParser.getSynonym("# {{alternative form of|ophiuroid}}").get());
  }

}
