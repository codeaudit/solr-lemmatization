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
    LemmatizationSpec spec = new LemmatizationSpec.Builder(dictionaryUrl, new File(FileUtils.getTempDirectory(), "lemmatization.txt")).build();
    LemmatizationWriter writer = new LemmatizationWriter(spec);
    writer.writeLemmatization();
  }

}
