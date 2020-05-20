package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.basic.Utils.NEXT_INDENTATION;
import static com.sporniket.libre.javabeans.doclet.codespecs.Comparators.IMPORT_SPECS_COMPARATOR_NATURAL;
import static java.lang.String.join;

import java.util.TreeSet;
import java.util.function.Consumer;

import com.sporniket.libre.javabeans.doclet.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sporniket.libre.lang.string.StringTools;

/**
 * Basic generator for pojos from javabeans.
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
 * @since 17.09.00
 */
public class BasicRawPojoGenerator extends BasicGenerator implements JavabeanGenerator
{

	@Override
	public void outputAccessors()
	{
		// nothing to do
	}

	@Override
	public void outputClassBegin()
	{
		final String _classMarker = getClassSpecs().isAbstractRequired() ? "abstract class" : "class";
		final String _extendsMarker = StringTools.isEmptyString(getClassSpecs().getSuperClassName()) ? "" : "\n        extends ";
		final String _implementsMarker = StringTools.isEmptyString(getClassSpecs().getInterfaceList()) ? "" : "\n      implements ";

		final String[] _javadocLines = getClassSpecs().getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			getOut().printf("/**%s\n*/\n", join("\n", _javadocLines));
		}
		final Consumer<? super AnnotationSpecs> _outputAnnotation = a -> outputAnnotation(a, "");
		getClassSpecs().getAnnotations().stream()//
				.forEach(_outputAnnotation);
		getOut().printf("%s %s%s%s%s%s%s\n{\n\n", //
				_classMarker, getClassSpecs().getClassName(), getClassSpecs().getDeclaredTypeArguments()//
				, _extendsMarker, getClassSpecs().getSuperClassName()//
				, _implementsMarker, getClassSpecs().getInterfaceList()//
		);
	}

	private void outputField(FieldSpecs field)
	{
		final String[] _javadocLines = field.getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			getOut().printf("/**%s\n*/\n", join("\n", _javadocLines));
		}
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnField)//
				.forEach(a -> outputAnnotation(a, NEXT_INDENTATION));
		getOut().printf("    %s%s %s ;\n", field.getTypeInvocation(), field.getArrayMarker(), field.getNameForField());
	}

	@Override
	public void outputFields()
	{
		getClassSpecs().getFields().stream().filter(FieldSpecs::isDirectlyRequired).forEach(f -> outputField(f));
	}

	@Override
	public void outputImportStatements()
	{
		TreeSet<ImportSpecs> _sortedImports = new TreeSet<ImportSpecs>(IMPORT_SPECS_COMPARATOR_NATURAL);
		_sortedImports.addAll(getClassSpecs().getImports());
		_sortedImports.stream().filter(ImportSpecs::isDirectlyRequired).forEach(i -> outputImportSpecIfValid(i));

		getOut().println();
	}
}
