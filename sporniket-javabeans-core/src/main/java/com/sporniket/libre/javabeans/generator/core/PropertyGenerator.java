/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.io.PrintWriter;

import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;

/**
 * Interface for generating the code snippet of a javabean properties.
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; core</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; core</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
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
 * @version 13.01.01
 * @since 13.01.01
 * 
 */
public interface PropertyGenerator
{
	/**
	 * Output in the given stream the Java code for managing the specified Javabean property.
	 * 
	 * @param out
	 *            outputStream.
	 * @param propertyType
	 *            Parsed property.getType().
	 * @param property
	 *            property to export.
	 * @param bean
	 *            context : containing bean (for javadoc tags generation).
	 * @param pack
	 *            context : containing package (for javadoc tags generation).
	 * @param set
	 *            context : containing set (for javadoc tags generation).
	 */
	void outputPropertyJavaCode(PrintWriter out, PropertyType propertyType, Property property, Bean bean, Package pack, BeanSet set);
}
