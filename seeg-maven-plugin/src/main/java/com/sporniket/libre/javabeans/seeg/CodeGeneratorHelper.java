package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.StringHelper.uncapitalizeFirstLetter;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Pool of code generators.
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
 * <i>The Sporniket Javabeans Project &#8211; seeg</i> is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; seeg</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 20.04.04
 * @since 20.04.01
 */
public class CodeGeneratorHelper
{
	private static final String PREFIX_ENTITY = "Entity";

	private static final String PREFIX_REPOSITORY = "Dao";

	private static final String PREFIX_FINDER = "FinderOf";

	private static final String PREFIX_ID_CLASS = "IdOf";

	private static final String ANNOTATION__NOT_NULL = "@javax.validation.constraints.NotNull ";

	private static final String JAVA_EXTENSION_SUFFIX = ".java";

	static final CodeGenerator<DefEnum> FROM_DEF_ENUM = (specs, targetDir, targetPackage, out) -> {
		File target = new File(targetDir, specs.nameInJava + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			_out.println(format("public enum %s {%s;}", specs.nameInJava, String.join(",", specs.values)));
			_out.close();
		}
		catch (

		Exception _error)
		{
			_error.printStackTrace(out);
		}
	};

	static final CodeGenerator<DefTable> FROM_DEF_TABLE_TO_FINDER = (specs, targetDir, targetPackage, out) -> {
		// sanity check : require pk
		if (specs.pkeysColumns.isEmpty())
		{
			out.println(format("Table %s has no primary key, cannot generate repository interface !!", specs.nameInDatabase));
			return;
		}
		final String _finderName = PREFIX_FINDER + specs.nameInJava;
		final String _entityName = PREFIX_ENTITY + specs.nameInJava;
		final String _idClassName = PREFIX_ID_CLASS + specs.nameInJava;
		File target = new File(targetDir, _finderName + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			if (null != specs.comment)
			{
				_out.println(format("/** %s. */", specs.comment));
			}
			_out.println("@org.springframework.data.repository.NoRepositoryBean");
			final String _javaTypeOfPrimaryKey = 1 == specs.pkeysColumns.size()
					? specs.columns.get(specs.pkeysColumns.iterator().next()).javaType
					: _idClassName;
			_out.println(format("public interface %s extends org.springframework.data.jpa.repository.JpaRepository<%s, %s> {",
					_finderName, _entityName, _javaTypeOfPrimaryKey));
			specs.selectors.values().forEach(s -> {
				final String _return = s.unique ? _entityName : format("java.util.List<%s>", _entityName);
				final String _find = s.unique ? "findBy" : "findAllBy";
				final String _queryMethod = s.columns.stream()//
						.map(c -> specs.columns.get(c))//
						.map(c -> c.nameInJava)//
						.collect(joining("And"));
				final String _args = s.columns.stream()//
						.map(c -> specs.columns.get(c))//
						.map(c -> format("%s %s", c.javaType, StringHelper.uncapitalizeFirstLetter(c.nameInJava)))//
						.collect(joining(", "));
				_out.println(format("  %s %s%s(%s);", _return, _find, _queryMethod, _args));
			});
			_out.println("}");
			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}

	};

