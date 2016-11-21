package com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers;

public abstract class Register<T extends Number>
{
	protected T value;

	public Register(T value)
	{
		setValue(value);
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public T getValue()
	{
		return value;
	}

	public abstract String getHexadecimalValue();
}