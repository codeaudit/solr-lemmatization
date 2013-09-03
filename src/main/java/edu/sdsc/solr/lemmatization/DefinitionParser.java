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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;

class DefinitionParser {

  static final Pattern pluralPattern = 
      Pattern.compile("# \\{\\{plural of\\|(.*)\\}\\.?\\}", Pattern.CASE_INSENSITIVE);
  static final Pattern adjectivePattern = 
      Pattern.compile("# (of or )?pertaining to (the )?\\[\\[(.*)\\]\\]\\.?", Pattern.CASE_INSENSITIVE);
  static final Pattern verbPattern = 
      Pattern.compile("# \\{\\{(en-third-person singular )?(en-simple past )?(en-past )?(present participle )?of\\|(.*)\\}\\}", Pattern.CASE_INSENSITIVE);
  static final Pattern adverbPattern = 
      Pattern.compile("# With regard to the \\[\\[(.*)\\]\\]\\.?", Pattern.CASE_INSENSITIVE);
  static final Pattern alternativePattern = 
      Pattern.compile("# \\{\\{alternative form of\\|(.*)\\}\\}", Pattern.CASE_INSENSITIVE);

  static Optional<String> getSynonym(String definition, LemmatizationSpec spec) {
    if (spec.isIncludeNouns()) {
      Optional<String> plural = checkPattern(pluralPattern, definition);
      if (plural.isPresent()) {
        return plural;
      }
      Optional<String> adjective = checkPattern(adjectivePattern, definition);
      if (adjective.isPresent()) {
        return adjective;
      }
    }
    if (spec.isIncludeVerbs()) {
      Optional<String> verb = checkPattern(verbPattern, definition);
      if (verb.isPresent()) {
        return verb;
      }
      Optional<String> adverb = checkPattern(adverbPattern, definition);
      if (adverb.isPresent()) {
        return adverb;
      }
    }
    if (spec.isIncludeVariants()) {
      Optional<String> alternative = checkPattern(alternativePattern, definition);
      if (alternative.isPresent()) {
        return alternative;
      }
    }

    return Optional.absent();
  }

  static Optional<String> checkPattern(Pattern pattern, String definition) {
    Matcher m = pattern.matcher(definition);
    if (m.find()) {
      String synonym = m.group(m.groupCount());
      if (synonym.contains("|")) {
        synonym = synonym.substring(0, synonym.indexOf("|"));
      }
      if (synonym.contains("]]")) {
        synonym = synonym.substring(0, synonym.indexOf("]]"));
      }
      if (synonym.contains("}}")) {
        synonym = synonym.substring(0, synonym.indexOf("}}"));
      }
      if (synonym.startsWith("wikipedia:")) {
        synonym = synonym.substring("wikipedia:".length());
      }

      return Optional.of(synonym.trim());
    }
    return Optional.absent();
  }

}
