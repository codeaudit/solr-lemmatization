package edu.sdsc.solr.lemmatization;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Multimap;
import com.google.common.io.Resources;

public class LemmatizationWriterTest {

  LemmatizationSpec.Builder builder;

  @Before
  public void setUp() throws Exception {
    builder = new LemmatizationSpec.Builder(null, null);
  }

  @Test
  public void testHippocampus() throws Exception {
    LemmatizationWriter writer = new LemmatizationWriter(builder.build());
    writer.setUncompressedDictionary(new File(Resources.getResource("test.tsv").toURI()));
    Multimap<String, String> syns = writer.buildSynonymMap();
    assertEquals("Should be only one entry", 1, syns.asMap().size());
    assertEquals("Should have the correct lemma", newHashSet("hippocampi", "hippocampally", "hippocampal"), syns.get("hippocampus"));
  }

  @Test
  public void testLexicalVariants() throws Exception {
    LemmatizationWriter writer = new LemmatizationWriter(builder.includeNouns(false).build());
    writer.setUncompressedDictionary(new File(Resources.getResource("test.tsv").toURI()));
    Multimap<String, String> syns = writer.buildSynonymMap();
    assertEquals("Should have the correct lemma", newHashSet("hippocampally"), syns.get("hippocampus"));
  }

  @Test
  public void testYiddish() throws Exception {
    LemmatizationWriter writer = new LemmatizationWriter(builder.languages(newHashSet("Yiddish")).build());
    writer.setUncompressedDictionary(new File(Resources.getResource("test.tsv").toURI()));
    Multimap<String, String> syns = writer.buildSynonymMap();
    assertEquals("Should be only one entry", 1, syns.asMap().size());
  }

}
