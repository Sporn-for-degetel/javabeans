package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;

/**
 * Mock factories for trivial cases.
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
 * 
 * @param <InputType>
 *            Type of object provided as parameter to instanciate the mocked object.
 * @param <MockType>
 *            The type of mocked object to build.
 */
interface MockFactory<InputType, MockType>
{

	/**
	 * Factory to quickly setup a {@link ClassDoc} with a qualified name.
	 */
	static MockFactory<String, ClassDoc> CLASSDOC_FROM_QUALIFIED_NAME = //
			qn -> (ClassDoc) new ClassDocMockModel_Builder(new ClassDocMock())//
					.withValQualifiedName(qn)//
					.done();

	/**
	 * Factory to quicly setup a {@link PackageDoc} with a qualified name.
	 */
	static MockFactory<String, PackageDoc> PACKAGEDOC_FROM_QUALIFIED_NAME = //
			qn -> (PackageDoc) new PackageDocMockModel_Builder(new PackageDocMock())//
					.withValQualifiedName(qn)//
					.done();

	/**
	 * Factory to quickly setup a {@link Type} with a qualified name and optionnal array dimensions markers (one "[]" sequence by
	 * dimension).
	 */
	static MockFactory<String, Type> TYPE_FROM_QUALIFIED_NAME = new MockFactoryTypeFromQualifiedNameWithArrayDimensions();

	MockType build(InputType input);
}
