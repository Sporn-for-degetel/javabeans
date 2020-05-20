/**
 *
 */
package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.strings.StringComparators.STRING_COMPARATOR_REVERSE;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Utility package for class names stored in a String.
 *
 * <p>
 * &copy; Copyright 2012-2020 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 20.05.01
 * @since 17.09.00
 */
public final class UtilsClassname
{
	/**
	 * Output the class name.
	 *
	 * @param classToOutput
	 *            the fully qualified class name to output.
	 * @param translations
	 *            a translation map to convert a registered class name into another class name.
	 * @param shortables
	 *            the registry of fully qualified class names of imported classes.
	 * @return the class simple name if in shortables
	 */
	public static String computeOutputClassname(String classToOutput, Map<String, String> translations, Set<String> shortables)
	{
		final String _translatedName = (translations.containsKey(classToOutput)) ? translations.get(classToOutput) : classToOutput;
		return computeOutputClassname(_translatedName, shortables);
	}

	/**
	 * Output the class name.
	 *
	 * @param classToOutput
	 *            the fully qualified class name to output.
	 * @param shortables
	 *            the registry of fully qualified class names of imported classes.
	 * @return the class simple name if in shortables
	 */
	public static String computeOutputClassname(String classToOutput, Set<String> shortables)
	{
		return (shortables.contains(classToOutput)) ? getSimpleName(classToOutput) : classToOutput;
	}

	/**
	 * Extract the package name from a fully qualified class name.
	 *
	 * @param fullClassName
	 *            the fully qualified class name.
	 * @return the fully qualified package name of the class.
	 */
	public static String getPackageName(String fullClassName)
	{
		final int _lastIndexOfDot = fullClassName.lastIndexOf('.');
		return fullClassName.substring(0, (_lastIndexOfDot < 0) ? 0 : _lastIndexOfDot);
	}

	/**
	 * Create a translation map from javabean qualified class names to pojo qualified class names where pojos class names are
	 * recognised by their suffix.
	 *
	 * @param registry
	 *            a list of fully qualified class names, pojos are inside the list.
	 * @param sourcePackages
	 *            a registry of fully qualified package names, class names outside of theses packages are ignored.
	 * @param pojoSuffix
	 *            the suffix that pojo class names MUST have, excluding class that have the suffix for name.
	 * @param builderSuffix
	 *            the suffix that builder class names MUST have, excluding class that have the suffix for name.
	 * @return the translation map.
	 */
	public static Map<String, String> getReverseTranslationMapWhenPojosAreSuffixed(Set<String> registry, Set<String> sourcePackages,
			String pojoSuffix, String builderSuffix)
	{
		// use a map with keys sorted in reverse order, so that looping on keys to translate something do not overlap.
		final Map<String, String> result = new TreeMap<>(STRING_COMPARATOR_REVERSE);
		final Predicate<String> _isPojo = c -> sourcePackages.contains(getPackageName(c)) && !pojoSuffix.equals(getSimpleName(c))
				&& c.endsWith(pojoSuffix);
		final Predicate<String> _isBuilder = c -> sourcePackages.contains(getPackageName(c))
				&& !builderSuffix.equals(getSimpleName(c)) && c.endsWith(builderSuffix);

		registry.stream().filter(_isBuilder.negate()).filter(_isPojo.negate()).forEach(c -> {
			result.put(c, c + pojoSuffix);
		});

		return result;
	}

	/**
	 * Extract the class simple name from a fully qualified class name.
	 *
	 * @param fullClassName
	 *            the fully qualified class name.
	 * @return the class simple name.
	 */
	public static String getSimpleName(String fullClassName)
	{
		return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
	}

	/**
	 * Create a translation map from pojo qualified class names to javabean qualified class names where pojos class names are
	 * recognised by their suffix.
	 *
	 * @param registry
	 *            a list of fully qualified class names, pojos are inside the list.
	 * @param sourcePackages
	 *            a registry of fully qualified package names, class names outside of theses packages are ignored. If
	 *            <code>null</code> or empty, there is no filtering.
	 * @param pojoSuffix
	 *            the suffix that pojo class names MUST have, excluding class that have the suffix for name.
	 * @return the translation map.
	 */
	public static Map<String, String> getTranslationMapWhenPojosAreSuffixed(Set<String> registry, Set<String> sourcePackages,
			String pojoSuffix)
	{
		// use a map with keys sorted in reverse order, so that looping on keys to translate something do not overlap.
		final Map<String, String> result = new TreeMap<>(STRING_COMPARATOR_REVERSE);

		final boolean _noPackageFiltering = (null == sourcePackages) || sourcePackages.isEmpty();
		final Predicate<String> _isInPackageList = c -> _noPackageFiltering || sourcePackages.contains(getPackageName(c));

		final Predicate<String> _isPojo = c -> !pojoSuffix.equals(getSimpleName(c)) && c.endsWith(pojoSuffix);

		registry.stream()//
				.filter(_isInPackageList)//
				.filter(_isPojo)//
				.forEach(c -> {
					result.put(c, removeSuffixFromClassName(c, pojoSuffix));
				});

		return result;
	}

	/**
	 * Remove the given suffix from the given class name.
	 *
	 * @param name
	 *            the class name.
	 * @param suffix
	 *            the suffix to remove.
	 * @return the result if the input are corrects.
	 */
	public static String removeSuffixFromClassName(String name, String suffix)
	{
		return name.substring(0, name.length() - suffix.length());
	}

	/**
	 * Update a mapping of fully qualified class names that may be adressed using their simple name.
	 *
	 * @param mapping
	 *            the mapping to update.
	 * @param registry
	 *            a registry of candidate fully qualified class names to add into the mapping.
	 */
	public static void updateShortClassnameMappingFromClassnames(Map<String, String> mapping, Collection<String> registry)
	{
		registry.forEach(c -> {
			final String _simpleName = getSimpleName(c);
			if (!mapping.containsKey(_simpleName))
			{
				mapping.put(_simpleName, c);

			}
		});
	}

}
