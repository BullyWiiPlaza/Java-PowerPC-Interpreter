package com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers;

import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

public class FloatingPointRegister extends Register<Float>
{
	public FloatingPointRegister()
	{
		super(0f);
	}

	@Override
	public String getHexadecimalValue()
	{
		int intBits = Float.floatToRawIntBits(value);

		return ValueConversions.to32BitHexadecimal(intBits);
	}
}