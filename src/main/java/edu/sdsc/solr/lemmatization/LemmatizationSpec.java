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

    /***
     * @param languages The languages to include during the transformation
     * @return
     */
    public Builder languages(Collection<String> languages) {
      this.languages = ImmutableSet.copyOf(languages); return this;
    }

    /***
     * @param verbs If verb forms should be included
     * @return
     */
    public Builder includeVerbs(boolean verbs) {
      this.includeVerbs = verbs; return this;
    }

    /***
     * @param nouns If noun forms should be included
     * @return
     */
    public Builder includeNouns(boolean nouns) {
      this.includeNouns = nouns; return this;
    }

    /***
     * @param variants If other variants (alternate spellings) should be included 
     * @return
     */
    public Builder includeVariants(boolean variants) {
      this.includeVariants = variants; return this;
    }

    /***
     * @param redownload If the wiktionary source should be forced to redownload
     * @return
     */
    public Builder redownload(boolean redownload) {
      this.redownload = redownload; return this;
    }

    /***
     * @param synonyms Extra synonyms to add to the dictionary based map. Useful for domain specific additions.
     * @return
     */
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