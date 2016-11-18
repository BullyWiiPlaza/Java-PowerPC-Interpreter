package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;

public class NoOperation extends AssemblyInstruction
{
	@Override
	public void execute(PPCAssemblyInterpreter interpreter)
	{

	}

	@Override
	public String toString()
	{
		return getMnemonic();
	}

	@Override
	public String getMnemonic()
	{
		return "nop";
	}

	@Override
	public AssemblyInstruction parse(String line)
	{
		if (line.equals(getMnemonic()))
		{
			return new NoOperation();
		}

		throw new IllegalArgumentException("Unexpected junk after "
				+ getMnemonic()
				+ ": "
				+ line.substring(getMnemonic().length(), line.length()));
	}
}