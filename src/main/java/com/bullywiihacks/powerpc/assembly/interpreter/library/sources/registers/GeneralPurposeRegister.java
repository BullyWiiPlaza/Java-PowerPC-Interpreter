package com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers;

import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

public class GeneralPurposeRegister extends Register<Integer>
{
	public GeneralPurposeRegister()
	{
		super(0);
	}

	@Override
	public String getHexadecimalValue()
	{
		return ValueConversions.to32BitHexadecimal(value);
	}
}