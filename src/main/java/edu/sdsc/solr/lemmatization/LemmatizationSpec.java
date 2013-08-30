package edu.sdsc.solr.lemmatization;

import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

class LemmatizationSpec {
  private final URL dictionaryUrl;
  private final File targetFile;
  private final Collection<String> languages;
  private final boolean includeVerbs;
  private final boolean includeNouns;
  private final boolean includeVariants;
  private final boolean redownload;
  private final Multimap<String, String> extraSynonyms;

  private LemmatizationSpec(Builder builder) {
    this.dictionaryUrl = builder.dictionaryUrl;
    this.targetFile = builder.targetFile;
    this.languages = builder.languages;
    this.includeVerbs = builder.includeVerbs;
    this.includeNouns = builder.includeNouns;
    this.includeVariants = builder.includeVariants;
    this.redownload = builder.redownload;
    this.extraSynonyms = builder.extraSynonyms;
  }

  public static class Builder {
    private final URL dictionaryUrl;
    private final File targetFile;

    private Collection<String> languages = newHashSet("English");
    private boolean includeVerbs = true;
    private boolean includeNouns = true;
    private boolean includeVariants = true;
    private boolean redownload = false;
    private Multimap<String, String> extraSynonyms = HashMultimap.create();

    public Builder(URL dictionaryUrl, File targetFile) {
      this.dictionaryUrl = dictionaryUrl;
      this.targetFile = targetFile;
    }

    public Builder languages(Collection<String> languages) {
      this.languages = ImmutableSet.copyOf(languages); return this;
    }

    public Builder includeVerbs(boolean verbs) {
      this.includeVerbs = verbs; return this;
    }

    public Builder includeNouns(boolean nouns) {
      this.includeNouns = nouns; return this;
    }

    public Builder includeVariants(boolean variants) {
      this.includeVariants = variants; return this;
    }

    public Builder redownload(boolean redownload) {
      this.redownload = redownload; return this;
    }

    public Builder extraSynonyms(Multimap<String, String> synonyms) {
      this.extraSynonyms = ImmutableListMultimap.copyOf(synonyms); return this;
    }

    public LemmatizationSpec build() {
      return new LemmatizationSpec(this);
    }
  }

  public URL getDictionaryUrl() {
    return dictionaryUrl;
  }

  public File getTargetFile() {
    return targetFile;
  }

  public Collection<String> getLanguages() {
    return languages;
  }

  public boolean isIncludeVerbs() {
    return includeVerbs;
  }

  public boolean isIncludeNouns() {
    return includeNouns;
  }

  public boolean isIncludeVariants() {
    return includeVariants;
  }

  public boolean isRedownload() {
    return redownload;
  }

  public Multimap<String, String> getExtraSynonyms() {
    return extraSynonyms;
  }

}