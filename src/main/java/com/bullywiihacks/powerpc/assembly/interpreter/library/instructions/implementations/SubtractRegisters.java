package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;

public class SubtractRegisters extends AddRegisters
{
	public SubtractRegisters(ArgumentRegister destinationArgumentRegister, ArgumentRegister firstSourceArgumentRegister, ArgumentRegister secondSourceArgumentRegister)
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
		GeneralPurposeRegister destinationRegister = this.destinationRegister.toGeneralPurposeRegister(generalPurposeRegisters);
		GeneralPurposeRegister firstSourceRegister = this.firstSourceRegister.toGeneralPurposeRegister(generalPurposeRegisters);
		GeneralPurposeRegister secondSourceRegister = this.secondSourceRegister.toGeneralPurposeRegister(generalPurposeRegisters);

		int firstSourceValue = firstSourceRegister.getValue();
		int secondSourceValue = secondSourceRegister.getValue();

		destinationRegister.setValue(firstSourceValue - secondSourceValue);
	}

	public SubtractRegisters parse(String instruction)
	{
		ParsingUtilities parsingUtilities = new ParsingUtilities(instruction);
		parsingUtilities.splitBlanks();
		parsingUtilities.removeCommas();

		ArgumentRegister destinationArgumentRegister = ArgumentRegister
				.parse(parsingUtilities.getNextArgument());
		ArgumentRegister firstSourceArgumentRegister = ArgumentRegister
				.parse(parsingUtilities.getNextArgument());
		ArgumentRegister secondSourceArgumentRegister = ArgumentRegister
				.parse(parsingUtilities.getNextArgument());

		return new SubtractRegisters(destinationArgumentRegister,
				firstSourceArgumentRegister, secondSourceArgumentRegister);
	}
}