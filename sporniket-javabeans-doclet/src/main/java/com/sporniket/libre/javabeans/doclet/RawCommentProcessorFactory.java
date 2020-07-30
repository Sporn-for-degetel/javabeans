package com.sporniket.libre.javabeans.doclet;

import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.function.Function;

/**
 * Factory to create the function that look for and converts references to pojos into references to javabeans.
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
public class RawCommentProcessorFactory
{
	private static final String MARKER_BEGIN = "{@";

	private static final String MARKER_END = "}";

	private static final String MARKER_VALUE = "value ";

	private static final String MARKER_LINK = "link ";

	private static final Function<String, String> createStrictSuffixRemover(String suffix)
	{
		return source -> {
			if (source.length() > suffix.length() && source.endsWith(suffix))
			{
				return source.substring(0, source.length() - suffix.length());
			}
			return source;
		};
	}

	public static Function<String, String> createRawCommentProcessor(DocletOptions options)
	{
		return s -> {
			StringBuilder result = new StringBuilder();
			Function<String, String> suffixRemover = createStrictSuffixRemover(options.pojoSuffix);
			// FIXME create a context structure to factorise things
			int from = 0; // next copy would start from here
			int current = from; // cursor position
			final int max = s.length(); // max cursor value
			while (current < max)
			{
				int pos = s.indexOf(MARKER_BEGIN, current);
				if (-1 != pos)
				{
					int posEnd = s.indexOf(MARKER_END, pos);
					if (-1 == posEnd)
					{
						// something is wrong, abort and copy as is
						current = s.length();
						break;
					}

					// found '{@...}' that may need conversion
					current = pos + MARKER_BEGIN.length();
					String _candidate = s.substring(current, posEnd);
					int _offset = 0;
					if (_candidate.startsWith(MARKER_LINK))
					{
						_offset = MARKER_LINK.length();
					}
					else if (_candidate.startsWith(MARKER_VALUE))
					{
						_offset = MARKER_VALUE.length();
					}

					if (0 < _offset)
					{
						// copy the source part reached so far
						current += _offset;
						result.append(s.substring(from, current));
						from = posEnd;

						String _convertible = _candidate.substring(_offset);
						String _suffix = "";
						// look for convertible
						final int _suffixPos = _convertible.indexOf("#");
						if (-1 < _suffixPos)
						{
							_suffix = _convertible.substring(_suffixPos);
							_convertible = _convertible.substring(0, _suffixPos);
						}
						result.append(join(".", asList(_convertible.split("\\.")).stream()//
								.map(suffixRemover)//
								.collect(toList())))//
								.append(_suffix);
					}

					current = posEnd; // next
				}
				else
				{
					// no more reference to process
					current = s.length();
					break;
				}
			}
			if (from < current)
			{
				result.append(s.substring(from, current));
			}
			return result.toString();
		};
	}
}
