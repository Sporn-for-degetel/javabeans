package com.sporniket.libre.javabeans.doclet.basic;

import java.io.PrintStream;

import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;

/**
 * A base to implements the generators of java source.
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
public class BasicGeneratorBase
{
	private ClassSpecs myClassSpecs;

	private DocletOptions myOptions;

	private PrintStream myOut;

	public BasicGeneratorBase()
	{
		super();
	}

	public ClassSpecs getClassSpecs()
	{
		return myClassSpecs;
	}

	public DocletOptions getOptions()
	{
		return myOptions;
	}

	public PrintStream getOut()
	{
		return myOut;
	}

	public void setClassSpecs(ClassSpecs classSpecs)
	{
		myClassSpecs = classSpecs;
	}

	public void setOptions(DocletOptions options)
	{
		myOptions = options;
	}

	public void setOut(PrintStream out)
	{
		myOut = out;
	}
}