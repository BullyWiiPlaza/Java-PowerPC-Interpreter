package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.DataRegisterEditor;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

public class LoadImmediate extends DataRegisterEditor
{
	ArgumentRegister register;
	Immediate immediate;

	public LoadImmediate(ArgumentRegister register, Immediate immediate)
	{
		this.register = register;
		this.immediate = immediate;
	}

	@Override
	public void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters)
	{
		GeneralPurposeRegister targetRegister = register.toGeneralPurposeRegister(generalPurposeRegisters);

		int newValue = immediate.getValue();
		targetRegister.setValue(newValue);
	}

	@Override
	public String toString()
	{
		return getMnemonicSpaced() + register + ", "
				+ ValueConversions.toHexadecimalNotation(immediate.getValue());
	}

	@Override
	public String getMnemonic()
	{
		return "li";
	}

	public LoadImmediate parse(String instruction)
	{
		ParsingUtilities parsingUtils = new ParsingUtilities(instruction);
		parsingUtils.splitBlanks();
		parsingUtils.removeCommas();

		ArgumentRegister argumentRegister = ArgumentRegister.parse(parsingUtils.getNextArgument());
		Immediate immediate = Immediate.parse(parsingUtils.getNextArgument());

		return new LoadImmediate(argumentRegister, immediate);
	}
}