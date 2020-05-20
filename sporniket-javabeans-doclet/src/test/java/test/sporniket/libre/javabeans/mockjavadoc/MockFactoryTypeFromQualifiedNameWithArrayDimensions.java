/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.Type;

/**
 * Factory to quickly setup a {@link Type} with a qualified name and optionnal array dimensions markers (one "[]" sequence by
 * dimension).
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
 * @since 19.04.00
 */
class MockFactoryTypeFromQualifiedNameWithArrayDimensions implements MockFactory<String, Type>
{
	private final static String ARRAY_DIMENSION_MARK = "[]";

	@Override
	public Type build(String input)
	{
		// separation of the type qualified name and the array dimension markers.
		String _qualifiedName = input;
		StringBuilder _arrayDimensionMarks = new StringBuilder();
		while (_qualifiedName.endsWith(ARRAY_DIMENSION_MARK))
		{
			_arrayDimensionMarks.append(ARRAY_DIMENSION_MARK);
			_qualifiedName = _qualifiedName.substring(0, _qualifiedName.length() - ARRAY_DIMENSION_MARK.length());
		}

		// done
		return (Type) new TypeMockModel_Builder(new TypeMock())//
				.withValQualifiedName(_qualifiedName)//
				.withValDimension(_arrayDimensionMarks.toString())//
				.done();
	}

}
