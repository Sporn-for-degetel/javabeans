/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sporniket.strings.pipeline.StringTransformation;

/**
 * Utilities for qualified names.
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
 * @since 19.03.00
 */
class QualifiedNameUtils
{
	static final StringTransformation CONTAINER_FROM_QUALIFIED_NAME = qn -> {
		final int _lastDotPos = qn.lastIndexOf('.');
		return -1 < _lastDotPos //
				? qn.substring(0, _lastDotPos) //
				: "";
	};

	static final StringTransformation NAME_FROM_QUALIFIED_NAME = qn -> {
		final int _lastDotPos = qn.lastIndexOf('.');
		return -1 < _lastDotPos //
				? qn //
				: qn.substring(_lastDotPos + 1);
	};

}
