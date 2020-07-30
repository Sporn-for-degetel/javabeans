package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.StringHelper.camelCaseCapitalizedFromSnakeCase;
import static com.sporniket.libre.javabeans.seeg.StringHelper.undoublequote;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Building of the internal representation that is specific to PostgreSql.
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
public class ExtractionWorkspacePostgresql extends ExtractionWorkspaceBase implements ExtractionWorkspace
{

	private static final Map<String, String> PGSQL_TYPE_MAP = Arrays.asList(//
			new String[][]
			{
					{
							"bigserial", "long"
					},
					{
							"serial", "integer"
					},
					{
							"int8", "long"
					},
					{
							"int4", "integer"
					},
					{
							"float8", "float"
					},
					{
							"bool", "boolean"
					},
					{
							"varchar", "String"
					},
					{
							"date", Date.class.getName()
					},
					{
							"time", Date.class.getName()
					},
					{
							"timestamp", Date.class.getName()
					}
			}).stream()//
			.collect(toMap(e -> e[0], e -> e[1]));

	private static final Map<String, String> PGSQL_TYPE_MAP_NULLABLE = Arrays.asList(//
			new String[][]
			{
					{
							"bigserial", "Long"
					},
					{
							"serial", "Integer"
					},
					{
							"int8", "Long"
					},
					{
							"int4", "Integer"
					},
					{
							"float8", "Float"
					},
					{
							"bool", "Boolean"
					}
			}).stream()//
			.collect(toMap(e -> e[0], e -> e[1]));

	private static final Map<String, String> PGSQL_TEMPORAL_ANNOTATION = Arrays.asList(//
			new String[][]
			{
					{
							"date", "@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)"
					},
					{
							"time", "@javax.persistence.Temporal(javax.persistence.TemporalType.TIME)"
					},
					{
							"timestamp", "@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)"
					}
			}).stream()//
			.collect(toMap(e -> e[0], e -> e[1]));

	@Override
	public void registerColumn(final String table, final String column, final String type, final String nullable,
			final String autoinc, final String isGenerated, final String comment, final String defaultValue)
	{
		if (!regClasses.containsKey(table))
		{
			registerClass(table, null);
		}
		DefTable _parent = regClasses.get(table);
		DefColumn _c = new DefColumn();
		_c.nameInDatabase = column;
		_c.comment = comment;
		_c.nameInJava = camelCaseCapitalizedFromSnakeCase(column);
		final boolean _useSequence = null != defaultValue && defaultValue.startsWith("nextval(");
		final boolean _autoIncrement = "YES".equals(autoinc);
		_c.generated = _autoIncrement || _useSequence;
		_c.notNullable = !_c.generated && "NO".equals(nullable);
		if (_c.generated)
		{
			// supports only identity and sequence strategy, for now
			if (_useSequence)
			{
				_c.generationStrategy = "javax.persistence.GenerationType.SEQUENCE";
				// should have a default value like : nextval('schema."seq_name"...
				int _nameStart = defaultValue.indexOf('"') + 1;
				int _nameEnd = defaultValue.indexOf('"', _nameStart);
				_c.sequenceGenerator = defaultValue.substring(_nameStart, _nameEnd);
			}
			else
			{
				_c.generationStrategy = "javax.persistence.GenerationType.IDENTITY";
			}
		}
		if (PGSQL_TYPE_MAP.containsKey(type))
		{
			_c.javaType = PGSQL_TYPE_MAP.get(type);
			if ((!_c.notNullable || _c.generated) && PGSQL_TYPE_MAP_NULLABLE.containsKey(type))
			{
				_c.javaType = PGSQL_TYPE_MAP_NULLABLE.get(type);
			}
			if (PGSQL_TEMPORAL_ANNOTATION.containsKey(type))
			{
				_c.temporalMapping = PGSQL_TEMPORAL_ANNOTATION.get(type);
			}
		}
		else
		{
			// enum value
			String[] _path = type.split("\\.");
			String _enum = _path[_path.length - 1];
			_enum = undoublequote(_enum);
			_c.javaType = regEnums.get(_enum).nameInJava;
		}
		_parent.columns.put(column, _c);
	}

}
