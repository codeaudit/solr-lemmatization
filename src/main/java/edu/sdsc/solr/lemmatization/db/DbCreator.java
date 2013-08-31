package edu.sdsc.solr.lemmatization.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class DbCreator {

  private final File input;
  private final File dbPath;

  private static final String DEFS_PATH = "http://toolserver.org/~enwikt/definitions/enwikt-defs-latest-all.tsv.gz";
  private final URL url;

  DbCreator() throws MalformedURLException {
    System.setProperty("textdb.allow_full_path", "true");
    input = new File(FileUtils.getTempDirectory(), "wiktionarydump.tsv");
    dbPath = new File(FileUtils.getTempDirectory(), "wikidb");
    dbPath.mkdir();
    url = new URL(DEFS_PATH);
  }

  void downloadDictionary() throws IOException {
    try (InputStream is = new GZIPInputStream(url.openConnection().getInputStream())) {
      FileUtils.copyInputStreamToFile(is, input);
    }
  }

  public void buildDb() throws SQLException, IOException {
    downloadDictionary();
    try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath.getAbsolutePath(), "sa", "");
        Handle h = DBI.open(connection)) {
      h.execute("DROP TABLE if exists definitions");
      h.execute("CREATE TEXT TABLE IF NOT EXISTS definitions" +
          "(language VARCHAR(2048), term VARCHAR(2048), pos VARCHAR(64), definition VARCHAR(2048))");
      h.execute("CREATE INDEX termIndex ON definitions(term)");
      h.execute("CREATE INDEX definitionsIndex ON definitions(definition)");
      h.execute("CREATE INDEX posIndex ON definitions(pos)");
      h.execute("SET TABLE definitions SOURCE \"" + input.getAbsolutePath() + ";fs=\\t;quoted=false\"");
    }
  }
  
  public Handle getConnection() throws SQLException {
    return DBI.open(DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath.getAbsolutePath(), "sa", ""));
  }

}
