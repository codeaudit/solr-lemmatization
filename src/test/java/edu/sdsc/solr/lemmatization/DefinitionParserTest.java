package edu.sdsc.solr.lemmatization;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DefinitionParserTest {

  LemmatizationSpec spec;
  
  @Before
  public void setup() {
    spec = new LemmatizationSpec.Builder(null, null).build();
  }
  
  @Test
  public void testPlurals() {
    assertEquals("Plural should be found", 
        "hippocampus", DefinitionParser.getSynonym("# {{plural of|hippocampus}}", spec).get());
  }

  @Test
  public void testAdjectives() {
    assertEquals("Adjectives should be found", 
        "hippocampus", DefinitionParser.getSynonym("# Pertaining to the [[hippocampus]].", spec).get());
    assertEquals("Adjectives should be found", 
        "zoosporangia", DefinitionParser.getSynonym("# pertaining to [[zoosporangia]].", spec).get());
    assertEquals("Adjectives should be found", 
        "zoosporangia", DefinitionParser.getSynonym("# of or pertaining to [[zoosporangia]].", spec).get());
  }

  @Test
  public void testVerbs() {
    assertEquals("Verbs should be found", 
        "run", DefinitionParser.getSynonym("# {{en-simple past of|run}}", spec).get());
    assertEquals("Verbs should be found", 
        "run", DefinitionParser.getSynonym("# {{present participle of|run}}", spec).get());
    assertEquals("Verbs should be found", 
        "ruralise", DefinitionParser.getSynonym("# {{en-past of|ruralise}}", spec).get());
    assertEquals("Verbs should be found", 
        "operate", DefinitionParser.getSynonym("# {{en-third-person singular of|operate}}", spec).get());
    assertEquals("Language should be parser out", 
        "frist", DefinitionParser.getSynonym("# {{present participle of|frist|lang=en}}", spec).get());
  }

  @Test
  public void testAlternateForms() {
    assertEquals("Alternate forms should be found", 
        "ophiuroid", DefinitionParser.getSynonym("# {{alternative form of|ophiuroid}}", spec).get());
  }

  @Test
  public void testUtf() {
    assertEquals("UTF variants should be found", 
        "שרעק", DefinitionParser.getSynonym("# {{plural of|שרעק|lang=yi}}", spec).get());
    
  }
  
}
