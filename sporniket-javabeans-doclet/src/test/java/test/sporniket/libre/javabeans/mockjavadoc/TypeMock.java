/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sporniket.strings.pipeline.StringTransformation;
import com.sun.javadoc.AnnotatedType;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.Type;
import com.sun.javadoc.TypeVariable;
import com.sun.javadoc.WildcardType;

/**
 * Mocked {@link Type}.
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
 * @since 19.03.00
 */
public class TypeMock extends TypeMockModel implements Type
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asAnnotatedType()
	 */
	@Override
	public AnnotatedType asAnnotatedType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asAnnotationTypeDoc()
	 */
	@Override
	public AnnotationTypeDoc asAnnotationTypeDoc()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asClassDoc()
	 */
	@Override
	public ClassDoc asClassDoc()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asParameterizedType()
	 */
	@Override
	public ParameterizedType asParameterizedType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asTypeVariable()
	 */
	@Override
	public TypeVariable asTypeVariable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asWildcardType()
	 */
	@Override
	public WildcardType asWildcardType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#dimension()
	 */
	@Override
	public String dimension()
	{
		return StringTransformation.NULL_TO_EMPTY.transform(getValDimension());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#getElementType()
	 */
	@Override
	public Type getElementType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#isPrimitive()
	 */
	@Override
	public boolean isPrimitive()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#qualifiedTypeName()
	 */
	@Override
	public String qualifiedTypeName()
	{
		return getValQualifiedName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#simpleTypeName()
	 */
	@Override
	public String simpleTypeName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#typeName()
	 */
	@Override
	public String typeName()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
