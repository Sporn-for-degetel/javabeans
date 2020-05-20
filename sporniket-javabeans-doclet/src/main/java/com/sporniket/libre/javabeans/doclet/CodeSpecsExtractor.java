/**
 *
 */
package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.CodeSpecsExtractor.ExtractionMode.EXPANDER;
import static com.sporniket.libre.javabeans.doclet.RawCommentProcessorFactory.createRawCommentProcessor;
import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.computeOutputType;
import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.computeOutputType_invocation;
import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.shouldBeAbstract;
import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.updateKnownClasses;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.updateShortClassnameMappingFromClassnames;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getAccessibleDeclaredFields;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getAccessibleFields;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getPrivateDeclaredFields;
import static com.sporniket.strings.StringPredicates.IS_EMPTY;
import static com.sporniket.strings.pipeline.StringTransformation.NULL_TO_EMPTY;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationParameterSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationParameterSpecsSingleValue_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationParameterSpecsValuesArray_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sporniket.strings.pipeline.StringPipelineBuilder;
import com.sporniket.strings.pipeline.StringTransformation;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.TypeVariable;

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
 * @version 20.05.01
 * @since 17.12.00
 */
public class CodeSpecsExtractor
{
	public static enum ExtractionMode
	{
		DISTILLER,
		EXPANDER;
	}

	private static final String JAVA_LANG_DEPRECATED = "java.lang.Deprecated";

	/**
	 * Convert javadoc comment string as string array.
	 */
	private static final Function<String, String[]> TO_JAVADOC_LINES = c -> null != c ? c.split("\n") : null;

	private String extractClassDeclaredTypeArguments(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		final TypeVariable[] _typeArguments = from.typeParameters();
		UtilsClassDoc.outputClassParameters__classDeclaration(_result, _typeArguments, translations, shortables);

		return _result.toString();
	}

	private String extractClassInvokedTypeArguments(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		final TypeVariable[] _typeArguments = from.typeParameters();
		UtilsClassDoc.TypeInvocation.outputTypeArguments(_result, _typeArguments, translations, shortables);

		return _result.toString();
	}

	private List<AnnotationParameterSpecs> extractFieldAnnotationParameters(AnnotationDesc annotation,
			final String annotationQualifiedName, Map<String, String> translations, Set<String> shortables)
	{
		final ElementValuePair[] _elementValues = annotation.elementValues();
		final List<AnnotationParameterSpecs> result = new ArrayList<>(_elementValues.length);

		for (AnnotationDesc.ElementValuePair valuePair : _elementValues)
		{
			final AnnotationTypeElementDoc _element = valuePair.element();
			final AnnotationValue _value = valuePair.value();
			Object _realValue = _value.value();
			Class<?> _valueClass = _realValue.getClass();

			if (!_valueClass.isArray())
			{
				result.add(new AnnotationParameterSpecsSingleValue_Builder()//
						.withName(_element.name())//
						.withValue(translateAnnotationValue(_realValue, translations))//
						.withString(_realValue instanceof String)//
						.done());
			}
			else
			{
				result.add(new AnnotationParameterSpecsValuesArray_Builder()//
						.withName(_element.name())//
						.withValues(Arrays.asList((AnnotationValue[]) _realValue)//
								.stream()//
								.map(AnnotationValue::value)//
								.map(v -> {
									return new AnnotationParameterSpecsSingleValue_Builder()//
											.withValue(translateAnnotationValue(v, translations))//
											.withString(v instanceof String)//
											.done();
								})//
								.collect(toList()))//
						.done());
			}
		}
		return result;
	}

