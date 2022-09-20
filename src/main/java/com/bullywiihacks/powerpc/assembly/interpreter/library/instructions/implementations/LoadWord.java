package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations;

import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

public class LoadWord extends AssemblyInstruction
{
	private ArgumentRegister targetArgument;
	private Immediate offset;
	private ArgumentRegister sourceArgument;

	public LoadWord(ArgumentRegister targetArgument,
	                Immediate offset,
	                ArgumentRegister sourceArgument)
	{
		this.targetArgument = targetArgument;
		this.offset = offset;
		this.sourceArgument = sourceArgument;
	}

	@Override
	public String toString()
	{
		return getMnemonicSpaced()
				+ " " + sourceArgument
				+ ", " + offset
				+ " (" + targetArgument + ")";
	}

	@Override
	public String getMnemonic()
	{
		return "lwz";
	}

	@Override
	public AssemblyInstruction parse(String instruction)
	{
		instruction = instruction.replaceFirst(getMnemonicSpaced(), "");
		String[] components = instruction.split(", ");
		targetArgument = ArgumentRegister.parse(components[0]);
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

		sourceArgument = ArgumentRegister.parse(targetArgumentsWithBrackets.substring(1, targetArgumentsWithBrackets.length() - 1));

		return new LoadWord(targetArgument, offset, sourceArgument);
	}

	@Override
	public void execute(AssemblyInterpreter interpreter)
	{
		GeneralPurposeRegister[] generalPurposeRegisters = interpreter.getGeneralPurposeRegisters();
		RandomAccessMemory randomAccessMemory = interpreter.getMemory();

		GeneralPurposeRegister sourceRegister = sourceArgument.toGeneralPurposeRegister(generalPurposeRegisters);
		int targetRegisterValue = sourceRegister.getValue();
		int offsetValue = offset.getValue();
		targetRegisterValue += offsetValue;
		int memoryValue = randomAccessMemory.getInteger(targetRegisterValue);

		GeneralPurposeRegister targetRegister = targetArgument.toGeneralPurposeRegister(generalPurposeRegisters);
		targetRegister.setValue(memoryValue);
	}
}