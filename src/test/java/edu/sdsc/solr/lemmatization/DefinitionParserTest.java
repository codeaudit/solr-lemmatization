/**
 * Copyright (C) 2013 Christopher Condit (condit@sdsc.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
