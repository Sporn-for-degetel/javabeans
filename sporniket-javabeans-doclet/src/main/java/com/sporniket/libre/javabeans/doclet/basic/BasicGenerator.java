/**
 *
 */
package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.UtilsClassname.getPackageName;

import java.io.PrintStream;
import java.util.function.Predicate;

import com.sporniket.libre.javabeans.doclet.JavaSourceGenerator;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;

/**
 * Base class for generating java code.
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
 * @since 17.09.00
 */
public abstract class BasicGenerator extends BasicGeneratorBase implements JavaSourceGenerator
{
	protected static final Predicate<? super String> FILTER__IS_NOT_TYPE_PARAMETER = c -> !"?".equals(c);

	private static final String PACKAGE_NAME__JAVA_LANG = Object.class.getPackage().getName();

	private AnnotationGenerator annotationGenerator = new AnnotationGenerator();

	protected Predicate<? super String> getFilterNotInSamePackage(String containingPackageName)
	{
		return c -> (!containingPackageName.equals(getPackageName(c)));
	}

	protected void outputAnnotation(AnnotationSpecs annotations, String indentation)
	{
		annotationGenerator.outputAnnotation(annotations, indentation);
	}

	@Override
	public void outputClassEnd()
	{
		getOut().println("}\n");
	}

	protected void outputImportSpecIfValid(ImportSpecs specs)
	{
		final String _packageName = getPackageName(specs.getClassName());
		if (!"?".equals(specs.getClassName())//
				&& !PACKAGE_NAME__JAVA_LANG.equals(_packageName)//
				&& !getClassSpecs().getPackageName().equals(_packageName))
		{
			getOut().printf("import %s;\n", specs.getClassName());
		}
	}

	@Override
	public void outputPackageStatement()
	{
		getOut().printf("package %s;\n", getClassSpecs().getPackageName());

		getOut().println();
	}

	@Override
	public void setOut(PrintStream out)
	{
		super.setOut(out);
		annotationGenerator.setOut(out);
	}
}
