/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.codespecs;

import static com.sporniket.strings.StringComparators.STRING_COMPARATOR_NATURAL;
import static com.sporniket.strings.StringComparators.STRING_COMPARATOR_REVERSE;
import static java.util.Comparator.nullsFirst;

import java.util.Comparator;

/**
 * Builtin comparators for the model.
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
 * @since 19.02.00
 */
public final class Comparators
{
	public final static Comparator<ImportSpecs> IMPORT_SPECS_COMPARATOR_NATURAL = nullsFirst(
			(is1, is2) -> STRING_COMPARATOR_NATURAL.compare(is1.getClassName(), is2.getClassName()));//

	public final static Comparator<ImportSpecs> IMPORT_SPECS_COMPARATOR_REVERSE = nullsFirst(
			(is1, is2) -> STRING_COMPARATOR_REVERSE.compare(is1.getClassName(), is2.getClassName()));//
}
