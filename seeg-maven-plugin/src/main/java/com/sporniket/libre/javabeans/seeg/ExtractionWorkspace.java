package com.sporniket.libre.javabeans.seeg;

import java.util.Collection;

/**
 * Workspace to maintains the internal representation for extracting the entities.
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
 * @version 20.04.04
 * @since 20.04.01
 */
public interface ExtractionWorkspace
{
	Collection<DefTable> getClasses();

	Collection<DefEnum> getEnums();

	void registerClass(final String table, final String comment, final String typeEnum);

	void registerColumn(final String table, final String column, final String type, final String nullable, final String autoinc,
			final String isGenerated, final String comment, final String defaultValue, String typeEnum);

	void registerEnumValue(final String _name, final String _value);

	void registerPrimaryKey(final String table, final String column);

	void registerSelector(final String table, final String selectorName, final String columnName, boolean unique);
}