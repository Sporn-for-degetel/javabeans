package com.sporniket.libre.javabeans.doclet;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Command line arguments supported by the javabeans doclets.
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
public class DocletOptions
{
	private static final String FORMAT_TO_STRING = "beanFieldPrefix={0}\nbuilderSuffix={1}\nd={2}\npojoSuffix={3}\naddAnnotationToGetter={4}\naddAnnotationToSetter={5}";

	private static final String KNOWN_ANNOTATIONS_FOR_GETTER = "java.lang.Deprecated,com.fasterxml.jackson.annotation.JsonProperty,com.fasterxml.jackson.annotation.JsonPropertyDescription";

	private static final String KNOWN_ANNOTATIONS_FOR_SETTER = "java.lang.Deprecated,com.fasterxml.jackson.annotation.JsonProperty,com.fasterxml.jackson.annotation.JsonPropertyDescription";

	/**
	 * List of annotation (fully qualified names separated with comma) that have to be put on the getter in addition to the field.
	 * 
	 * @since 20.04.00
	 */
	String addAnnotationToGetter;

	/**
	 * List of annotation (fully qualified name separated with comma) that have to be put on the setter in addition to the field.
	 * 
	 * @since 20.04.00
	 */
	String addAnnotationToSetter;

	/**
	 * Prefix used for the fields of the javabeans.
	 */
	String beanFieldPrefix = "my";

	/**
	 * Suffix of a generated Builder for a Javabean.
	 */
	String builderSuffix = "_Builder";

	/**
	 * Store the <code>-d</code> option value (target directory).
	 */
	String d;

	/**
	 * Suffix of a Pojo that should be expanded into a Javabean.
	 */
	String pojoSuffix = "Raw";

	/**
	 * Internal, set of annotations from {@link #addAnnotationToGetter}.
	 * 
	 * @since 20.04.00
	 */
	private Set<String> myAnnotationsToAddToGetters = null;

	/**
	 * Internal, set of annotations from {@link #addAnnotationToSetter}.
	 * 
	 * @since 20.04.00
	 */
	private Set<String> myAnnotationsToAddToSetters = null;

	public String getAddAnnotationToGetter()
	{
		return (null != addAnnotationToGetter)
				? KNOWN_ANNOTATIONS_FOR_GETTER + "," + addAnnotationToGetter
				: KNOWN_ANNOTATIONS_FOR_GETTER;
	}

	public String getAddAnnotationToSetter()
	{
		return (null != addAnnotationToSetter)
				? KNOWN_ANNOTATIONS_FOR_SETTER + "," + addAnnotationToSetter
				: KNOWN_ANNOTATIONS_FOR_SETTER;
	}

	public String getBeanFieldPrefix()
	{
		return beanFieldPrefix;
	}

	public String getBuilderSuffix()
	{
		return builderSuffix;
	}

	public String getD()
	{
		return d;
	}

	public String getPojoSuffix()
	{
		return pojoSuffix;
	}

	@Override
	public String toString()
	{
		return MessageFormat.format(FORMAT_TO_STRING, getBeanFieldPrefix(), getBuilderSuffix(), getD(), getPojoSuffix(),
				getAddAnnotationToGetter(), getAddAnnotationToSetter());
	}

	/**
	 * @return the set of annotations (fully qualified names) that must be generated on the getter.
	 * @since 20.04.00
	 */
	public Set<String> getAnnotationsToAddToGetters()
	{
		if (null == myAnnotationsToAddToGetters)
		{
			myAnnotationsToAddToGetters = new HashSet<String>();
			myAnnotationsToAddToGetters.addAll(//
					asList(getAddAnnotationToGetter().split(","))//
							.stream()//
							.filter(a -> a != null)//
							.map(a -> a.trim())//
							.filter(a -> a.length() > 0)//
							.collect(toList()));

		}
		return myAnnotationsToAddToGetters;
	}

	/**
	 * @return the set of annotations (fully qualified names) that must be generated on the setter.
	 * @since 20.04.00
	 */
	public Set<String> getAnnotationsToAddToSetters()
	{
		if (null == myAnnotationsToAddToSetters)
		{
			myAnnotationsToAddToSetters = new HashSet<String>();
			myAnnotationsToAddToSetters.addAll(//
					asList(getAddAnnotationToSetter().split(","))//
							.stream()//
							.filter(a -> a != null)//
							.map(a -> a.trim())//
							.filter(a -> a.length() > 0)//
							.collect(toList()));

		}
		return myAnnotationsToAddToSetters;
	}

}