/**
 * 
 */
package unit;

import static java.lang.String.join;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.RawCommentProcessorFactory;

import test.sporniket.libre.javabeans.doclet.TestBase;

/**
 * Tests on RawCommentProcessors created by {@link RawCommentProcessorFactory}.
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
 * @version 20.05.00
 * @since 19.09.00
 */
public class RawCommentProcessorTest extends TestBase
{
	public static class TestSpec
	{
		public String description;

		public String[] input;

		public String[] expected;
	}

	public static class TestSuite
	{
		public List<TestSpec> specs;
	}

	@TestFactory
	public Stream<DynamicTest> shouldConvertAsExpected() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare
		TestSuite _suite = loadJsonData(getPathToDataRessource("testSuite.json"), TestSuite.class);
		final Function<String, String> _processor = RawCommentProcessorFactory.createRawCommentProcessor(new DocletOptions());

		// execute and verify
		return _suite.specs.parallelStream()//
				.map(s -> dynamicTest(//
						s.description, //
						() -> {
							then(_processor.apply(join("\n", s.input))).isEqualTo(join("\n", s.expected));
						}));
	}

}