	static final CodeGenerator<DefTable> FROM_DEF_TABLE_TO_REPOSITORY = (specs, targetDir, targetPackage, out) -> {
		// sanity check : require pk
		if (specs.pkeysColumns.isEmpty())
		{
			out.println(format("Table %s has no primary key, cannot generate repository interface !!", specs.nameInDatabase));
			return;
		}
		final String _repoName = PREFIX_REPOSITORY + specs.nameInJava;
		final String _finderName = PREFIX_FINDER + specs.nameInJava;
		File target = new File(targetDir, _repoName + JAVA_EXTENSION_SUFFIX);

		// the repository class should be generated if absent.
		// then it is manually edited and thus should not be overwritten.
		if (target.exists())
		{
			out.println(format("Repository class already exist, skip : %s", target.getAbsolutePath()));
			return;
		}

		// ok proceed
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			if (null != specs.comment)
			{
				_out.println(format("/** %s. */", specs.comment));
			}
			_out.println("@org.springframework.stereotype.Repository");
			_out.println(format("public interface %s extends %s {", _repoName, _finderName));
			_out.println("  // write your own method here");
			_out.println("}");
			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}

	};

	static final CodeGenerator<DefTable> FROM_DEF_TABLE_TO_ENTITY = (specs, targetDir, targetPackage, out) -> {
		final String _entityName = PREFIX_ENTITY + specs.nameInJava;
		final String _idClassName = PREFIX_ID_CLASS + specs.nameInJava;
		File target = new File(targetDir, _entityName + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			if (null != specs.comment)
			{
				_out.println(format("/** %s. */", specs.comment));
			}
			_out.println("@javax.persistence.Entity");
			_out.println(format("@javax.persistence.Table(name = \"%s\")", specs.nameInDatabase));
			if (specs.pkeysColumns.size() > 1)
			{
				_out.println(format("@javax.persistence.IdClass(%s.class)", _idClassName));
			}
			if (specs.useEnums && StringUtils.isNotBlank(specs.typeDefPgEnum))
			{
				_out.println(format("@org.hibernate.annotations.TypeDef(name = \"pgsql_enum\", typeClass = %s.class)",
						specs.typeDefPgEnum));
			}
			_out.println(format("public class %s {", _entityName));

			// fields
			Map<String, String> _fieldNames = new HashMap<>(specs.columns.size());
			new TreeSet<>(specs.columns.keySet()).stream()//
					.map(k -> specs.columns.get(k))//
					.forEach(colSpecs -> {
						// field
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   *%s. \n   */", colSpecs.comment));
						}
						_out.println(format("  @javax.persistence.Column(name = \"%s\")", colSpecs.nameInDatabase));
						if (colSpecs.isEnum)
						{
							_out.println("  @javax.persistence.Enumerated(javax.persistence.EnumType.STRING)");
							if (StringUtils.isNotBlank(specs.typeDefPgEnum))
							{
								_out.println("  @org.hibernate.annotations.Type(type = \"pgsql_enum\")");
							}
						}
						if (colSpecs.generated)
						{
							if (null != colSpecs.generationStrategy)
							{
								if (null != colSpecs.sequenceGenerator)
								{
									_out.println(format(
											"  @javax.persistence.GeneratedValue(strategy = %s, generator = \"generator__%s\")",
											colSpecs.generationStrategy, colSpecs.sequenceGenerator));
									_out.println(format(
											"  @javax.persistence.SequenceGenerator(name = \"generator__%s\", sequenceName = \"%s\", allocationSize = 1)",
											colSpecs.sequenceGenerator, colSpecs.sequenceGenerator));
								}
								else
								{
									_out.println(format("  @javax.persistence.GeneratedValue(strategy = %s)",
											colSpecs.generationStrategy));
								}
							}
							else
							{
								_out.println("  @javax.persistence.GeneratedValue");
							}
						}
						if (specs.pkeysColumns.contains(colSpecs.nameInDatabase))
						{
							_out.println("  @javax.persistence.Id");
						}
						if (null != colSpecs.temporalMapping)
						{
							_out.println(format("  %s", colSpecs.temporalMapping));
						}
						if (colSpecs.notNullable)
						{
							_out.println("  " + ANNOTATION__NOT_NULL);
						}

						final String _fieldName = StringHelper.uncapitalizeFirstLetter(colSpecs.nameInJava);
						_fieldNames.put(colSpecs.nameInDatabase, _fieldName);

						_out.println(format("  private %s %s ;", colSpecs.javaType, _fieldName));

						// getter
						if (null != colSpecs.comment)
						{
							_out.println(
									format("  /**\n   * %s. \n   *\n   * @returns the current value. \n   */", colSpecs.comment));
						}
						_out.println(
								format("  public %s%s get%s() { return %s ;} ", colSpecs.notNullable ? ANNOTATION__NOT_NULL : "",
										colSpecs.javaType, colSpecs.nameInJava, _fieldName));

						// setter
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   * %s. \n   *\n   * @value the new value. \n   */", colSpecs.comment));
						}
						_out.println(format("  public void set%s(%s%s value) { this.%s = value ;} ", colSpecs.nameInJava,
								colSpecs.notNullable ? ANNOTATION__NOT_NULL : "", colSpecs.javaType, _fieldName));

						// fluent setter
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   * %s. \n   *\n   * @value the new value. \n   */", colSpecs.comment));
						}
						_out.println(format("  public %s with%s(%s%s value) { this.%s = value ; return this ;} ", _entityName,
								colSpecs.nameInJava, colSpecs.notNullable ? ANNOTATION__NOT_NULL : "", colSpecs.javaType,
								_fieldName));
						_out.println();
					});

			// equals
			_out.println();
			_out.println("  public boolean equals(Object obj) {");
			specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.filter(c -> false == c.notNullable)//
					.forEach(c ->

					{
						_out.println(
								format("    if (null == this.%s) return super.equals(obj) ;", _fieldNames.get(c.nameInDatabase)));
					});
			_out.println("    if (null == obj) return false ;");
			_out.println(format("    if (!(obj instanceof %s)) return false ;", _entityName));
			_out.println();
			_out.println(format("    %s _id = (%s) obj ;", _entityName, _entityName));
			specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.forEach(c -> {
						_out.println(format("    if (!java.util.Objects.equals(this.%s, _id.get%s())) return false ;",
								_fieldNames.get(c.nameInDatabase), c.nameInJava));
					});
			_out.println("    return true;");
			_out.println("  }");

			// hashcode
			final String _pkeysAsArgs = specs.pkeysColumns.stream()//
					.map(k -> _fieldNames.get(k))//
					.collect(Collectors.joining(", "));
			_out.println();
			_out.println("  public int hashCode() {");
			specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.filter(c -> false == c.notNullable)//
					.forEach(c -> {
						_out.println(format("    if (null == this.%s) return super.hashCode() ;", _fieldNames.get(c.nameInDatabase),
								c.nameInJava));
					});
			_out.println(format("    return java.util.Objects.hash(%s) ;", _pkeysAsArgs));
			_out.println("  }");

			// to id class
			if (specs.pkeysColumns.size() > 1)
			{
				_out.println();
				_out.println(format("  public %s toIdClass() { return new %s(%s) ;}", _idClassName, _idClassName, _pkeysAsArgs));
			}

			// the end
			_out.println("}");
			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}
	};

	static final CodeGenerator<DefTable> FROM_DEF_TABLE_TO_ID_CLASS = (specs, targetDir, targetPackage, out) -> {
		final String _idClassName = PREFIX_ID_CLASS + specs.nameInJava;
		final String _entityName = PREFIX_ENTITY + specs.nameInJava;
		File target = new File(targetDir, _idClassName + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			_out.println(format("/** Id of {@link %s}. */", _entityName));
			_out.println(format("public class %s implements java.io.Serializable {", _idClassName));

			// serial version id
			final String _unifiedDescription = specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.map(c -> format("%s %s", c.javaType, c.nameInJava))//
					.collect(Collectors.joining("+"));
			_out.println(format("  private static final long serialVersionUID = %dL;", _unifiedDescription.hashCode()));
			_out.println();

			// fields and accessors
			Map<String, String> _fieldNames = new HashMap<>(specs.columns.size());
			new TreeSet<>(specs.pkeysColumns).stream()//
					.map(k -> specs.columns.get(k))//
					.forEach(colSpecs -> {
						// field
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   *%s. \n   */", colSpecs.comment));
						}
						if (colSpecs.notNullable)
						{
							_out.println("  " + ANNOTATION__NOT_NULL);
						}
						String _fieldName = uncapitalizeFirstLetter(colSpecs.nameInJava);
						_fieldNames.put(colSpecs.nameInDatabase, _fieldName);
						_out.println(format("  private final %s %s ;", colSpecs.javaType, _fieldName));

						// getter
						if (null != colSpecs.comment)
						{
							_out.println(
									format("  /**\n   * %s. \n   *\n   * @returns the current value. \n   */", colSpecs.comment));
						}
						_out.println(
								format("  public %s%s get%s() { return %s ;} ", colSpecs.notNullable ? ANNOTATION__NOT_NULL : "",
										colSpecs.javaType, colSpecs.nameInJava, _fieldName));

						// done
						_out.println();
					});

			// constructor
			String _constructorArgs = specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.map(c -> format("%s%s %s", c.notNullable ? ANNOTATION__NOT_NULL : "", c.javaType,
							_fieldNames.get(c.nameInDatabase)))//
					.collect(Collectors.joining(", "));
			_out.println();
			_out.println(format("  public %s(%s) {", _idClassName, _constructorArgs));
			specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.forEach(c -> {
						String _fieldName = _fieldNames.get(c.nameInDatabase);
						_out.println(format("    this.%s = %s ;", _fieldName, _fieldName));
					});
			_out.println("  }");

			// equals
			_out.println();
			_out.println("  public boolean equals(Object obj) {");
			_out.println("    if (null == obj) return false ;");
			_out.println(format("    if (!(obj instanceof %s)) return false ;", _idClassName));
			_out.println();
			_out.println(format("    %s _id = (%s) obj ;", _idClassName, _idClassName));
			specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.forEach(c -> {
						_out.println(format("    if (!java.util.Objects.equals(%s, _id.get%s())) return false ;",
								_fieldNames.get(c.nameInDatabase), c.nameInJava));
					});
			_out.println("    return true;");
			_out.println("  }");

			// hashcode
			final String _hashComponents = specs.pkeysColumns.stream()//
					.map(k -> specs.columns.get(k))//
					.map(c -> _fieldNames.get(c.nameInDatabase))//
					.collect(Collectors.joining(", "));
			_out.println();
			_out.println(format("  public int hashCode() { return java.util.Objects.hash(%s) ; }", _hashComponents));

			_out.println("}");

			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}
	};

	private static void printPackage(String targetPackage, PrintStream _out)
	{
		_out.println(format("package %s;", targetPackage));
	}

}
