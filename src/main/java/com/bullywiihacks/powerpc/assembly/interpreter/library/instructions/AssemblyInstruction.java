package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;

/*
 * add, addi, addis, sub, subi, subis, and, andi., andis., b, cmpw, cpmwi, lbz, lbzx, lhz, lhzx, lwz, lwzx, mulli, mullw, or, ori, oris, stb, stbx, sth, sthx, stw, stwx, xor, xori, xoris
 */

public abstract class AssemblyInstruction
{
	public abstract void execute(AssemblyInterpreter interpreter);

	public abstract String toString();

	public abstract String getMnemonic();

	public String getMnemonicSpaced()
	{
		return getMnemonic() + (hasArguments() ? " " : "");
	}

	public abstract AssemblyInstruction parse(String instruction);

	protected boolean hasArguments()
	{
		return true;
	}
}