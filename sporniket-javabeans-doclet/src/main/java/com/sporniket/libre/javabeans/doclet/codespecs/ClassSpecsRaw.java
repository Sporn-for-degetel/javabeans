package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.Collection;
import java.util.List;

import com.sun.javadoc.Doc;

/**
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
 * @version 20.05.00
 * @since 17.12.00
 */
class ClassSpecsRaw
{
	boolean abstractRequired;

	List<AnnotationSpecsRaw> annotations;

	/**
	 * Class simple name (without package).
	 */
	String className;

	/**
	 * Type arguments when defining the class (<code>class</code> keyword).
	 */
	String declaredTypeArguments;

	List<FieldSpecsRaw> fields;

	Collection<ImportSpecsRaw> imports;

	/**
	 * (NOT NULL) Comma separated values of interfaces names.
	 *
	 * Each name SHOULD be simple, unless the type name is not in the list of shortable types.
	 */
	String interfaceList;

	/**
	 * Type arguments when invoking the class (e.g. in the builder).
	 */
	String invokedTypeArguments;

	/**
	 * Javadoc comment lines, as obtained from {@link Doc#getRawCommentText()}.
	 */
	String[] javadocLines;

	/**
	 * fully qualified package name.
	 */
	String packageName;

	/**
	 * (NOT NULL) Simple name of the extended class (MUST be imported), or empty if the super class is <code>Object</code>.
	 */
	String superClassName;
}