	private List<AnnotationSpecs> extractFieldAnnotations(ClassDoc srcClass, Map<String, String> translations,
			Set<String> shortables)
	{
		List<AnnotationSpecs> _result = new ArrayList<>(srcClass.annotations().length);
		for (AnnotationDesc annotation : srcClass.annotations())
		{
			final String _qualifiedName = annotation.annotationType().qualifiedName();
			boolean _needParametersProcessing = annotation.elementValues().length > 0;

			final AnnotationSpecs_Builder _annotationBuilder = new AnnotationSpecs_Builder()//
					.withType(computeOutputType(annotation.annotationType(), translations, shortables))//
					.withOnBuilder(JAVA_LANG_DEPRECATED.equals(_qualifiedName));

			if (_needParametersProcessing)
			{
				_annotationBuilder
						.withParameters(extractFieldAnnotationParameters(annotation, _qualifiedName, translations, shortables));
			}

			_result.add(_annotationBuilder.done());
		}
		return _result;
	}

	private List<AnnotationSpecs> extractFieldAnnotations(FieldDoc field, Map<String, String> translations, Set<String> shortables,
			DocletOptions options)
	{
		List<AnnotationSpecs> _result = new ArrayList<>(field.annotations().length);
		for (AnnotationDesc annotation : field.annotations())
		{
			final String _qualifiedName = annotation.annotationType().qualifiedName();
			boolean _needParametersProcessing = annotation.elementValues().length > 0;
			final AnnotationSpecs_Builder _annotationBuilder = new AnnotationSpecs_Builder()
					.withType(computeOutputType(annotation.annotationType(), translations, shortables))//
					.withOnField(true)//
					.withOnGetter(options.getAnnotationsToAddToGetters().contains(_qualifiedName))//
					.withOnSetter(options.getAnnotationsToAddToGetters().contains(_qualifiedName))//
					.withOnBuilder(JAVA_LANG_DEPRECATED.equals(_qualifiedName));

			if (_needParametersProcessing)
			{
				_annotationBuilder
						.withParameters(extractFieldAnnotationParameters(annotation, _qualifiedName, translations, shortables));
			}

			_result.add(_annotationBuilder.done());
		}
		return _result;
	}

	private List<FieldSpecs> extractFields(ClassDoc from, Map<String, String> translations, Set<String> shortables,
			DocletOptions options, ExtractionMode mode)
	{
		final boolean _noPrefix = IS_EMPTY.test(options.getBeanFieldPrefix());

		final List<FieldDoc> _directFields = (EXPANDER == mode)
				? getAccessibleDeclaredFields(from)
				: getPrivateDeclaredFields(from);

		final TreeSet<String> _directlyRequiredFields = _directFields//
				.stream()//
				.map(FieldDoc::name)//
				.collect(toCollection(TreeSet::new));

		final StringTransformation _simplePrefixRemover = UtilsString.TransformationFactories
				.buildPrefixRemover(options.getBeanFieldPrefix());
		final StringTransformation _prefixRemover = EXPANDER == mode //
				? new StringPipelineBuilder()//
						.pipeThrough(_simplePrefixRemover)//
						.pipeThrough(UtilsString.Transformations.CAPITALIZER)//
						.done()
				: new StringPipelineBuilder()//
						.pipeThrough(_simplePrefixRemover)//
						.pipeThrough(UtilsString.Transformations.UNCAPITALIZER)//
						.done();

		final Function<? super FieldDoc, ? extends FieldSpecs> _toFieldSpecs = (EXPANDER == mode) ? (f -> {
			final String _unprefixedName = _prefixRemover.transform(f.name());
			String[] _comment = null;
			final String _rawCommentText = f.getRawCommentText();
			if (!IS_EMPTY.test(_rawCommentText))
			{
				final String _processedComment = EXPANDER == mode
						? createRawCommentProcessor(options).apply(_rawCommentText)
						: _rawCommentText;
				_comment = TO_JAVADOC_LINES.apply(_processedComment);
			}
			return new FieldSpecs_Builder()//
					.withNameForField(_unprefixedName)//
					.withArrayMarker(NULL_TO_EMPTY.transform(f.type().dimension()))//
					.withNameForAccessor(_unprefixedName)//
					.withFieldPrefix(_noPrefix ? "this." : options.getBeanFieldPrefix())//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.withBooleanGetter(("boolean".equals(f.type().qualifiedTypeName()))
							|| ("java.lang.Boolean".equals(f.type().qualifiedTypeName())))//
					.withAnnotations(extractFieldAnnotations(f, translations, shortables, options))//
					.withJavadocLines(_comment)//
					.done();
		}) : (f -> {
			final String _unprefixedName = _prefixRemover.transform(f.name());
			return new FieldSpecs_Builder()//
					.withNameForField(_unprefixedName)//
					.withArrayMarker(NULL_TO_EMPTY.transform(f.type().dimension()))//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.withAnnotations(extractFieldAnnotations(f, translations, shortables, options))//
					.done();
		});

