package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;
import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs_Builder;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.Type;
import com.sun.javadoc.TypeVariable;
import com.sun.javadoc.WildcardType;

/**
 * Utility class for {@link ClassDoc}
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
 * @since 17.09.00
 */
public final class UtilsClassDoc
{
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
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the
	 * terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License,
	 * or (at your option) any later version.
	 *
	 * <p>
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
	 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
	 * General Public License for more details.
	 *
	 * <p>
	 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library
	 * &#8211; core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
	 *
	 * <hr>
	 *
	 * @author David SPORN
	 * @version 20.05.00
	 * @since 17.09.00
	 */
	public static class ClassDeclaration
	{
		/**
		 * Output the type name.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		public static void outputType(StringBuilder into, Type toOutput, Map<String, String> translations, Set<String> shortables)
		{

			// Either Parametrized type ...
			final ParameterizedType _pType = toOutput.asParameterizedType();
			if (null != _pType)
			{
				outputType__ParameterizedType(into, _pType, translations, shortables);
				return; // done
			}

			// ... or Wildcard type ...
			final WildcardType _wType = toOutput.asWildcardType();
			if (null != _wType)
			{
				outputType__WildcardType(into, _wType, translations, shortables);
				return; // done
			}

			// ... or type variable ...
			final TypeVariable _tVar = toOutput.asTypeVariable();
			if (null != _tVar)
			{
				outputType__TypeVariable(into, _tVar, translations, shortables);
				return; // done
			}

			// ... or normal type.
			into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
		}

		/**
		 * Output the type name when the type is a parametrized type.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		private static void outputType__ParameterizedType(StringBuilder into, ParameterizedType toOutput,
				Map<String, String> translations, Set<String> shortables)
		{
			into.append(computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
			final Type[] _typeArguments = toOutput.typeArguments();
			for (int _i = 0; _i < _typeArguments.length; _i++)
			{
				into.append((0 == _i) ? "<" : ", ");
				outputType(into, _typeArguments[_i], translations, shortables);
			}
			into.append(">");
		}

		/**
		 * Output the type name when the type is a type variable.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		private static void outputType__TypeVariable(StringBuilder into, TypeVariable toOutput, Map<String, String> translations,
				Set<String> shortables)
		{
			into.append(toOutput.typeName());
			final Type[] _bounds = toOutput.bounds();
			if (_bounds.length > 0)
			{
				into.append(" extends ");
				outputType(into, _bounds[0], translations, shortables);
			}
		}

		/**
		 * Output the type name when the type is a wildcard type.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		private static void outputType__WildcardType(StringBuilder into, WildcardType toOutput, Map<String, String> translations,
				Set<String> shortables)
		{
			into.append("?");
			final Type[] _extendsBounds = toOutput.extendsBounds();
			if (_extendsBounds.length > 0)
			{
				into.append(" extends ");
				outputType(into, _extendsBounds[0], translations, shortables);
			}
			final Type[] _superBounds = toOutput.superBounds();
			if (_superBounds.length > 0)
			{
				into.append(" super ");
				outputType(into, _superBounds[0], translations, shortables);
			}
		}
	}

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
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the
	 * terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License,
	 * or (at your option) any later version.
	 *
	 * <p>
	 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
	 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
	 * General Public License for more details.
	 *
	 * <p>
	 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library
	 * &#8211; core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
	 *
	 * <hr>
	 *
	 * @author David SPORN
	 * @version 20.05.00
	 * @since 17.09.00
	 */
	public static class TypeInvocation
	{
		/**
		 * Output the type name.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		public static void outputType(StringBuilder into, Type toOutput, Map<String, String> translations, Set<String> shortables)
		{

			// Either Parametrized type ...
			final ParameterizedType _pType = toOutput.asParameterizedType();
			if (null != _pType)
			{
				outputType__ParameterizedType(into, _pType, translations, shortables);
				return; // done
			}

			// ... or Wildcard type ...
			final WildcardType _wType = toOutput.asWildcardType();
			if (null != _wType)
			{
				outputType__WildcardType(into, _wType, translations, shortables);
				return; // done
			}

			// ... or type variable ...
			final TypeVariable _tVar = toOutput.asTypeVariable();
			if (null != _tVar)
			{
				outputType__TypeVariable(into, _tVar, translations, shortables);
				return; // done
			}

			// ... or normal type.
			into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
		}

		/**
		 * Output the type name when the type is a parametrized type.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		private static void outputType__ParameterizedType(StringBuilder into, ParameterizedType toOutput,
				Map<String, String> translations, Set<String> shortables)
		{
			into.append(computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
			final Type[] _typeArguments = toOutput.typeArguments();
			outputTypeArguments(into, _typeArguments, translations, shortables);
		}

		/**
		 * Output the type name when the type is a type variable.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		private static void outputType__TypeVariable(StringBuilder into, TypeVariable toOutput, Map<String, String> translations,
				Set<String> shortables)
		{
			into.append(toOutput.typeName());
		}

		/**
		 * Output the type name when the type is a wildcard type.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param toOutput
		 *            the class to output.
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		private static void outputType__WildcardType(StringBuilder into, WildcardType toOutput, Map<String, String> translations,
				Set<String> shortables)
		{
			into.append("?");
		}

		/**
		 * Output the type arguments.
		 *
		 * @param into
		 *            the buffer into which to output the class name.
		 * @param arguments
		 *            the arguments of the type
		 * @param translations
		 *            translation map.
		 * @param shortables
		 *            set of class that can be output as a simple name instead of fully qualified.
		 */
		public static void outputTypeArguments(StringBuilder into, final Type[] arguments, Map<String, String> translations,
				Set<String> shortables)
		{
			if (arguments.length > 0)
			{
				for (int _i = 0; _i < arguments.length; _i++)
				{
					into.append((0 == _i) ? "<" : ", ");
					into.append(computeOutputClassname(arguments[_i].qualifiedTypeName(), translations, shortables));
				}
				into.append(">");
			}
		}
	}

