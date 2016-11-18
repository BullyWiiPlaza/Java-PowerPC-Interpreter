package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.DataRegisterEditor;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;

public class Add extends DataRegisterEditor
{
	protected ArgumentRegister destinationArgumentRegister;
	protected ArgumentRegister firstSourceArgumentRegister;
	protected ArgumentRegister secondSourceArgumentRegister;

	public Add(ArgumentRegister destinationArgumentRegister,
	           ArgumentRegister firstSourceArgumentRegister,
	           ArgumentRegister secondSourceArgumentRegister)
	{
		this.destinationArgumentRegister = destinationArgumentRegister;
		this.firstSourceArgumentRegister = firstSourceArgumentRegister;
		this.secondSourceArgumentRegister = secondSourceArgumentRegister;
	}

	@Override
	public void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters)
	{
		GeneralPurposeRegister destinationRegister = generalPurposeRegisters[destinationArgumentRegister
				.getRegisterIndex()];
		GeneralPurposeRegister firstSourceRegister = generalPurposeRegisters[firstSourceArgumentRegister
				.getRegisterIndex()];
		GeneralPurposeRegister secondSourceRegister = generalPurposeRegisters[secondSourceArgumentRegister
				.getRegisterIndex()];

		int firstSourceValue = firstSourceRegister.getValue();
		int secondSourceValue = secondSourceRegister.getValue();

		destinationRegister.setValue(firstSourceValue + secondSourceValue);
	}

	@Override
	public String toString()
	{
		return getMnemonic() + " " + destinationArgumentRegister + ", "
				+ firstSourceArgumentRegister + ", "
				+ secondSourceArgumentRegister;
	}

	@Override
	public String getMnemonic()
	{
		return "add";
	}

	public Add parse(String addRegistersInstruction)
	{
		ParsingUtilities parsingUtilities = new ParsingUtilities(addRegistersInstruction);
		parsingUtilities.splitBlanks();
		parsingUtilities.removeCommas();

		ArgumentRegister destinationArgumentRegister = ArgumentRegister
				.parse(parsingUtilities.getNextArgument());
		ArgumentRegister firstSourceArgumentRegister = ArgumentRegister
				.parse(parsingUtilities.getNextArgument());
		ArgumentRegister secondSourceArgumentRegister = ArgumentRegister
				.parse(parsingUtilities.getNextArgument());

		return new Add(destinationArgumentRegister,
				firstSourceArgumentRegister, secondSourceArgumentRegister);
	}
}