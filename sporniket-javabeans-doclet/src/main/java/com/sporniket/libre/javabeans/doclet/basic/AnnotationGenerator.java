/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.basic.Utils.NEXT_INDENTATION;

import java.util.List;

import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationParameterSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationParameterSpecsValuesArray;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs;

/**
 * Annotation generator, independant of the type of java source code generator.
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
 * @since 18.10.00
 */
public class AnnotationGenerator extends BasicGeneratorBase
{
	private String computeAnnotationValue(AnnotationParameterSpecs annotation, String indentation)
	{
		if (annotation instanceof AnnotationParameterSpecsSingleValue)
		{
			return computeAnnotationValue((AnnotationParameterSpecsSingleValue) annotation, indentation + NEXT_INDENTATION);
		}
		else if (annotation instanceof AnnotationParameterSpecsValuesArray)
		{
			return computeAnnotationValue((AnnotationParameterSpecsValuesArray) annotation, indentation + NEXT_INDENTATION);
		}
		throw new IllegalStateException("Unsupported annotation parameter specs.");
	}

	private String computeAnnotationValue(AnnotationParameterSpecsSingleValue annotation, String indentation)
	{
		return outputAnnotationParameterValue(annotation);
	}

	private String computeAnnotationValue(AnnotationParameterSpecsValuesArray annotation, String indentation)
	{
		StringBuilder _result = new StringBuilder("{");
		for (AnnotationParameterSpecsSingleValue _value : annotation.getValues())
		{
			if (_result.length() > 1)
			{
				_result.append(", ");
			}
			_result.append("\n      ").append(indentation).append(outputAnnotationParameterValue(_value));
		}
		return _result.append("\n").append(indentation).append("}").toString();
	}

	public void outputAnnotation(AnnotationSpecs annotations, String indentation)
	{
		final List<AnnotationParameterSpecs> _parameters = annotations.getParameters();
		if (null != _parameters && !_parameters.isEmpty())
		{
			String _format = indentation + "@%s(%s)\n";
			final AnnotationParameterSpecs _firstParameter = _parameters.get(0);
			StringBuilder _parametersValueStatement = new StringBuilder();
			if (1 == _parameters.size() && "value".equals(_firstParameter.getName()))
			{
				_parametersValueStatement.append(computeAnnotationValue(_firstParameter, indentation));
			}
			else
			{
				String _nextIndentation = indentation + NEXT_INDENTATION;
				// explicite parameters
				for (AnnotationParameterSpecs _parameter : _parameters)
				{
					if (_parametersValueStatement.length() > 0)
					{
						_parametersValueStatement.append(", ");
					}
					_parametersValueStatement.append("\n").append(_nextIndentation)//
							.append(_parameter.getName()).append(" = ")
							.append(computeAnnotationValue(_parameter, _nextIndentation));
				}
				_parametersValueStatement.append("\n");
			}
			getOut().printf(_format, annotations.getType(), _parametersValueStatement.toString());
		}
		else
		{
			getOut().printf(indentation + "@%s\n", annotations.getType());
		}
	}

	private String outputAnnotationParameterValue(final AnnotationParameterSpecsSingleValue value)
	{
		return (value.isString()) ? String.format("\"%s\"", value.getValue()) : value.getValue().toString();
	}

}
