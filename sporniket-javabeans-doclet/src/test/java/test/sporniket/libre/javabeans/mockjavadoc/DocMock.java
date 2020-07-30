package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.Doc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

/**
 * Mock implementation of {@link Doc} backed using {@link DocMockModel}.
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
 * @version 20.04.04
 * @since 19.09.00
 */
public class DocMock extends DocMockModel implements Doc
{

	@Override
	public String commentText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tag[] firstSentenceTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRawCommentText()
	{
		return String.join("\n", getValRawComment());
	}

	@Override
	public Tag[] inlineTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAnnotationType()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnnotationTypeElement()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConstructor()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnum()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnumConstant()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isError()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isException()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isField()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIncluded()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInterface()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMethod()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOrdinaryClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourcePosition position()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeeTag[] seeTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRawCommentText(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Tag[] tags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag[] tags(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
