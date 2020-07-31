package com.sporniket.libre.javabeans.seeg;

/**
 * Internal representation of a column, will be converted into an object property.
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
 * @version 20.05.01
 * @since 20.04.01
 */
public class DefColumn extends Def
{
	/**
	 * <code>true</code> if the column is an automatically incremented value or a value taken from a sequence.
	 */
	public boolean generated;

	/**
	 * When the column value is generated, contains the type of generation (identity, sequence,...)
	 */
	public String generationStrategy;

	/**
	 * When the column value is generated by a sequence, the parameter of the name of the sequence.
	 */
	public String sequenceGenerator;

	/**
	 * Java type of the column.
	 */
	public String javaType;

	/**
	 * Database type of the column.
	 */
	public String dbType;

	/**
	 * The fully parametred temporal annotation.
	 */
	public String temporalMapping;

	/**
	 * <code>true</code> if the value MUST be non null at validation time.
	 */
	public boolean notNullable;

	/**
	 * <code>true</code> if the column use an enumeration type.
	 *
	 * @since 20.05.00
	 */
	public boolean isEnum;

	/**
	 * Defined when the column is a foreign key.
	 */
	public DefReference foreignKey;
}
