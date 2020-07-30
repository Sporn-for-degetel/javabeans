package test.sporniket.libre.javabeans.doclet;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sporniket.libre.javabeans.doclet.UtilsFieldDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

/**
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
 * @since 17.09.01
 */
@ExtendWith(MockitoExtension.class)
public class UtilsFieldDocTest
{
	@Mock
	ClassDoc class1;

	@Mock
	ClassDoc class2;

	@Mock
	ClassDoc class3;

	@Mock
	FieldDoc field1;

	@Mock
	FieldDoc field2;

	@Mock
	FieldDoc field3;

	@Mock
	FieldDoc field4;

	@Test
	public void test__getAccessibleDeclaredFields()
	{
		// prepare
		// * field 1
		when(field1.isPublic()).thenReturn(true);

		// * field 2
		when(field2.isPublic()).thenReturn(false);
		when(field2.isPackagePrivate()).thenReturn(true);

		// * field 3
		when(field3.isPublic()).thenReturn(false);
		when(field3.isPackagePrivate()).thenReturn(false);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1, field2, field3
		});

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getAccessibleDeclaredFields(class1);

		// verify
		then(_toTest)//
				.contains(field1, field2)//
				.doesNotContain(field3);
	}

	@Test
	public void test__getAccessibleFields()
	{
		// prepare
		// * field 1
		when(field1.isPublic()).thenReturn(true);

		// * field 2
		when(field2.isPublic()).thenReturn(false);
		when(field2.isPackagePrivate()).thenReturn(true);

		// * field 3
		when(field3.isPublic()).thenReturn(false);
		when(field3.isPackagePrivate()).thenReturn(false);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1, field2, field3
		});
		when(class1.superclass()).thenReturn(class2);

		// * class 2
		when(class2.qualifiedName()).thenReturn(Object.class.getName());

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getAccessibleFields(class1);

		// verify
		verify(class2, never()).fields();
		then(_toTest)//
				.contains(field1, field2)//
				.doesNotContain(field3);
	}

	@Test
	public void test__getAccessibleFields__shouldIncludeInheritedFields()
	{
		// prepare
		// * field 1
		when(field1.isPublic()).thenReturn(true);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[] {});
		when(class1.superclass()).thenReturn(class2);

		// * class 2
		when(class2.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});
		when(class2.superclass()).thenReturn(class3);

		// * class 3
		when(class3.qualifiedName()).thenReturn(Object.class.getName());

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getAccessibleFields(class1);

		// verify
		then(_toTest).contains(field1);
	}

	@Test
	public void test__getPrivateDeclaredFields()
	{
		// prepare
		// * field 1
		when(field1.isPrivate()).thenReturn(true);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1, field2
		});

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getPrivateDeclaredFields(class1);

		// verify
		then(_toTest)//
				.contains(field1)//
				.doesNotContain(field2);
	}
}
