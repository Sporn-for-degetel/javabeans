package com.sporniket.libre.javabeans.seeg;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven goal to start seeg.
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
@Mojo(name = "seeg")
public class SeegMojo extends AbstractMojo
{
	/**
	 * Path to the Java source directory of the target project.
	 */
	@Parameter(defaultValue = "${project.build.sourceDirectory}", property = "sourceDir", required = true, readonly = true)
	private File sourceDirectory;

	/**
	 * Configuration of the connection to the database.
	 *
	 * <p>
	 * A property file that MUST contains the following keys :
	 * </p>
	 *
	 * <ul>
	 * <li>url : jdbc url, e.g. 'jdbc:postgresql://localhost:54320/postgres'.</li>
	 * <li>driverClass : jdbc url, e.g. 'org.postgresql.Driver'.</li>
	 * <li>username : name of the database user, should have access to the schema.</li>
	 * <li>password : password for the database user.</li>
	 * </ul>
	 */
	@Parameter(defaultValue = "${project.basedir}/seeg-connection.properties", property = "connectionConfig", required = true)
	private File connectionConfiguration;

	/**
	 * Fully qualified name of the Java package of the generated sources.
	 */
	@Parameter(property = "targetPackage", required = true)
	private String targetPackage;

	/**
	 * Optionnal, name of the database schema to inspect.
	 */
	@Parameter(property = "schemaName", required = false)
	private String schemaName;

	/**
	 * Optionnal, name of the custom hibernate type for enumeration (to support PostgreSql enums) ; either a class inside the target
	 * package, or a fully qualified class name.
	 */
	@Parameter(property = "typeEnum", required = false)
	private String typeEnum;

	@Override
	public void execute() throws MojoExecutionException
	{
		if (StringUtils.isBlank(targetPackage))
		{
			throw new MojoExecutionException("Target package MUST be specified.");
		}
		final String _localPath = trimToEmpty(targetPackage).replace('.', File.separatorChar);
		String _targetDirectory = new File(sourceDirectory, _localPath).getAbsolutePath();
		String _connectionConfiguration = connectionConfiguration.getAbsolutePath();
		String _schemaName = StringUtils.trimToNull(schemaName);
		String _typeEnum = StringUtils.trimToNull(typeEnum);

		try (PipedInputStream pis = new PipedInputStream();
				PipedOutputStream pos = new PipedOutputStream(pis);
				BufferedReader br = new BufferedReader(new InputStreamReader(pis), 32000);
				PrintStream out = new PrintStream(pos);)
		{
			final List<Throwable> errorStack = new ArrayList<>(1);
			final ExecutorService executor = newSingleThreadExecutor();
			executor.submit(() -> {
				try
				{
					new Seeg().perform(_connectionConfiguration, _targetDirectory, targetPackage, _schemaName, _typeEnum, out);
				}
				catch (Exception _error)
				{
					// _error.printStackTrace(out);
					errorStack.add(_error);
				}
				finally
				{
					executor.shutdown();
				}
			});

			while (!executor.isShutdown())
			{
				try
				{
					String nextLine = br.readLine();
					if (null != nextLine)
					{
						getLog().info(nextLine);
					}
				}
				catch (IOException _error)
				{
					getLog().error("Error while reading pipe.", _error);
				}
			}
			if (!errorStack.isEmpty())
			{
				throw errorStack.get(0);
			}
		}
		catch (Throwable _error)
		{
			throw new MojoExecutionException("A problem occurred.", _error);
		}
	}
}
