package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments;

import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyInterpreter;

public class ArgumentRegister
{
	private static int registersMinIndex = 0;
	private static int registersMaxIndex = PPCAssemblyInterpreter.REGISTERS_COUNT - 1;

	private int registerIndex;

	public ArgumentRegister(int registerIndex)
	{
		if (isValidRegister(registerIndex))
		{
			this.registerIndex = registerIndex;
		} else
		{
			throw new IllegalArgumentException("The register index is "
					+ registerIndex + " but must be between "
					+ registersMinIndex + " and " + registersMaxIndex + "!");
		}
	}

	private boolean isValidRegister(int registerIndex)
	{
		return (registerIndex <= registersMaxIndex)
				&& (registerIndex >= registersMinIndex);
	}

	public int getRegisterIndex()
	{
		return registerIndex;
	}

	public static ArgumentRegister parse(String argumentRegisterText)
	{
		if (!argumentRegisterText.startsWith("r"))
		{
			throw new IllegalArgumentException(
					"The argument register was " + argumentRegisterText + " but has to start with an \"r\"!");
		} else
		{
			int registerIndex = Integer.parseInt(argumentRegisterText.replace("r", ""));

			return new ArgumentRegister(registerIndex);
		}
	}

	@Override
	public String toString()
	{
		return "r" + registerIndex;
	}
}