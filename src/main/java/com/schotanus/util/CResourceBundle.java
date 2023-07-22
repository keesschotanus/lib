/*
 * Copyright (C) 2002 Kees Schotanus
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.schotanus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * Utility resource bundle class.
 * <br>In addition to Oracle's {@link ResourceBundle} class this class adds the capability of searching multiple resource
 * bundle files, in order, transparently.
 * This allows you to store common resources and specific resources in separate bundles without adding complexity to code,
 * to retrieve resources.<br>
 * Imagine writing a Java Swing application or a JSP of JSF application.
 * You probably want a properties file containing all the common messages for your application.
 * Plus an additional one for all GUI related messages, like 'Ok' or 'Cancel'.
 * For each module in your system, you may want to use a separate properties file.
 * Each page probably also deserves its own properties file.<br>
 * In this case you always have to figure out which resource bundle to use.
 * Using this CResourceBundle class, you would add your resource bundles from generic to specific and then use these bundles
 * as if they were one bundle.<br><br>
 * The alternative that comes closest is to put all your properties in a single properties file, but that is hard to maintain
 * and does not allow you to override property values (translations) as this class does.<br><br>
 * Another perk of this resource bundle class is that is allows you to get properties of a certain type.
 * For example: <code>var numberOfDoors = bundle.getInteger("key")</code>
 * @author Kees Schotanus
 * @version 1.1 $Revision: 1.4 $
 */
public class CResourceBundle {

    /**
     * Locale to be used for all resource bundles that are part of this bundle.
     */
    private final Locale locale;

    private record ResourceBundleRecord(String resourceBundleName, ResourceBundle bundle) {
    }

