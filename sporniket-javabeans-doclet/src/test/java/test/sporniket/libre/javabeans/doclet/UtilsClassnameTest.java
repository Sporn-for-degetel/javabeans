package test.sporniket.libre.javabeans.doclet;

import static java.util.Arrays.asList;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.sporniket.libre.javabeans.doclet.UtilsClassname;

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
 * @since 17.09.01
 */
public class UtilsClassnameTest
{
	@Test
	public void test__computeOutputClassname()
	{
		// prepare
		Set<String> _shortables = new HashSet<>(asList("foo.bar.Sample"));
		Map<String, String> _translations = new HashMap<>(1);
		_translations.put("foo.bar.WhateverRaw", "foo.bar.Sample");
		// execute
		String _toTestShortable = UtilsClassname.computeOutputClassname("foo.bar.WhateverRaw", _translations, _shortables);
		String _toTestUnshortable = UtilsClassname.computeOutputClassname("foo.bar.WhoCaresRaw", _translations, _shortables);
		// verify
		then(_toTestShortable).isEqualTo("Sample");
		then(_toTestUnshortable).isEqualTo("foo.bar.WhoCaresRaw");
	}

	@Test
	public void test__computeOutputClassname__noTranslations()
	{
		// prepare
		Set<String> _shortables = new HashSet<>(asList("foo.bar.Sample"));
		// execute
		String _toTestShortable = UtilsClassname.computeOutputClassname("foo.bar.Sample", _shortables);
		String _toTestUnshortable = UtilsClassname.computeOutputClassname("foo.bar.Whatever", _shortables);
		// verify
		then(_toTestShortable).isEqualTo("Sample");
		then(_toTestUnshortable).isEqualTo("foo.bar.Whatever");
	}

	@Test
	public void test__getPackageName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.getPackageName(Object.class.getName());
		// verify
		then(_toTest).isEqualTo(Object.class.getPackage().getName());
	}

	@Test
	public void test__getSimpleName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.getSimpleName(Object.class.getName());
		// verify
		then(_toTest).isEqualTo(Object.class.getSimpleName());
	}

	@Test
	public void test__getTranslationMapWhenPojosAreSuffixed()
	{
		// prepare
		Set<String> _registry = new HashSet<>(asList("foo.bar.Bar", "foo.BearRaw", "foo.bar.Raw", "foo.bar.sampleRaw"));
		Set<String> _sourcePackages = new HashSet<>(asList("foo.bar", "foo.bar.bir"));
		// execute
		Map<String, String> _toTest = UtilsClassname.getTranslationMapWhenPojosAreSuffixed(_registry, _sourcePackages, "Raw");
		// verify
		then(_toTest).hasSize(1);
		then(_toTest.get("foo.bar.sampleRaw")).isEqualTo("foo.bar.sample");
	}

	@Test
	public void test__removeSuffixFromClassName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.removeSuffixFromClassName("foobar", "bar");
		// verify
		then(_toTest).isEqualTo("foo");
	}

	@Test
	public void test__updateShortClassnameMappingFromClassnames()
	{
		// prepare
		List<String> _registry = Arrays.asList("foo.bar.bar", "foo.foo.bar", "foo.fee");
		Map<String, String> _mapping = new HashMap<>(_registry.size());
		// execute
		UtilsClassname.updateShortClassnameMappingFromClassnames(_mapping, _registry);
		// verify
		then(_mapping).hasSize(2);
		then(_mapping.get("bar")).isEqualTo("foo.bar.bar");
		then(_mapping.get("fee")).isEqualTo("foo.fee");
	}
}
