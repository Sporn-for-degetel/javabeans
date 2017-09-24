/**
 *
 */
package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.updateKnowClasses;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.libre.javabeans.doclet.basic.BasicBuilderGenerator;
import com.sporniket.libre.javabeans.doclet.basic.BasicRawPojoGenerator;
import com.sporniket.libre.javabeans.doclet.basic.Builder;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;

/**
 * Extract a hierarchy of POJO structs from a hierarchy of Javabeans.
 *
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
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
 * @version 17.09.01
 * @since 17.09.01
 */
public class DistillerDoclet
{
	private static String extractOptionName(String optionName)
	{
		return (optionName.startsWith("--")) ? optionName.substring(2) : optionName.substring(1);
	}

	/**
	 * Supports generics and annotations.
	 *
	 * @return {@link LanguageVersion#JAVA_1_5}
	 */
	public static LanguageVersion languageVersion()
	{
		return LanguageVersion.JAVA_1_5;
	}

	/**
	 * Return 1 or 2 for supported args.
	 *
	 * Supported args and their type are found by reflection.
	 *
	 * @param option
	 *            the option name to test.
	 * @return 0, 1 (flag arguments) or 2 (value argument).
	 */
	public static int optionLength(String option)
	{
		try
		{
			final Field _declaredField = DocletOptions.class.getDeclaredField(extractOptionName(option));
			if (String.class.equals(_declaredField.getType()))
			{
				return 2;
			}
			else if (_declaredField.getType().isPrimitive() && _declaredField.getType().equals(boolean.class))
			{
				return 1;
			}
		}
		catch (final Exception _exception)
		{
			return 0;
		}
		return 0;
	}

	private static DocletOptions readOptions(String[][] options)
	{
		final DocletOptions _result = new DocletOptions();
		for (final String[] _option : options)
		{
			final String _optionName = extractOptionName(_option[0]); // remove leading "-" or "--"
			try
			{
				final Field _optionField = _result.getClass().getDeclaredField(_optionName);
				if (String.class.equals(_optionField.getType()))
				{
					_optionField.set(_result, _option[1]);
				}
			}
			catch (NoSuchFieldException | SecurityException _exception)
			{
				continue;
			}
			catch (IllegalArgumentException | IllegalAccessException _exception)
			{
				_exception.printStackTrace();
				continue;
			}
		}
		return _result;
	}

	public static boolean start(RootDoc root)
	{
		final DocletOptions _options = readOptions(root.options());

		new DistillerDoclet().execute(root, _options);

		return true;
	}

	private void execute(RootDoc root, DocletOptions options)
	{
		final List<ClassDoc> _sourceClasses = asList(root.classes());

		final Set<String> _packages = asList(root.specifiedPackages()).stream().map(PackageDoc::name)
				.collect(toCollection(TreeSet::new));
		final Set<String> _classes = _sourceClasses.stream().map(ClassDoc::qualifiedName).collect(toCollection(TreeSet::new));
		final Map<String, String> _translations = getReverseTranslationMapWhenPojosAreSuffixed(_classes, _packages, options.pojoSuffix, options.builderSuffix);

		_sourceClasses.stream() //
				.filter(c -> _translations.containsKey(c.qualifiedName())) //
				.forEach(p -> processPojoClass(p, _translations, options));

	}

	private void generateBuilder(ClassDoc pojo, PrintStream out, Set<String> knownClasses, Map<String, String> translations,
			Set<String> shortables, DocletOptions options)
	{
		new Builder<>(new BasicBuilderGenerator())//
				.withKnownClasses(knownClasses)//
				.withOut(out)//
				.withShortables(shortables)//
				.withSource(pojo)//
				.withTranslations(translations)//
				.withOptions(options)//
				.done().generate();
	}

	private void generatePojo(ClassDoc pojo, PrintStream out, Set<String> knownClasses, Map<String, String> translations,
			Set<String> shortables, DocletOptions options)
	{
		new Builder<>(new BasicRawPojoGenerator())//
				.withKnownClasses(knownClasses)//
				.withOut(out)//
				.withShortables(shortables)//
				.withSource(pojo)//
				.withTranslations(translations)//
				.withOptions(options)//
				.done().generate();
	}

	/**
	 * Get the file to generate the expanded source code.
	 *
	 * @param qualifiedName
	 *            the qualified name of the expanded source code.
	 * @param options
	 *            the options.
	 * @return a File descriptor.
	 */
	private File getFileToGenerate(String qualifiedName, DocletOptions options)
	{
		final String filePath = options.d + File.separatorChar + qualifiedName.replace('.', File.separatorChar) + ".java";
		final File _result = new File(filePath);
		final File _targetFolder = _result.getParentFile();
		if (!_targetFolder.exists())
		{
			_targetFolder.mkdirs();
		}
		return _result;
	}

	private void processPojoClass(ClassDoc pojo, final Map<String, String> translations, DocletOptions options)
	{
		final Set<String> _knownClasses = new TreeSet<>();
		updateKnowClasses(_knownClasses, pojo);

		final Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + translations.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping, _knownClasses);
		updateShortClassnameMappingFromClassnames(_shortNameMapping, translations.values());
		final Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

		try
		{
			PrintStream _out = (null != options.d)
					? new PrintStream(getFileToGenerate(translations.get(pojo.qualifiedName()), options))
					: System.out;
			generatePojo(pojo, _out, _knownClasses, translations, _shortables, options);
			if (null != options.d)
			{
				_out.close();
			}
		}
		catch (final FileNotFoundException _exception)
		{
			_exception.printStackTrace();
		}
	}

}
