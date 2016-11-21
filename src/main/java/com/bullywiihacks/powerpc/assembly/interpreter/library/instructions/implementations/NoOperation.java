package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;

public class NoOperation extends AssemblyInstruction
{
	@Override
	public void execute(AssemblyInterpreter interpreter)
	{
		// Nothing
	}

	@Override
	public String toString()
	{
		return getMnemonicSpaced();
	}

	@Override
	public String getMnemonic()
	{
		return "nop";
	}

	@Override
	protected boolean hasArguments()
	{
		return false;
	}

	@Override
	public AssemblyInstruction parse(String instruction)
	{
		if (instruction.equals(getMnemonic()))
		{
			return new NoOperation();
		}

		throw new IllegalArgumentException("Unexpected junk after "
				+ getMnemonic()
				+ ": "
				+ instruction.substring(getMnemonic().length(), instruction.length()));
	}
}