package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;

public abstract class MemoryEditor extends AssemblyInstruction
{
	@Override
	public void execute(PPCAssemblyInterpreter interpreter)
	{
		RandomAccessMemory randomAccessMemory = interpreter.getMemory();

		modifyMemory(randomAccessMemory);
	}

	public abstract void modifyMemory(RandomAccessMemory randomAccessMemory);
}