	/**
	 * List of "markers intenfaces", i.e. interfaces that require no methods to implement.
	 */
	private static final Set<String> MARKERS_INTERFACES = new HashSet<>(
			Arrays.asList(Serializable.class.getName(), Cloneable.class.getName()));

	private static final Set<String> PRIMITIVE_TYPES = new HashSet<>(Arrays.asList(//
			boolean.class.getName()//
			, byte.class.getName()//
			, char.class.getName()//
			, int.class.getName()//
			, long.class.getName()//
			, float.class.getName()//
			, double.class.getName()//
			, short.class.getName()//
	));

	public static String computeOutputType(Type toOutput, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _buffer = new StringBuilder();
		ClassDeclaration.outputType(_buffer, toOutput, translations, shortables);
		return _buffer.toString();
	}

	public static String computeOutputType_invocation(Type toOutput, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _buffer = new StringBuilder();
		TypeInvocation.outputType(_buffer, toOutput, translations, shortables);
		return _buffer.toString();
	}

	/**
	 * @param toScan
	 *            an interface
	 * @return true if there are any abstract method.
	 */
	private static boolean hasAbstractMethods(ClassDoc toScan)
	{
		return !Arrays.asList(toScan.methods(false))//
				.stream()//
				.filter(MethodDoc::isAbstract)//
				.collect(Collectors.toList())//
				.isEmpty();
	}

	/**
	 * Output the class name for the bean type instanciation, e.g. "<code>... pojo = new SampleBasicBean&lt;&gt;() ;</code>".
	 *
	 * @param into
	 *            the buffer into which to output the class name.
	 * @param toOutput
	 *            the class to output.
	 * @param translations
	 *            translation map.
	 * @param shortables
	 *            set of class that can be output as a simple name instead of fully qualified.
	 */
	public static void outputClassName__beanInstanciation(StringBuilder into, ClassDoc toOutput, Map<String, String> translations,
			Set<String> shortables)
	{
		into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
		final TypeVariable[] _typeArguments = toOutput.typeParameters();
		if (_typeArguments.length > 0)
		{
			into.append("<>");
		}
	}

