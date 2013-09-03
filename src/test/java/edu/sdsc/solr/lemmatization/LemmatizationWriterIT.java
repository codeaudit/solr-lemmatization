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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class LemmatizationWriterIT {

  @Test
  public void testWriteLemmatization() throws IOException {
    URL dictionaryUrl = new URL("http://toolserver.org/~enwikt/definitions/enwikt-defs-latest-en.tsv.gz");
    File lemmaFile = new File(FileUtils.getTempDirectory(), "lemmatization.txt");
    LemmatizationSpec spec = new LemmatizationSpec.Builder(dictionaryUrl, lemmaFile).redownload(true).build();
    LemmatizationWriter writer = new LemmatizationWriter(spec);
    writer.writeLemmatization();
  }

}
