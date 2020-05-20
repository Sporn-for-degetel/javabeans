package com.sporniket.libre.javabeans.doclet;

import com.sporniket.strings.StringPredicates;
import com.sporniket.strings.pipeline.StringTransformation;

/**
 * The collection of String utilities.
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
public final class UtilsString
{
	/**
	 * String Transformation collections.
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
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the
	 * terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License,
	 * or (at your option) any later version.
	 *
	 * <p>
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
	 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
	 * General Public License for more details.
	 *
	 * <p>
	 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library
	 * &#8211; core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
	 *
	 * <hr>
	 *
	 * @author David SPORN
	 * @version 20.05.01
	 * @since 19.03.00
	 */
	public static final class Transformations
	{
		public static final StringTransformation CAPITALIZER = s -> s.substring(0, 1).toUpperCase() + s.substring(1);

		public static final StringTransformation UNCAPITALIZER = s -> s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * String transformation factories.
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
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the
	 * terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License,
	 * or (at your option) any later version.
	 *
	 * <p>
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
	 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
	 * General Public License for more details.
	 *
	 * <p>
	 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library
	 * &#8211; core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
	 *
	 * <hr>
	 *
	 * @author David SPORN
	 * @version 20.05.01
	 * @since 19.03.00
	 */
	public static final class TransformationFactories
	{
		public static final StringTransformation buildPrefixRemover(String prefix)
		{
			if (StringPredicates.IS_EMPTY.test(prefix))
			{
				return s -> s;
			}
			return s -> null != s && s.length() > prefix.length() && s.startsWith(prefix) ? s.substring(prefix.length()) : s;
		}
	}
}
