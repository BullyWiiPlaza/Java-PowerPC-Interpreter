package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;

public abstract class MemoryEditor extends AssemblyInstruction
{
	@Override
	public void execute(AssemblyInterpreter interpreter)
	{
		RandomAccessMemory randomAccessMemory = interpreter.getMemory();

		modifyMemory(randomAccessMemory);
	}

	public abstract void modifyMemory(RandomAccessMemory randomAccessMemory);
}