		final List<FieldDoc> _fields = (EXPANDER == mode) ? getAccessibleFields(from) : _directFields;
		return _fields.stream()//
				.map(_toFieldSpecs)//
				.collect(toList());
	}

	private String extractInterfaceList(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		final ClassDoc[] _interfaces = from.interfaces();
		if (_interfaces.length > 0)
		{
			for (int _i = 0; _i < _interfaces.length; _i++)
			{
				_result.append((0 == _i) ? "" : ", ")
						.append(computeOutputClassname(_interfaces[_i].name(), translations, shortables));
			}
		}

		return _result.toString();
	}

	public ClassSpecs extractSpecs(ClassDoc from, Map<String, String> translations, DocletOptions options, ExtractionMode mode)
	{
		final Collection<ImportSpecs> _knownClasses = updateKnownClasses(from);

		final Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + translations.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping,
				_knownClasses.stream().map(ImportSpecs::getClassName).collect(toList()));
		updateShortClassnameMappingFromClassnames(_shortNameMapping, translations.values());
		final Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

		final String _rawCommentText = from.getRawCommentText();
		String[] _comment = null;
		if (!IS_EMPTY.test(_rawCommentText))
		{
			final String _processedComment = EXPANDER == mode
					? createRawCommentProcessor(options).apply(_rawCommentText)
					: _rawCommentText;
			_comment = TO_JAVADOC_LINES.apply(_processedComment);
		}

		return new ClassSpecs_Builder()//
				.withImports(_knownClasses)// FIXME SORT IN NATURAL ORDER
				.withClassName(computeOutputClassname(from.qualifiedTypeName(), translations, _shortables))//
				.withPackageName(from.containingPackage().name())//
				.withDeclaredTypeArguments(extractClassDeclaredTypeArguments(from, translations, _shortables))//
				.withInvokedTypeArguments(extractClassInvokedTypeArguments(from, translations, _shortables))//
				.withFields(extractFields(from, translations, _shortables, options, mode))//
				.withAbstractRequired(shouldBeAbstract(from))//
				.withSuperClassName(extractSuperClassName(from.superclass(), translations, _shortables))//
				.withInterfaceList(extractInterfaceList(from, translations, _shortables))//
				.withAnnotations(extractFieldAnnotations(from, translations, _shortables))//
				.withJavadocLines(_comment)//
				.done();
	}

	private String extractSuperClassName(ClassDoc superclass, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		if (!Object.class.getName().equals(superclass.qualifiedName()))
		{
			_result//
					.append(computeOutputClassname(superclass.qualifiedTypeName(), translations, shortables))//
					.append(extractClassInvokedTypeArguments(superclass, translations, shortables));
		}

		return _result.toString();
	}

	private String translateAnnotationValue(Object value, Map<String, String> translations)
	{
		String _asString = value.toString();
		if (value instanceof String)
		{
			_asString = _asString.replace("\\", "\\\\");
			_asString = _asString.replace("\"", "\\\"");
		}
		else
		{
			for (Map.Entry<String, String> translation : translations.entrySet())
			{
				_asString = _asString.replace(translation.getKey(), translation.getValue());
			}
		}
		return _asString;
	}
}
