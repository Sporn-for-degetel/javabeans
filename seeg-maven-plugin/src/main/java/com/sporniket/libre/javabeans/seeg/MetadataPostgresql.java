package com.sporniket.libre.javabeans.seeg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Metadata for PostgreSql.
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
public class MetadataPostgresql extends MetadataBase
{

	private static final String PGSQL_GET_ENUM_VALUES = "SELECT pg_namespace.nspname as schema, pg_type.typname AS enum_type, pg_enum.enumlabel AS enum_value FROM pg_type JOIN pg_enum ON pg_enum.enumtypid = pg_type.oid JOIN pg_namespace ON pg_namespace.oid = pg_type.typnamespace order by pg_namespace.nspname, pg_enum.enumtypid, pg_enum.enumsortorder;";

	public MetadataPostgresql(Connection connection) throws SQLException
	{
		super(connection);
	}

	@Override
	public ResultSet getEnumValues(String schema) throws SQLException
	{
		return myConnection.prepareCall(PGSQL_GET_ENUM_VALUES).executeQuery();
	}

}
