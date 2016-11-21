package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.DataRegisterEditor;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.ParsingUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;

public class AddRegisters extends DataRegisterEditor
{
	ArgumentRegister destinationRegister;
	ArgumentRegister firstSourceRegister;
	ArgumentRegister secondSourceRegister;

	public AddRegisters(ArgumentRegister destinationRegister,
	                    ArgumentRegister firstSourceRegister,
	                    ArgumentRegister secondSourceRegister)
	{
		this.destinationRegister = destinationRegister;
		this.firstSourceRegister = firstSourceRegister;
		this.secondSourceRegister = secondSourceRegister;
	}

	@Override
	public void modifyDataRegisters(GeneralPurposeRegister[] generalPurposeRegisters)
	{
		GeneralPurposeRegister destinationRegister = this.destinationRegister.toGeneralPurposeRegister(generalPurposeRegisters);
		GeneralPurposeRegister firstSourceRegister = this.firstSourceRegister.toGeneralPurposeRegister(generalPurposeRegisters);
		GeneralPurposeRegister secondSourceRegister = this.secondSourceRegister.toGeneralPurposeRegister(generalPurposeRegisters);

		int firstSourceValue = firstSourceRegister.getValue();
		int secondSourceValue = secondSourceRegister.getValue();

		destinationRegister.setValue(firstSourceValue + secondSourceValue);
	}

	@Override
	public String toString()
	{
		return getMnemonicSpaced() + destinationRegister + ", "
				+ firstSourceRegister + ", "
				+ secondSourceRegister;
	}

	@Override
	public String getMnemonic()
	{
		return "add";
	}

	public AddRegisters parse(String instruction)
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

		return new AddRegisters(destinationArgumentRegister,
				firstSourceArgumentRegister, secondSourceArgumentRegister);
	}
}