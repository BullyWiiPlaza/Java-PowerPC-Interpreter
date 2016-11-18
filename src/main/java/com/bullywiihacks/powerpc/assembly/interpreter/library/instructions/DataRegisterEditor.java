package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.GeneralPurposeRegister;

public abstract class DataRegisterEditor extends AssemblyInstruction
{
	@Override
	public void execute(PPCAssemblyInterpreter interpreter)
	{
		GeneralPurposeRegister[] generalPurposeRegisters = interpreter.getGeneralPurposeRegisters();
		modifyDataRegisters(generalPurposeRegisters);
	}

	public abstract void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters);
}