package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.DataRegisterEditor;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

public class LoadImmediate extends DataRegisterEditor
{
	protected ArgumentRegister register;
	protected Immediate immediate;

	public LoadImmediate(ArgumentRegister register, Immediate immediate)
	{
		this.register = register;
		this.immediate = immediate;
	}

	@Override
	public void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters)
	{
		int registerIndex = register.getRegisterIndex();
		GeneralPurposeRegister targetRegister = generalPurposeRegisters[registerIndex];

		int newValue = immediate.getValue();
		targetRegister.setValue(newValue);
	}

	@Override
	public String toString()
	{
		return getMnemonic() + " " + register + ", "
				+ ValueConversions.toHexadecimalNotation(immediate.getValue());
	}

	@Override
	public String getMnemonic()
	{
		return "li";
	}

	public LoadImmediate parse(String loadImmediateInstruction)
	{
		ParsingUtilities parsingUtils = new ParsingUtilities(loadImmediateInstruction);
		parsingUtils.splitBlanks();
		parsingUtils.removeCommas();

		ArgumentRegister argumentRegister = ArgumentRegister.parse(parsingUtils.getNextArgument());
		Immediate immediate = Immediate.parse(parsingUtils.getNextArgument());

		return new LoadImmediate(argumentRegister, immediate);
	}
}