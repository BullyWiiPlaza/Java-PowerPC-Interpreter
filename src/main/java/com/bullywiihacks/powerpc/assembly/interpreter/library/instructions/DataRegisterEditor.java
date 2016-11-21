package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

public abstract class DataRegisterEditor extends AssemblyInstruction
{
	@Override
	public void execute(AssemblyInterpreter interpreter)
	{
		GeneralPurposeRegister[] generalPurposeRegisters = interpreter.getGeneralPurposeRegisters();
		modifyDataRegisters(generalPurposeRegisters);
	}

	public abstract void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters);
}