/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import java.util.ArrayList;
import java.util.List;

import com.sun.javadoc.AnnotatedType;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;
import com.sun.javadoc.TypeVariable;
import com.sun.javadoc.WildcardType;

/**
 * Mocked {@link ClassDoc}.
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
public class ClassDocMock extends ClassDocMockModel implements ClassDoc
{

	private static ClassDoc[] MOCK_INTERFACES = {};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#annotations()
	 */
	@Override
	public AnnotationDesc[] annotations()
	{
		// TODO Auto-generated method stub
		return new AnnotationDesc[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#containingClass()
	 */
	@Override
	public ClassDoc containingClass()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#containingPackage()
	 */
	@Override
	public PackageDoc containingPackage()
	{
		return getValContainingPackage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isFinal()
	 */
	@Override
	public boolean isFinal()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isPackagePrivate()
	 */
	@Override
	public boolean isPackagePrivate()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isPrivate()
	 */
	@Override
	public boolean isPrivate()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isProtected()
	 */
	@Override
	public boolean isProtected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isPublic()
	 */
	@Override
	public boolean isPublic()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isStatic()
	 */
	@Override
	public boolean isStatic()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#modifierSpecifier()
	 */
	@Override
	public int modifierSpecifier()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#modifiers()
	 */
	@Override
	public String modifiers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#qualifiedName()
	 */
	@Override
	public String qualifiedName()
	{
		return getValQualifiedName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#commentText()
	 */
	@Override
	public String commentText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#firstSentenceTags()
	 */
	@Override
	public Tag[] firstSentenceTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#getRawCommentText()
	 */
	@Override
	public String getRawCommentText()
	{
		return null != getValRawComment() ? String.join("\n", getValRawComment()) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#inlineTags()
	 */
	@Override
	public Tag[] inlineTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isAnnotationType()
	 */
	@Override
	public boolean isAnnotationType()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isAnnotationTypeElement()
	 */
	@Override
	public boolean isAnnotationTypeElement()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isClass()
	 */
	@Override
	public boolean isClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isConstructor()
	 */
	@Override
	public boolean isConstructor()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isEnum()
	 */
	@Override
	public boolean isEnum()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isEnumConstant()
	 */
	@Override
	public boolean isEnumConstant()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isError()
	 */
	@Override
	public boolean isError()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isException()
	 */
	@Override
	public boolean isException()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isField()
	 */
	@Override
	public boolean isField()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isIncluded()
	 */
	@Override
	public boolean isIncluded()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isInterface()
	 */
	@Override
	public boolean isInterface()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isMethod()
	 */
	@Override
	public boolean isMethod()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isOrdinaryClass()
	 */
	@Override
	public boolean isOrdinaryClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#name()
	 */
	@Override
	public String name()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#position()
	 */
	@Override
	public SourcePosition position()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#seeTags()
	 */
	@Override
	public SeeTag[] seeTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#setRawCommentText(java.lang.String)
	 */
	@Override
	public void setRawCommentText(String arg0)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#tags()
	 */
	@Override
	public Tag[] tags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#tags(java.lang.String)
	 */
	@Override
	public Tag[] tags(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#constructors()
	 */
	@Override
	public ConstructorDoc[] constructors()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#constructors(boolean)
	 */
	@Override
	public ConstructorDoc[] constructors(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#definesSerializableFields()
	 */
	@Override
	public boolean definesSerializableFields()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#enumConstants()
	 */
	@Override
	public FieldDoc[] enumConstants()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#fields()
	 */
	@Override
	public FieldDoc[] fields()
	{
		final List<FieldDoc> _allFields = new ArrayList<FieldDoc>();
		// if (null != superclass())
		// {
		// _allFields.addAll(asList(superclass().fields()));
		// }
		if (null != getValFieldDocs())
		{
			_allFields.addAll(getValFieldDocs());
		}
		return _allFields.toArray(new FieldDoc[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#fields(boolean)
	 */
	@Override
	public FieldDoc[] fields(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#findClass(java.lang.String)
	 */
	@Override
	public ClassDoc findClass(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#importedClasses()
	 */
	@Override
	public ClassDoc[] importedClasses()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#importedPackages()
	 */
	@Override
	public PackageDoc[] importedPackages()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#innerClasses()
	 */
	@Override
	public ClassDoc[] innerClasses()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#innerClasses(boolean)
	 */
	@Override
	public ClassDoc[] innerClasses(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#interfaceTypes()
	 */
	@Override
	public Type[] interfaceTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#interfaces()
	 */
	@Override
	public ClassDoc[] interfaces()
	{
		return MOCK_INTERFACES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#isAbstract()
	 */
	@Override
	public boolean isAbstract()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#isExternalizable()
	 */
	@Override
	public boolean isExternalizable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#isSerializable()
	 */
	@Override
	public boolean isSerializable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#methods()
	 */
	@Override
	public MethodDoc[] methods()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#methods(boolean)
	 */
	@Override
	public MethodDoc[] methods(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#serializableFields()
	 */
	@Override
	public FieldDoc[] serializableFields()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#serializationMethods()
	 */
	@Override
	public MethodDoc[] serializationMethods()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#subclassOf(com.sun.javadoc.ClassDoc)
	 */
	@Override
	public boolean subclassOf(ClassDoc arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#superclass()
	 */
	@Override
	public ClassDoc superclass()
	{
		return getValSuperclass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#superclassType()
	 */
	@Override
	public Type superclassType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#typeParamTags()
	 */
	@Override
	public ParamTag[] typeParamTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ClassDoc#typeParameters()
	 */
	@Override
	public TypeVariable[] typeParameters()
	{
		// TODO Auto-generated method stub
		return new TypeVariable[] {};
	}

}
