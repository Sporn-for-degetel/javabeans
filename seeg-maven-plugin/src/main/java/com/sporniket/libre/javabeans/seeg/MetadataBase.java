package com.sporniket.libre.javabeans.seeg;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Most of the metadata are accessed through the connection metadata.
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
public abstract class MetadataBase implements Metadata
{

	protected final DatabaseMetaData myMetaData;

	protected final Connection myConnection;

	public MetadataBase(Connection connection) throws SQLException
	{
		myConnection = connection;
		myMetaData = connection.getMetaData();
	}

	@Override
	public ResultSet getColumns(String schema) throws SQLException
	{
		return myMetaData.getColumns(null, schema, null, null);
	}

	@Override
	public ResultSet getForeignKeys(String schema, String table) throws SQLException
	{
		return myMetaData.getImportedKeys(null, schema, table);
	}

	@Override
	public ResultSet getIndexes(String schema, String table) throws SQLException
	{
		return myMetaData.getIndexInfo(null, schema, table, false, false);
	}

	@Override
	public ResultSet getPrimaryKeys(String schema, String table) throws SQLException
	{
		return myMetaData.getPrimaryKeys(null, schema, table);
	}

	@Override
	public ResultSet getTables(String schema) throws SQLException
	{
		return myMetaData.getTables(null, schema, null, new String[]
		{
				"TABLE"
		});
	}

}