	/**
	 * Output the class name for the bean type declaration, e.g.
	 * "<code>private final SampleBasicBean&lt;T, R&gt; bean = ...</code>".
	 *
	 * @param into
	 *            the buffer into which to output the class name.
	 * @param toOutput
	 *            the class to output.
	 * @param translations
	 *            translation map.
	 * @param shortables
	 *            set of class that can be output as a simple name instead of fully qualified.
	 */
	public static void outputClassName__beanType(StringBuilder into, ClassDoc toOutput, Map<String, String> translations,
			Set<String> shortables)
	{
		into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
		final TypeVariable[] _typeArguments = toOutput.typeParameters();
		if (_typeArguments.length > 0)
		{
			for (int _i = 0; _i < _typeArguments.length; _i++)
			{
				into.append((0 == _i) ? "<" : ", ");
				into.append(_typeArguments[_i].simpleTypeName());
			}
			into.append(">");
		}
	}

	/**
	 * Output the class name for the class declaration, e.g.
	 * "<code>public class X&lt;A, B extends Foo, C super Bar, ...&gt; [extends... implements...]</code>".
	 *
	 * @param into
	 *            the buffer into which to output the class name.
	 * @param toOutput
	 *            the class to output.
	 * @param translations
	 *            translation map.
	 * @param shortables
	 *            set of class that can be output as a simple name instead of fully qualified.
	 */
	public static void outputClassName__classDeclaration(StringBuilder into, ClassDoc toOutput, Map<String, String> translations,
			Set<String> shortables)
	{
		into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), translations, shortables));
		final TypeVariable[] _typeArguments = toOutput.typeParameters();
		outputClassParameters__classDeclaration(into, _typeArguments, translations, shortables);
	}

	/**
	 * Output the class name for the pojo type instanciation, e.g. "<code>... pojo = new SampleBasicBeanRaw&lt;&gt;() ;</code>".
	 *
	 * @param into
	 *            the buffer into which to output the class name.
	 * @param toOutput
	 *            the class to output.
	 * @param translations
	 *            translation map.
	 * @param shortables
	 *            set of class that can be output as a simple name instead of fully qualified.
	 */
	public static void outputClassName__pojoInstanciation(StringBuilder into, ClassDoc toOutput, Map<String, String> translations,
			Set<String> shortables)
	{
		into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), shortables));
		final TypeVariable[] _typeArguments = toOutput.typeParameters();
		if (_typeArguments.length > 0)
		{
			into.append("<>");
		}
	}

	/**
	 * Output the class name for the pojo type declaration, e.g.
	 * "<code>private final SampleBasicBeanRaw&lt;T, R&gt; pojo = ...</code>".
	 *
	 * @param into
	 *            the buffer into which to output the class name.
	 * @param toOutput
	 *            the class to output.
	 * @param translations
	 *            translation map.
	 * @param shortables
	 *            set of class that can be output as a simple name instead of fully qualified.
	 */
	public static void outputClassName__pojoType(StringBuilder into, ClassDoc toOutput, Map<String, String> translations,
			Set<String> shortables)
	{
		into.append(UtilsClassname.computeOutputClassname(toOutput.qualifiedTypeName(), shortables));
		final TypeVariable[] _typeArguments = toOutput.typeParameters();
		if (_typeArguments.length > 0)
		{
			for (int _i = 0; _i < _typeArguments.length; _i++)
			{
				into.append((0 == _i) ? "<" : ", ");
				into.append(_typeArguments[_i].simpleTypeName());
			}
			into.append(">");
		}
	}

	/**
	 * Output the parameters of the class.
	 *
	 * @param into
	 *            the buffer into which to output the class name.
	 * @param parameters
	 *            the parameters to output.
	 * @param translations
	 *            translation map.
	 * @param shortables
	 *            set of class that can be output as a simple name instead of fully qualified.
	 */
	public static void outputClassParameters__classDeclaration(StringBuilder into, final TypeVariable[] parameters,
			Map<String, String> translations, Set<String> shortables)
	{
		if (parameters.length > 0)
		{
			for (int _i = 0; _i < parameters.length; _i++)
			{
				into.append((0 == _i) ? "<" : ", ");
				ClassDeclaration.outputType__TypeVariable(into, parameters[_i], translations, shortables);
			}
			into.append(">");
		}
	}

	/**
	 * @param toScan
	 *            the class to scan.
	 * @return <code>true</code> when the class is abstract or implements any interface that require to implement a method.
	 */
	public static boolean shouldBeAbstract(ClassDoc toScan)
	{
		boolean result = toScan.isAbstract();
		if (!result && (toScan.interfaceTypes() != null) && (toScan.interfaceTypes().length > 0))
		{
			result = !Arrays.asList(toScan.interfaceTypes()).stream()//
					.filter(t -> !MARKERS_INTERFACES.contains(t.qualifiedTypeName()))//
					.filter(t -> hasAbstractMethods(t.asClassDoc())).collect(Collectors.toList())//
					.isEmpty();
		}
		return result;
	}

	/**
	 * Add into a collection of 'known classes' the specified class, its superclass and implemented interfaces, and the type of its
	 * public fields.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the class to scan.
	 */
	@Deprecated
	public static void updateKnowClasses(Collection<String> knownClasses, ClassDoc toScan)
	{
		final Predicate<String> _isNotRegistered = i -> !knownClasses.contains(i);
		final Consumer<String> _registerKnownClass = i -> knownClasses.add(i); // do not support parametrized types
		final Consumer<Type> _processType = t -> updateKnownClasses(knownClasses, t);

		Arrays.asList(toScan.qualifiedName(), toScan.superclass().qualifiedName()).stream().filter(_isNotRegistered)
				.forEach(_registerKnownClass);

		Arrays.asList(toScan.interfaces()).stream().map(ClassDoc::qualifiedName).filter(_isNotRegistered)
				.forEach(_registerKnownClass);
		UtilsFieldDoc.getAccessibleFields(toScan).stream().map(FieldDoc::type).forEach(_processType);
		UtilsFieldDoc.getPrivateDeclaredFields(toScan).stream().map(FieldDoc::type).forEach(_processType);
	}

	/**
	 * Add into a collection of 'classes to import' the specified class, its superclass and implemented interfaces, and the type of
	 * its public fields.
	 *
	 * @param toScan
	 *            the class to scan.
	 */
	public static Collection<ImportSpecs> updateKnownClasses(ClassDoc toScan)
	{
		final Map<String, ImportSpecs> _knownClasses = new HashMap<>();
		updateKnownClasses(_knownClasses, toScan, true, false);
		return _knownClasses.values().parallelStream()//
				.filter(i -> !PRIMITIVE_TYPES.contains(i.getClassName())).collect(Collectors.toList());
	}

	/**
	 * Add into a collection of 'known classes' the specified type, and the type of its parameters if any.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the type to scan.
	 */
	@Deprecated
	private static void updateKnownClasses(Collection<String> knownClasses, Type toScan)
	{
		if (null != toScan.asTypeVariable())
		{
			// skip type variables
			return;
		}
		final ParameterizedType _pt = toScan.asParameterizedType();
		if (null != _pt)
		{
			Arrays.asList(_pt.typeArguments()).forEach(t -> updateKnownClasses(knownClasses, t));
		}
		final String _qualifiedTypeName = toScan.qualifiedTypeName();
		if (!knownClasses.contains(_qualifiedTypeName))
		{
			knownClasses.add(_qualifiedTypeName);
		}
	}

	/**
	 * Add into a collection of 'classes to import' the specified class, its superclass and implemented interfaces, and the type of
	 * its public fields.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the class to scan.
	 * @param isDirectlyRequired
	 *            <code>true</code> when the import is required for the java class to generate. <code>false</code> when the class is
	 *            required only for a builder of class.
	 * @param isRequiredByAnnotation
	 *            <code>true</code> when the import is required by an annotation.
	 */
	private static void updateKnownClasses(Map<String, ImportSpecs> knownClasses, ClassDoc toScan, boolean isDirectlyRequired,
			boolean isRequiredByAnnotation)
	{
		final Predicate<String> _isNotRegistered = i -> !knownClasses.containsKey(i);
		final Consumer<String> _registerKnownClass = //
				i -> knownClasses.put(i, new ImportSpecs_Builder()//
						.withClassName(i)//
						.withDirectlyRequired(isDirectlyRequired)//
						.withAnnotation(isRequiredByAnnotation)//
						.done()); // do not support parametrized types

		final Consumer<Type> _processTypeAnnotation = t -> updateKnownClasses(knownClasses, t, isDirectlyRequired, true);
		final Consumer<FieldDoc> _processDirectField = f -> {
			updateKnownClasses(knownClasses, f.type(), isDirectlyRequired, false);
			if (null != f.annotations())
			{
				Arrays.asList(f.annotations()).stream()//
						.filter(d -> 0 == d.elementValues().length)// FIXME do not support parameters
						.map(AnnotationDesc::annotationType)//
						.forEach(_processTypeAnnotation);
			}
		};

		final Consumer<Type> _processTypeInheritedAnnotation = t -> updateKnownClasses(knownClasses, t, false, true);
		final Consumer<FieldDoc> _processInheritedField = f -> {
			updateKnownClasses(knownClasses, f.type(), false, false);
			if (null != f.annotations())
			{
				Arrays.asList(f.annotations()).stream()//
						.filter(d -> 0 == d.elementValues().length)// FIXME do not support parameters
						.map(AnnotationDesc::annotationType)//
						.forEach(_processTypeInheritedAnnotation);
			}
		};

		// process this class and its superclass
		Arrays.asList(toScan.qualifiedName(), toScan.superclass().qualifiedName()).stream().filter(_isNotRegistered)
				.forEach(_registerKnownClass);

		// process this class interfaces and annotations
		Arrays.asList(toScan.interfaces()).stream().map(ClassDoc::qualifiedName).filter(_isNotRegistered)
				.forEach(_registerKnownClass);
		if (null != toScan.annotations())
		{
			final List<AnnotationDesc> _annotations = Arrays.asList(toScan.annotations());
			_annotations.stream().map(a -> a.annotationType().qualifiedName()).filter(_isNotRegistered)
					.forEach(_registerKnownClass);
			// process annotation parameters, if any
			_annotations.stream().filter(a -> a.elementValues().length > 0)//
					.map(a -> Arrays.asList(a.elementValues()))//
					.flatMap(List::stream)//
					.map(ElementValuePair::value)//
					.map(AnnotationValue::value)//
					.map(v -> {
						// try to find known types or to translate.
						// convert arrays and single values to list.
						Class<?> _vClass = v.getClass();
						if (_vClass.isArray())
						{
							// array of annotationValues
							// support only one level
							return Arrays.asList((AnnotationValue[]) v).stream()//
									.map(Object::toString)//
									.collect(toList());
						}
						else
						{
							List<String> _result = new ArrayList<>(1);
							_result.add(v.toString());
							return _result;
						}
					})//
					.flatMap(List::stream)//
					.filter(v -> {
						// interested in fully qualified class names
						try
						{
							Class.forName(v);
							return true;
						}
						catch (ClassNotFoundException _exception)
						{
							return false;
						}
					})//
					.forEach(_registerKnownClass);
		}

		// process fields
		UtilsFieldDoc.getPrivateDeclaredFields(toScan).stream().forEach(_processDirectField);
		UtilsFieldDoc.getAccessibleDeclaredFields(toScan).stream().forEach(_processDirectField);

		// require to process again accessible declared fields, but they are already registered.
		UtilsFieldDoc.getAccessibleFields(toScan).stream().forEach(_processInheritedField);
	}

	/**
	 * Add into a collection of 'known classes' the specified type, and the type of its parameters if any.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the type to scan.
	 */
	private static void updateKnownClasses(Map<String, ImportSpecs> knownClasses, Type toScan, boolean isDirectlyRequired,
			boolean isRequiredByAnnotation)
	{
		if (null != toScan.asTypeVariable())
		{
			// skip type variables
			return;
		}
		final ParameterizedType _pt = toScan.asParameterizedType();
		if (null != _pt)
		{
			Arrays.asList(_pt.typeArguments())
					.forEach(t -> updateKnownClasses(knownClasses, t, isDirectlyRequired, isRequiredByAnnotation));
		}
		final String _qualifiedTypeName = toScan.qualifiedTypeName();
		if (!knownClasses.containsKey(_qualifiedTypeName))
		{
			knownClasses.put(_qualifiedTypeName, new ImportSpecs_Builder()//
					.withClassName(_qualifiedTypeName)//
					.withDirectlyRequired(isDirectlyRequired)//
					.withAnnotation(isRequiredByAnnotation)//
					.done());
		}
	}

}
