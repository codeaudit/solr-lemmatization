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
