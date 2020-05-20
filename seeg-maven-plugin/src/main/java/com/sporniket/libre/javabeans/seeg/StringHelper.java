package com.sporniket.libre.javabeans.seeg;

/**
 * Utilities for strings.
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
 * <i>The Sporniket Javabeans Project &#8211; seeg</i> is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; seeg</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 20.05.00
 * @since 20.04.01
 */
public class StringHelper
{
	private static String camelCamelFromSnakeCase(String snaked, boolean firstLetterIsCapitalized)
	{
		String[] parts = snaked.split("_");
		StringBuilder b = new StringBuilder();
		for (String part : parts)
		{
			b.append(0 == b.length() && !firstLetterIsCapitalized ? part : capitalizeFirstLetter(part));
		}
		return b.toString();
	}

	public static String camelCaseCapitalizedFromSnakeCase(String snaked)
	{
		return camelCamelFromSnakeCase(snaked, true);
	}

	public static String camelCaseUncapitalizedFromSnakeCase(String snaked)
	{
		return camelCamelFromSnakeCase(snaked, false);
	}

	private static String capitalizeFirstLetter(String v)
	{
		return v.substring(0, 1).toUpperCase() + v.substring(1);
	}

	public static String uncapitalizeFirstLetter(String v)
	{
		return v.substring(0, 1).toLowerCase() + v.substring(1);
	}

	public static String undoublequote(String value)
	{
		if (value.startsWith("\""))
		{
			value = value.substring(1);
		}
		if (value.endsWith("\""))
		{
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

}
