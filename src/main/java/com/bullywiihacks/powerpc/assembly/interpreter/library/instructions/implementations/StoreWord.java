package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.MemoryEditor;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

public class StoreWord extends MemoryEditor
{
	private ArgumentRegister sourceArgument;
	private Immediate offset;
	private ArgumentRegister targetArgument;

	public StoreWord(ArgumentRegister sourceArgument,
	                 Immediate offset,
	                 ArgumentRegister targetArgument)
	{
		this.sourceArgument = sourceArgument;
		this.offset = offset;
		this.targetArgument = targetArgument;
	}

	@Override
	public String getMnemonic()
	{
		return "stw";
	}

	@Override
	public AssemblyInstruction parse(String instruction)
	{
		instruction = instruction.replaceFirst(getMnemonicSpaced(), "");
		String[] components = instruction.split(", ");
		sourceArgument = ArgumentRegister.parse(components[0]);
		components = components[1].split(" ");
		offset = Immediate.parse(components[0]);
		String targetArgumentsWithBrackets = components[1];

		if (!targetArgumentsWithBrackets.startsWith("("))
		{
			throw new IllegalArgumentException("Rounded opening expected");
		}

		if (!targetArgumentsWithBrackets.endsWith(")"))
		{
			throw new IllegalArgumentException("Rounded closing bracket expected");
		}

		targetArgument = ArgumentRegister.parse(targetArgumentsWithBrackets.substring(1, targetArgumentsWithBrackets.length() - 1));

		return new StoreWord(sourceArgument, offset, targetArgument);
	}

	@Override
	public void modifyMemory(GeneralPurposeRegister[] generalPurposeRegisters,
	                         RandomAccessMemory randomAccessMemory)
	{
		GeneralPurposeRegister targetRegister = targetArgument.toGeneralPurposeRegister(generalPurposeRegisters);
		int targetRegisterValue = targetRegister.getValue();
		int offsetValue = offset.getValue();
		targetRegisterValue += offsetValue;
		GeneralPurposeRegister sourceRegister = sourceArgument.toGeneralPurposeRegister(generalPurposeRegisters);
		int sourceValue = sourceRegister.getValue();
		randomAccessMemory.setInteger(sourceValue, targetRegisterValue);
	}

	@Override
	public String toString()
	{
		return getMnemonicSpaced()
				+ " " + sourceArgument
				+ ", " + offset
				+ " (" + targetArgument + ")";
	}
}