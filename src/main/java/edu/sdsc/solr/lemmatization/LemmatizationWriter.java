package edu.sdsc.solr.lemmatization;

import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class LemmatizationWriter {

  private final File uncompressedDictionary;
  private final Multimap<String, String> synonyms = HashMultimap.create();
  private final LemmatizationSpec spec;

  public LemmatizationWriter(LemmatizationSpec spec) {
    uncompressedDictionary = new File(FileUtils.getTempDirectory(), "wiktionary.tsv");
    this.spec = spec;
  }

  public void writeLemmatization() throws IOException {
    if (spec.isRedownload() || !uncompressedDictionary.exists()) {
      downloadDictionary(spec.getDictionaryUrl(), uncompressedDictionary);
    }
    Files.readLines(uncompressedDictionary, Charsets.UTF_8, new LineProcessor<Void>() {

      @Override
      public boolean processLine(String line) throws IOException {
        List<String> cols = newArrayList(Splitter.on('\t').split(line));
        String language = cols.get(0);
        if (!spec.getLanguages().contains(language)) {
          return true;
        }
        Optional<String> synonym = DefinitionParser.getSynonym(cols.get(3), spec);
        if (synonym.isPresent()) {
          synonyms.put(synonym.get(), cols.get(1).trim());
        }
        return true;
      }

      @Override
      public Void getResult() { return null; }

    });


    try (OutputStreamWriter writer = 
        new OutputStreamWriter(new FileOutputStream(spec.getTargetFile()), Charset.forName("UTF-8").newEncoder())) {
      writeSynonyms(synonyms, writer);
      writer.flush();
    }
  }

  static void downloadDictionary(URL definitionUrl, File target) throws IOException {
    try (InputStream is = new GZIPInputStream(definitionUrl.openConnection().getInputStream())) {
      FileUtils.copyInputStreamToFile(is, target);
    }
  }

  static void writeSynonyms(Multimap<String, String> synonyms, Writer writer) throws IOException {
    for (Entry<String, Collection<String>> entry: synonyms.asMap().entrySet()) {
      entry.getValue().add(entry.getKey());
      writer.write(Joiner.on(", ").join(entry.getValue()) + "\n");
    }
  }

}
