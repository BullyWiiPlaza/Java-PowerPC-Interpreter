package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.DataRegisterEditor;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

public class AddImmediate extends DataRegisterEditor
{
	private ArgumentRegister destinationArgument;
	private ArgumentRegister sourceArgument;
	private Immediate immediate;

	public AddImmediate(ArgumentRegister destinationArgument,
	                    ArgumentRegister sourceArgument,
	                    Immediate immediate)
	{
		this.destinationArgument = destinationArgument;
		this.sourceArgument = sourceArgument;
		this.immediate = immediate;
	}

	@Override
	public String toString()
	{
		return getMnemonicSpaced() + destinationArgument
				+ " " + sourceArgument
				+ " " + immediate;
	}

	@Override
	public String getMnemonic()
	{
		return "addi";
	}

	@Override
	public AssemblyInstruction parse(String instruction)
	{
		instruction = instruction.replaceFirst(getMnemonic() + " ", "");
		String[] components = instruction.split(", ");
		destinationArgument = ArgumentRegister.parse(components[0]);
		sourceArgument = ArgumentRegister.parse(components[1]);
		immediate = Immediate.parse(components[2]);

		return new AddImmediate(destinationArgument, sourceArgument, immediate);
	}

	@Override
	public void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters)
	{
		GeneralPurposeRegister destinationRegister = destinationArgument.toGeneralPurposeRegister(generalPurposeRegisters);
		GeneralPurposeRegister sourceRegister = sourceArgument.toGeneralPurposeRegister(generalPurposeRegisters);

		int sourceRegisterValue = sourceRegister.getValue();
		int immediateValue = immediate.getValue();

		destinationRegister.setValue(sourceRegisterValue + immediateValue);
	}
}