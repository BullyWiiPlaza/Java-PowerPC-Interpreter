package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;

public class LoadImmediateShifted extends LoadImmediate
{
	public LoadImmediateShifted(ArgumentRegister register, Immediate immediate)
	{
		super(register, immediate);
	}

	@Override
	public void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters)
	{
		GeneralPurposeRegister targetRegister = register.toGeneralPurposeRegister(generalPurposeRegisters);

		int newValue = immediate.getValue();
		targetRegister.setValue(newValue << 16);
	}

	@Override
	public String getMnemonic()
	{
		return "lis";
	}

	public LoadImmediateShifted parse(String instruction)
	{
		ParsingUtilities parsingUtils = new ParsingUtilities(instruction);
		parsingUtils.splitBlanks();
		parsingUtils.removeCommas();

		ArgumentRegister argumentRegister = ArgumentRegister.parse(parsingUtils.getNextArgument());
		Immediate immediate = Immediate.parse(parsingUtils.getNextArgument());

		return new LoadImmediateShifted(argumentRegister, immediate);
	}
}