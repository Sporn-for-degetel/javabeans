package com.sporniket.libre.javabeans.doclet.encapsulator;

public interface BuilderGenerator
{
	void outputClassBegin();

	void outputFields();

	void outputSetters();

	void outputClassEnd();

	default void generate()
	{
		outputClassBegin();
		outputFields();
		outputSetters();
		outputClassEnd();
	}
}
