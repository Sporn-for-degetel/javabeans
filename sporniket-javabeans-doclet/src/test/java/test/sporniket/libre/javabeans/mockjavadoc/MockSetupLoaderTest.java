/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;

import test.sporniket.libre.javabeans.doclet.TestBase;

/**
 * Tests for {@link MockSetupLoader}.
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
public class MockSetupLoaderTest extends TestBase
{
	@Test
	public void shouldBuildWorkingSetup() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare

		// execute
		MockSetup setup = new MockSetupLoader().load(getDataRessource("classWithInheritedField.json"));

		// verify
		// -- overall checks
		then(setup.getTypes())//
				.hasSize(2)//
				.containsKeys("java.net.URL", "java.util.Date");
		setup.getTypes().forEach((n, t) -> {
			then(t.qualifiedTypeName()).isEqualTo(n);
		});

		then(setup.getFields())//
				.hasSize(2)//
				.containsKeys("TheBaseClass.myDateCreation", "TheClass.mySource");
		setup.getFields().forEach((n, f) -> {
			then(f.qualifiedName()).isEqualTo(n);
		});

		then(setup.getClasses())//
				.hasSize(3)//
				.containsKeys("TheClass", "TheBaseClass", "java.lang.Object");
		setup.getClasses().forEach((n, c) -> {
			then(c.qualifiedName()).isEqualTo(n);
		});

		then(setup.getPackages())//
				.hasSize(1)//
				.containsKeys("");
		setup.getPackages().forEach((n, p) -> {
			then(p.name()).isEqualTo(n);
		});

		// -- field details
		thenFieldHasExpectedFeatures(setup.getFields().get("TheBaseClass.myDateCreation")//
				, "myDateCreation", setup.getTypes().get("java.util.Date")//
				, true//
				, false);
		thenFieldHasExpectedFeatures(setup.getFields().get("TheClass.mySource")//
				, "mySource", setup.getTypes().get("java.net.URL")//
				, true//
				, false);

		// -- class details
		thenClassDocHasExpectedFeatures(setup.getClasses().get("TheBaseClass")//
				, new FieldDoc[]
				{
						setup.getFields().get("TheBaseClass.myDateCreation")
				}//
				, setup.getClasses().get("java.lang.Object")//
				, setup.getPackages().get(""));
		thenClassDocHasExpectedFeatures(setup.getClasses().get("TheClass")//
				, new FieldDoc[]
				{
						setup.getFields().get("TheClass.mySource")
				}//
				, setup.getClasses().get("TheBaseClass")//
				, setup.getPackages().get(""));

	}

	private void thenFieldHasExpectedFeatures(FieldDoc field, String name, Type type, boolean isPackagePrivate, boolean isPublic)
	{
		then(field.name()).isEqualTo(name);
		then(field.type()).isEqualTo(type);
		then(field.isPackagePrivate()).isTrue();
		then(field.isPublic()).isFalse();
	}

	private void thenClassDocHasExpectedFeatures(ClassDoc classdoc, FieldDoc[] fields, ClassDoc superclass,
			PackageDoc containingPackage)
	{
		then(classdoc.fields())//
				.hasSize(fields.length)//
				.contains(fields);
		then(classdoc.superclass()).isEqualTo(superclass);
		then(classdoc.containingPackage()).isEqualTo(containingPackage);
	}
}
