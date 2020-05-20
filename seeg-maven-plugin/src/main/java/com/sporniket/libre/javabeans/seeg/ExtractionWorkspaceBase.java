package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.StringHelper.camelCaseCapitalizedFromSnakeCase;
import static java.lang.String.format;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Building of the internal representation that is common to all databases.
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
public abstract class ExtractionWorkspaceBase implements ExtractionWorkspace
{

	protected final Map<String, DefTable> regClasses = new HashMap<>();

	protected final Map<String, DefEnum> regEnums = new HashMap<>();

	public ExtractionWorkspaceBase()
	{
		super();
	}

	@Override
	public Collection<DefTable> getClasses()
	{
		return regClasses.values();
	}

	private DefTable getClassOrDie(String table)
	{
		if (!regClasses.containsKey(table))
		{
			throw new IllegalStateException(format("Try to register primary key of unknown table '%s'", table));
		}
		return regClasses.get(table);
	}

	@Override
	public Collection<DefEnum> getEnums()
	{
		return regEnums.values();
	}

	@Override
	public void registerClass(final String table, final String comment, final String typeEnum)
	{
		DefTable _class = new DefTable();
		_class.nameInDatabase = table;
		_class.nameInJava = camelCaseCapitalizedFromSnakeCase(table);
		_class.comment = comment;
		_class.typeDefPgEnum = typeEnum;
		regClasses.put(table, _class);
	}

	@Override
	public void registerEnumValue(final String name, final String value)
	{
		if (!regEnums.containsKey(name))
		{
			final DefEnum _enum = new DefEnum();
			_enum.nameInDatabase = name;
			_enum.nameInJava = camelCaseCapitalizedFromSnakeCase(name);
			regEnums.put(name, _enum);
		}
		regEnums.get(name).values.add(value);
	}

	@Override
	public void registerPrimaryKey(String table, String column)
	{
		DefTable _class = getClassOrDie(table);
		_class.pkeysColumns.add(column);

	}

	@Override
	public void registerSelector(String table, String selectorName, String columnName, boolean unique)
	{
		DefTable _class = getClassOrDie(table);
		if (!_class.columns.containsKey(columnName))
		{
			throw new IllegalStateException(format("Try to register selector of unknown column '%s.%s'", table, columnName));
		}
		if (1 == _class.pkeysColumns.size() && _class.pkeysColumns.contains(columnName))
		{
			return; // already exists in JpaRepository.
		}
		DefSelector selector;
		if (_class.selectors.containsKey(selectorName))
		{
			selector = _class.selectors.get(selectorName);
		}
		else
		{
			selector = new DefSelector();
			selector.nameInDatabase = selectorName;
			selector.unique = unique;
			_class.selectors.put(selectorName, selector);
		}
		selector.columns.add(columnName);

	}

}