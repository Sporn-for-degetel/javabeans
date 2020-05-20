/**
 *
 */
package test.sporniket.libre.javabeans.doclet.basic;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.basic.BasicJavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.basic.Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs_Builder;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;

/**
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
 * @since 17.09.01
 */
@ExtendWith(MockitoExtension.class)
public class BasicJavabeanGeneratorTest
{
	@Mock
	ClassDoc class1;

	@Mock
	FieldDoc field1;

	@Mock
	DocletOptions options;

	@Mock
	Type type1;

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testBooleanGetterPrefixShouldBeIsInsteadOfGet() throws UnsupportedEncodingException
	{
		// prepare
		final ClassSpecs specs = new ClassSpecs_Builder()//
				.withFields(Arrays.asList(new FieldSpecs_Builder()//
						.withDirectlyRequired(true)//
						.withFieldPrefix("my")//
						.withArrayMarker("")//
						.withNameForAccessor("TheField")//
						.withNameForField("TheField")//
						.withTypeInvocation("foo")//
						.withBooleanGetter(true)//
						.withAnnotations(new ArrayList<>(0))//
						.done()))//
				.done();

		final Charset _charset = StandardCharsets.UTF_8;
		final ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		final PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withOut(_ps)//
				.withClassSpecs(specs)//
				.done();
		// execute
		_generator.outputAccessors();
		final String _result = new String(_baos.toByteArray(), _charset);

		// verify
		then(_result).contains("public foo isTheField(");
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testOutputAccessorsWhenBeanFieldPrefixIsEmpty() throws UnsupportedEncodingException
	{
		// prepare
		final ClassSpecs specs = new ClassSpecs_Builder()//
				.withFields(Arrays.asList(new FieldSpecs_Builder()//
						.withDirectlyRequired(true)//
						.withFieldPrefix("this.")//
						.withArrayMarker("")//
						.withNameForAccessor("TheField")//
						.withNameForField("theField")//
						.withTypeInvocation("foo")//
						.withBooleanGetter(false)//
						.withAnnotations(new ArrayList<>(0))//
						.done()))//
				.done();

		final Charset _charset = StandardCharsets.UTF_8;
		final ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		final PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withOut(_ps)//
				.withClassSpecs(specs)//
				.done();
		// execute
		_generator.outputAccessors();
		final String _result = new String(_baos.toByteArray(), _charset);

		// verify
		then(_result).contains("public foo getTheField(");
		then(_result).contains("public void setTheField(");
		then(_result).contains("{return this.theField ;}");
		then(_result).contains("{this.theField = value;}");
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testOutputAccessorsWhenBeanFieldPrefixIsSpecified() throws UnsupportedEncodingException
	{
		// prepare
		final ClassSpecs specs = new ClassSpecs_Builder()//
				.withFields(Arrays.asList(new FieldSpecs_Builder()//
						.withDirectlyRequired(true)//
						.withFieldPrefix("my")//
						.withArrayMarker("")//
						.withNameForAccessor("TheField")//
						.withNameForField("TheField")//
						.withTypeInvocation("foo")//
						.withBooleanGetter(false)//
						.withAnnotations(new ArrayList<>(0))//
						.done()))//
				.done();

		final Charset _charset = StandardCharsets.UTF_8;
		final ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		final PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withOut(_ps)//
				.withClassSpecs(specs)//
				.done();
		// execute
		_generator.outputAccessors();
		final String _result = new String(_baos.toByteArray(), _charset);

		// verify
		then(_result).contains("public foo getTheField(");
		then(_result).contains("public void setTheField(");
		then(_result).contains("{return myTheField ;}");
		then(_result).contains("{myTheField = value;}");
	}
}
