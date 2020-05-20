/**
 * 
 */
package test.sporniket.libre.javabeans.doclet;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for when a test needs common utilities.
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
 * @version 20.05.01
 * @since 19.03.00
 */
public class TestBase
{
	private static final String FORMAT__DATA_FILE_PATH = "%s_data/%s";

	private ObjectMapper mapper = createMapper();

	private ObjectMapper createMapper()
	{
		final ObjectMapper _result = new ObjectMapper();
		_result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return _result;
	}

	protected InputStream getDataRessource(String resourceName)
	{
		return getClass().getClassLoader().getResourceAsStream(getPathToDataRessource(resourceName));
	}

	/**
	 * Compute the path of the ressource, that is put in a folder named after the class name with the "_data" suffix.
	 *
	 * @param resourceName
	 *            the resource name, e.g. "my-data.json".
	 * @return the path, e.g. <code>"com/mypackage/MyClass_data/my-data.json"</code>.
	 */
	protected String getPathToDataRessource(String resourceName)
	{
		return format(FORMAT__DATA_FILE_PATH, getClass().getName().replace('.', File.separatorChar), resourceName);
	}

	/**
	 * Reads a JSON resource file.
	 * 
	 * @param path
	 *            the path of the resource in the classpath.
	 * @param type
	 *            the type to obtain
	 * @return the extracted object
	 * @throws JsonParseException
	 *             when there is a problem.
	 * @throws JsonMappingException
	 *             when there is a problem.
	 * @throws IOException
	 *             when there is a problem.
	 */
	protected <T> T loadJsonData(String path, Class<T> type) throws JsonParseException, JsonMappingException, IOException
	{
		return mapper.readValue(getClass().getClassLoader().getResourceAsStream(path), type);
	}

	/**
	 * Convert to json representation.
	 * 
	 * @param source
	 *            the object to convert.
	 * @return the json representation.
	 * @throws JsonProcessingException
	 *             when there is a problem.
	 */
	protected String toJson(Object source) throws JsonProcessingException
	{
		return mapper.writeValueAsString(source);
	}

}
