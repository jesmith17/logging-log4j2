/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.plugins.Namespace;
import org.apache.logging.log4j.plugins.Plugin;
import org.apache.logging.log4j.util3.PerformanceSensitive;
import org.apache.logging.log4j.util3.StringBuilders;

import java.util.List;

/**
 * Equals ignore case pattern converter.
 */
@Namespace(PatternConverter.CATEGORY)
@Plugin("equalsIgnoreCase")
@ConverterKeys({ "equalsIgnoreCase" })
@PerformanceSensitive("allocation")
public final class EqualsIgnoreCaseReplacementConverter extends EqualsBaseReplacementConverter {

    /**
     * Gets an instance of the class.
     *
     * @param config
     *            The current Configuration.
     * @param options
     *            pattern options, an array of three elements: pattern, testString, and substitution.
     * @return instance of class.
     */
    public static EqualsIgnoreCaseReplacementConverter newInstance(final Configuration config, final String[] options) {
        if (options.length != 3) {
            LOGGER.error("Incorrect number of options on equalsIgnoreCase. Expected 3 received " + options.length);
            return null;
        }
        if (options[0] == null) {
            LOGGER.error("No pattern supplied on equalsIgnoreCase");
            return null;
        }
        if (options[1] == null) {
            LOGGER.error("No test string supplied on equalsIgnoreCase");
            return null;
        }
        if (options[2] == null) {
            LOGGER.error("No substitution supplied on equalsIgnoreCase");
            return null;
        }
        final String p = options[1];
        final PatternParser parser = PatternLayout.createPatternParser(config);
        final List<PatternFormatter> formatters = parser.parse(options[0]);
        return new EqualsIgnoreCaseReplacementConverter(formatters, p, options[2], parser);
    }

    /**
     * Construct the converter.
     *
     * @param formatters   The PatternFormatters to generate the text to manipulate.
     * @param testString   The test string.
     * @param substitution The substitution string.
     * @param parser        The PatternParser.
     */
    private EqualsIgnoreCaseReplacementConverter(final List<PatternFormatter> formatters, final String testString,
            final String substitution, final PatternParser parser) {
        super("equalsIgnoreCase", "equalsIgnoreCase", formatters, testString, substitution, parser);
    }

    @Override
    protected boolean equals(final String str, final StringBuilder buff, final int from, final int len) {
        return StringBuilders.equalsIgnoreCase(str, 0, str.length(), buff, from, len);
    }
}