    /**
     * List of all the resource bundles that are part of this bundle.
     * <br>The first record added is the last to search for.
     */
    private final List<ResourceBundleRecord> resourceBundles = new ArrayList<>();

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CResourceBundle.class);

    /**
     * Constructs this bundle using the supplied resourceBundleName and the default locale.
     * <br>All subsequent bundles added using {@link #addResourceBundle(String)} will use the same (default) locale.
     * @param resourceBundleName Name of the resource bundle file.
     *  Specifying "com.schotanus.util.UtilMessages" would use the file "com/schotanus/util/UtilMessages.properties".
     * @throws MissingResourceException When the resource bundle could not be located.
     * @throws NullPointerException When the supplied resourceBundleName is null.
     * @throws IllegalArgumentException When the supplied resourceBundleName is empty.
     */
    public CResourceBundle(final String resourceBundleName)
    {
        this(resourceBundleName, Locale.getDefault());
    }

    /**
     * Constructs this bundle using the supplied resourceBundleName and the supplied locale.
     * <br>All subsequent bundles added using {@link #addResourceBundle(String)} will use the same locale.
     * @param resourceBundleName Name of the resource bundle file.
     *  Specifying "com.schotanus.util.UtilMessages" would use the file "com/schotanus/util/UtilMessages.properties".
     * @param locale Locale used to translate messages.
     * @throws MissingResourceException When the resource bundle could not be located.
     * @throws NullPointerException When either the supplied resourceBundleName or locale is null.
     */
    public CResourceBundle(final String resourceBundleName, final Locale locale) {
        Objects.requireNonNull(resourceBundleName);
        this.locale = Objects.requireNonNull(locale);

        final ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale);
        resourceBundles.add(new ResourceBundleRecord(resourceBundleName, resourceBundle));
    }

    /**
     * Adds a resource bundle file to the list of resource bundles.
     * @param resourceBundleName Name of the resource bundle.
     * @throws MissingResourceException When the resource bundle could not be located.
     * @throws NullPointerException When the supplied resourceBundleName is null.
     */
    public void addResourceBundle(final String resourceBundleName) {
        Objects.requireNonNull(resourceBundleName);

        final ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale);
        resourceBundles.add(new ResourceBundleRecord(resourceBundleName, resourceBundle));
    }

    /**
     * Gets a Boolean resource from the list of resource bundles.
     * @param key Key to the Boolean resource.
     * @return An Optional of type Boolean.
     *  When no value is present the key was not found.
     *  When the value is present it will be {@link Boolean#TRUE} when the value was "true" (ignoring case).
     *  In all other cases {@link Boolean#FALSE} is returned.
     * @throws NullPointerException When the supplied key is null.
     */
    public Optional<Boolean> getBoolean(final String key) {
        final String stringResource = getString(Objects.requireNonNull(key));

        return stringResource == null ? Optional.empty() : Optional.of(Boolean.parseBoolean(stringResource));
    }

    /**
     * Gets a required Boolean resource from the list of resource bundles.
     * @param key Key to the Boolean resource.
     * @return {@link Boolean#TRUE}  when the value was "true" (ignoring case), otherwise {@link Boolean#FALSE} is returned.
     * @throws MissingResourceException When the key does not exist.
     * @throws NullPointerException When the supplied key is null.
     */
    public Boolean getRequiredBoolean(final String key) {
        Objects.requireNonNull(key);

        return Boolean.valueOf(getRequiredString(key));
    }

    /**
     * Gets an Integer resource from the list of resource bundles.
     * @param key Key to the Integer resource.
     * @return An Optional of type Integer, containing the Integer value,
     *  or {@link Optional#empty()} if the key was not found.
     * @throws NumberFormatException When the key was present but the value was not a valid integer.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
     public Optional<Integer> getInteger(final String key) {
         Objects.requireNonNull(key);

         Optional<Integer> result = Optional.empty();
         final String stringResource = getString(key);
         if (stringResource != null) {
             try {
                 final Integer value = Integer.valueOf(stringResource);
                 result = Optional.of(value);
             } catch (final NumberFormatException exception) {
                 logger.atWarn().log("key: {}, value: {}, is not an integer.", key, stringResource);
                 throw exception;
             }
        }
        return result;
    }

    /**
     * Gets a required Integer resource from the list of resource bundles.
     * @param key Key to the Integer resource.
     * @return Integer value of the String resource.
     * @throws MissingResourceException When the key does not exist.
     * @throws NumberFormatException When the key exists, but the value is not an integer.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
    public Integer getRequiredInteger(final String key) throws MissingResourceException {
        Objects.requireNonNull(key);

        final String stringResource = getRequiredString(key);
        try {
            return Integer.valueOf(stringResource);
        } catch (final NumberFormatException exception) {
            logger.atWarn().log("key: {}, value: {}, is not an integer.", key, stringResource);
            throw exception;
        }
    }

    /**
     * Gets a Long resource from the list of resource bundles.
     * @param key Key to the Long resource.
     * @return An Optional of type Long, containing the Long value,
     *  or {@link Optional#empty()} if the key was not found.
     * @throws NumberFormatException When the key was present but the value was not a valid long.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
    public Optional<Long> getLong(final String key) {
        Objects.requireNonNull(key);

        Optional<Long> result = Optional.empty();
        final String stringResource = getString(key);
        if (stringResource != null) {
            try {
                final Long value = Long.valueOf(stringResource);
                result = Optional.of(value);
            } catch (final NumberFormatException exception) {
                logger.atWarn().log("key: {}, value: {}, is not a long value.", key, stringResource);
                throw exception;
            }
        }
        return result;
    }

    /**
     * Gets a required Long resource from the list of resource bundles.
     * @param key Key to the Long resource.
     * @return Long value of the String resource.
     * @throws MissingResourceException When the key does not exist.
     * @throws NumberFormatException When the key exists, but the value is not a long value.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
    public Long getRequiredLong(final String key) throws MissingResourceException {
        Objects.requireNonNull(key);

        final String stringResource = getRequiredString(key);
        try {
            return Long.valueOf(stringResource);
        } catch (final NumberFormatException exception) {
            logger.atWarn().log("key: {}, value: {}, is not a long value.", key, stringResource);
            throw exception;
        }
    }

    /**
     * Gets a Double resource from the list of resource bundles.
     * @param key Key to the Double resource.
     * @return An Optional of type Double, containing the Double value,
     *  or {@link Optional#empty()} if the key was not found.
     * @throws NumberFormatException When the key was present but the value was not a valid double.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
    public Optional<Double> getDouble(final String key) {
        Objects.requireNonNull(key);

        Optional<Double> result = Optional.empty();
        final String stringResource = getString(key);
        if (stringResource != null) {
            try {
                final Double value = Double.valueOf(stringResource);
                result = Optional.of(value);
            } catch (final NumberFormatException exception) {
                logger.atWarn().log("key: {}, value: {}, is not a double value.", key, stringResource);
                throw exception;
            }
        }
        return result;
    }

    /**
     * Gets a required Double resource from the list of resource bundles.
     * @param key Key to the Double resource.
     * @return Double value of the String resource.
     * @throws MissingResourceException When the key does not exist.
     * @throws NumberFormatException When the key exists, but the value is not a double value.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
    public Double getRequiredDouble(final String key) throws MissingResourceException {
        Objects.requireNonNull(key);

        final String stringResource = getRequiredString(key);
        try {
            return Double.valueOf(stringResource);
        } catch (final NumberFormatException exception) {
            logger.atWarn().log("key: {}, value: {}, is not a double value.", key, stringResource);
            throw exception;
        }
    }

    /**
     * Gets a String resource from the list of resource bundles.
     * <br>This is the only method that directly accesses the resource bundles.
     * @param key Key to the String resource.
     * @return String resource or null when the resource could not be located.
     * @throws NullPointerException When the supplied key is null.
     */
    public String getString(final String key) {
        Objects.requireNonNull(key);

        // Search from bottom to top so last resource bundle added is searched first
        for (int i = resourceBundles.size() - 1; i >= 0; --i) {
            try {
                final ResourceBundleRecord resourceBundleRecord = resourceBundles.get(i);
                return resourceBundleRecord.bundle().getString(key);
            } catch (final MissingResourceException ignore) {
                // Just try another bundle
            }
        }

        return null;
    }

    /**
     * Gets a String resource from the list of resource bundles and formats it with the supplied arguments.
     * @param key Key to the String resource.
     * @param arguments Message arguments that will be substituted in the retrieved string resource.
     * @return String resource or null when the resource could not be located.
     * @throws NullPointerException When either the supplied key is null or supplied arguments is null.
     */
    public String getString(final String key, final Object... arguments) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(arguments);

        String result = getString(key);
        if (result != null) {
            result = format(result, arguments);
        }

        return result;
    }

    /**
     * Gets a required String resource from the list of resource bundles and formats it with the supplied arguments.
     * @param key Key to the String resource.
     * @return String resource.
     * @throws MissingResourceException When the key does not exist.
     *  In this case a warning will be logged.
     * @throws NullPointerException When the supplied key is null.
     */
    public String getRequiredString(final String key) {
        final String result = getString(Objects.requireNonNull(key));
        if (result == null) {
            final String message = String.format("key: %s, does not exist.", key);
            logger.atWarn().log(message);
            throw new MissingResourceException(message, getClass().getName(), key);
        }

        return result;
    }

    /**
     * Gets a required String resource from the list of resource bundles and formats it with the supplied arguments.
     * @param key Key to the String resource.
     * @param arguments Message arguments that will be substituted in the retrieved string resource.
     * @return String resource.
     * @throws MissingResourceException When the key does not exist.
     *  In this case a warning will be logged.
     * @throws NullPointerException When either the supplied key is null or supplied arguments is null.
     */
    public String getRequiredString(final String key, final Object... arguments) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(arguments);

        final String result = getString(key, arguments);
        if (result == null) {
            final String message = String.format("key: %s, does not exist.", key);
            logger.atWarn().log(message);
            throw new MissingResourceException(message, getClass().getName(), key);
        }

        return result;
    }

    /**
     * Constructs a compound message based on the message pattern and the supplied arguments.
     * @param messagePattern The message pattern.
     *  <br>For example: Processing record {0} of {1}.
     * @param messageArguments The arguments that must be inserted into the message pattern.
     * @return The localized compound message or the supplied messagePattern when the messageArguments are null.
     * @throws NullPointerException When the supplied messagePattern is null.
     */
    public String format(final String messagePattern, final Object... messageArguments) {
        Objects.requireNonNull(messagePattern);

        String result = messagePattern;

        /* Not passing any arguments can be performed in two distinct ways
         * 1) Passing <code>null</code> or <code>(Object []) null</code>, causes messageArguments to be null.
         * 2) Passing <code>(Object) null)</code>, causes messageArguments to be an array with a single null value
         */
        if (messageArguments != null && !(messageArguments.length == 1 && messageArguments[0] == null)) {
            final MessageFormat messageFormat = new MessageFormat(messagePattern);
            messageFormat.setLocale(this.locale);
            result = messageFormat.format(messageArguments);
        }

        return result;
    }

    /**
     * Creates a String representation of this bundle of resource bundles.
     * @return String representation of this bundle of resource bundles.
     */
    public String toString() {
        return resourceBundles.stream()
            .map(resourceBundleRecord -> resourceBundleRecord.resourceBundleName)
            .collect(Collectors.joining(",", "[", "]"));
    }

 }
