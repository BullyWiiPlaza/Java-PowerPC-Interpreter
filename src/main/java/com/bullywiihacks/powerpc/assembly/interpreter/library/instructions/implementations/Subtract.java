package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;

public class Subtract extends Add
{
	public Subtract(ArgumentRegister destinationArgumentRegister, ArgumentRegister firstSourceArgumentRegister, ArgumentRegister secondSourceArgumentRegister)
	{
		super(destinationArgumentRegister, firstSourceArgumentRegister, secondSourceArgumentRegister);
	}

	@Override
	public String getMnemonic()
	{
		return "sub";
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

		destinationRegister.setValue(firstSourceValue - secondSourceValue);
	}

	public Subtract parse(String addRegistersInstruction)
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

		return new Subtract(destinationArgumentRegister,
				firstSourceArgumentRegister, secondSourceArgumentRegister);
	}
}