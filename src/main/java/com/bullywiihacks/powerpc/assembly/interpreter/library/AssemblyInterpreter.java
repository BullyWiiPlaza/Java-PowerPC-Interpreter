package com.bullywiihacks.powerpc.assembly.interpreter.library;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.GeneralPurposeRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.FloatingPointRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.ArgumentRegister;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments.Immediate;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations.Add;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations.LoadImmediate;

public class AssemblyInterpreter
{
	private GeneralPurposeRegister[] generalPurposeRegisters;
	private FloatingPointRegister[] floatingPointRegisters;
	private RandomAccessMemory memory;

	public static final int REGISTERS_COUNT = 32;

	public AssemblyInterpreter()
	{
		generalPurposeRegisters = new GeneralPurposeRegister[REGISTERS_COUNT];
		floatingPointRegisters = new FloatingPointRegister[REGISTERS_COUNT];
		memory = new RandomAccessMemory();

		for (int registerIndex = 0; registerIndex < generalPurposeRegisters.length; registerIndex++)
		{
			generalPurposeRegisters[registerIndex] = new GeneralPurposeRegister();
			floatingPointRegisters[registerIndex] = new FloatingPointRegister();
		}
	}

	public RandomAccessMemory getMemory()
	{
		return memory;
	}

	public GeneralPurposeRegister[] getGeneralPurposeRegisters()
	{
		return generalPurposeRegisters;
	}

	public FloatingPointRegister[] getFloatingPointRegisters()
	{
		return floatingPointRegisters;
	}

	public String getDataRegisterValue(int registerIndex)
	{
		return generalPurposeRegisters[registerIndex].getHexadecimalValue();
	}

	public String getFloatingPointRegisterValue(int registerIndex)
	{
		return floatingPointRegisters[registerIndex].getHexadecimalValue();
	}

	public void printAllDataRegisters()
	{
		for (int registerIndex = 0; registerIndex < generalPurposeRegisters.length; registerIndex++)
		{
			System.out.println("r" + registerIndex + ":\t "
					+ getDataRegisterValue(registerIndex));
		}
	}

	public void printAllFloatingPointRegisters()
	{
		for (int registerIndex = 0; registerIndex < floatingPointRegisters.length; registerIndex++)
		{
			System.out.println("f" + registerIndex + ":\t "
					+ getFloatingPointRegisterValue(registerIndex));
		}
	}

	private static void randomAccessMemoryTesting()
	{
		int targetAddress = 25165820;
		int value = 0x12345678;

		RandomAccessMemory randomAccessMemory = new RandomAccessMemory();
		randomAccessMemory.setInteger(value, targetAddress);
		System.out.println(randomAccessMemory.getInteger(targetAddress));
	}

	private static void interpreterTesting()
	{
		AssemblyInterpreter assemblyInterpreter = new AssemblyInterpreter();
		LoadImmediate loadImmediate = new LoadImmediate(
				new ArgumentRegister(31), new Immediate(32767));

		loadImmediate.execute(assemblyInterpreter);
		Add add = new Add(new ArgumentRegister(30),
				new ArgumentRegister(30), new ArgumentRegister(31));

		add.execute(assemblyInterpreter);
		add.execute(assemblyInterpreter);

		assemblyInterpreter.printAllDataRegisters();
	}

	public void setGeneralPurposeRegisterValue(int index, int value)
	{
		generalPurposeRegisters[index].setValue(value);
	}

	public void setMemoryValue(int offset, int value)
	{
		memory.setInteger(value, offset);
	}
}