/**
 * 
 */
package test.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.CodeSpecsExtractor.ExtractionMode.EXPANDER;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sporniket.libre.javabeans.doclet.CodeSpecsExtractor;
import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sun.javadoc.ClassDoc;

import test.sporniket.libre.javabeans.mockjavadoc.MockSetup;
import test.sporniket.libre.javabeans.mockjavadoc.MockSetupLoader;

/**
 * Tests for {@link CodeSpecsExtractor} when expanding pojos.
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
public class CodeSpecsExtractorExpanderTest extends TestBase
{

	private static final String MAIN_CLASS_NAME = "TheClass";

	private ClassSpecs extractSpecs(ClassDoc source)
	{
		return new CodeSpecsExtractor().extractSpecs(source, new HashMap<String, String>(0), new DocletOptions(), EXPANDER);
	}

	@TestFactory
	public Stream<DynamicTest> shouldHaveExpectedClassSpecs() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare
		MockSetup setupFieldExtractionTest = new MockSetupLoader().load(getDataRessource("fieldTestSuite.json"));
		ClassSpecs _expectedClassSpecs = loadJsonData(getPathToDataRessource("fieldTestSuite_expected.json"), ClassSpecs.class);
		Map<String, FieldSpecs> _expectedFields = _expectedClassSpecs.getFields().parallelStream()
				.collect(toMap(FieldSpecs::getNameForField, identity()));

		// execute
		ClassSpecs _actualClassSpecs = extractSpecs(setupFieldExtractionTest.getClasses().get(MAIN_CLASS_NAME));
		Map<String, FieldSpecs> _actualFields = _actualClassSpecs.getFields().parallelStream()
				.collect(toMap(FieldSpecs::getNameForField, identity()));

		// verify
		List<DynamicTest> _result = new ArrayList<DynamicTest>(_actualFields.size() + 1);
		_result.add(//
				dynamicTest("Should have javadoc lines", //
						() -> {
							then(_actualClassSpecs.getJavadocLines())//
									.isNotNull()//
									.isNotEmpty()//
							;
							then(join("\n", _actualClassSpecs.getJavadocLines()))
									.isEqualTo(join("\n", _expectedClassSpecs.getJavadocLines()))//
							;
						}//
				));
		_result.add(//
				dynamicTest("Should have the same number of fields with same names", //
						() -> then(_actualFields).containsOnlyKeys(_expectedFields.keySet())));
		_result.addAll(_actualFields.entrySet().parallelStream()//
				.map(e -> dynamicTest(//
						format("Field '%s' should be as expected", e.getKey()), //
						() -> then(toJson(e.getValue())).isEqualTo(toJson(_expectedFields.get(e.getKey())))))//
				.collect(toList()));

		return _result.stream();
	}

	@TestFactory
	public Stream<DynamicTest> shouldHaveExpectedImportSpecs() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare
		MockSetup setupInheritedField = new MockSetupLoader().load(getDataRessource("classWithInheritedField.json"));
		MockSetup setupNativeTypeFields = new MockSetupLoader().load(getDataRessource("classWithNativeTypeFields.json"));

		// execute
		ClassSpecs _classSpecsInheritedField = extractSpecs(setupInheritedField.getClasses().get(MAIN_CLASS_NAME));
		ClassSpecs _classSpecsNativeTypeFields = extractSpecs(setupNativeTypeFields.getClasses().get(MAIN_CLASS_NAME));

		// verify
		return asList(//
				dynamicTest("Should not miss an import"//
						, () -> then(_classSpecsInheritedField.getImports()//
								.stream()//
								.map(ImportSpecs::getClassName)//
								.collect(toList())//
						)//
								.contains("java.net.URL", "java.util.Date", MAIN_CLASS_NAME, "TheBaseClass")//
								.hasSize(4))//
				, dynamicTest("Should not import directly types from inherited fields"//
						, () -> then(_classSpecsInheritedField.getImports()//
								.stream()//
								.filter(ImportSpecs::isDirectlyRequired)//
								.map(ImportSpecs::getClassName)//
								.collect(toList())//
						)//
								.doesNotContain("java.util.Date")//
								.hasSize(3))//
				, dynamicTest("Should not import native types"//
						, () -> then(_classSpecsNativeTypeFields.getImports()//
								.stream()//
								.map(ImportSpecs::getClassName)//
								.collect(toList())//
						)//
								.doesNotContain(boolean.class.getName()//
										, byte.class.getName()//
										, char.class.getName()//
										, int.class.getName()//
										, long.class.getName()//
										, float.class.getName()//
										, double.class.getName()//
										, short.class.getName()//
								))//
		).stream();
	}
}
