package com.sporniket.libre.javabeans.doclet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

/**
 * Utility class for {@link FieldDoc}.
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
 * @version 20.04.04
 * @since 17.09.00
 */
public final class UtilsFieldDoc
{
	private static Predicate<FieldDoc> IS_ACCESSIBLE_FIELD = f -> f.isPublic() || f.isPackagePrivate();

	private static void collectPublicFieldsInto(List<FieldDoc> buffer, ClassDoc toScan)
	{
		if (!Object.class.getName().equals(toScan.qualifiedName()))
		{
			buffer.addAll(Arrays.asList(toScan.fields()).stream().filter(IS_ACCESSIBLE_FIELD).collect(Collectors.toList()));
			collectPublicFieldsInto(buffer, toScan.superclass());
		}
	}

	/**
	 * Get the declared public or package private fields of a given class, inherited fields are excluded.
	 *
	 * @param toScan
	 *            the class to scan.
	 * @return a list of fields.
	 */
	public static List<FieldDoc> getAccessibleDeclaredFields(ClassDoc toScan)
	{
		return Arrays.asList(toScan.fields()).stream().filter(IS_ACCESSIBLE_FIELD).collect(Collectors.toList());
	}

	/**
	 * Get the public or package private fields of a given class, inherited fields are included.
	 *
	 * @param toScan
	 *            the class to scan.
	 * @return a list of fields.
	 */
	public static List<FieldDoc> getAccessibleFields(ClassDoc toScan)
	{
		final List<FieldDoc> _buffer = new ArrayList<>();
		collectPublicFieldsInto(_buffer, toScan);
		return new ArrayList<>(_buffer);
	}

	/**
	 * Get the declared private fields of a given class, inherited fields are excluded.
	 *
	 * @param toScan
	 *            the class to scan.
	 * @return a list of fields.
	 */
	public static List<FieldDoc> getPrivateDeclaredFields(ClassDoc toScan)
	{
		return Arrays.asList(toScan.fields()).stream().filter(f -> f.isPrivate()).collect(Collectors.toList());
	}

}
