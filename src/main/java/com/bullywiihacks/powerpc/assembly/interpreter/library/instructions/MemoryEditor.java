package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

public abstract class MemoryEditor extends AssemblyInstruction
{
	@Override
	public void execute(AssemblyInterpreter interpreter)
	{
		RandomAccessMemory randomAccessMemory = interpreter.getMemory();
		GeneralPurposeRegister[] generalPurposeRegisters = interpreter.getGeneralPurposeRegisters();

		modifyMemory(generalPurposeRegisters, randomAccessMemory);
	}

	public abstract void modifyMemory(GeneralPurposeRegister[] generalPurposeRegisters,
	                                  RandomAccessMemory randomAccessMemory);
}