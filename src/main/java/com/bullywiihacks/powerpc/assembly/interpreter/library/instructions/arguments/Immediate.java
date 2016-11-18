package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.arguments;

public class Immediate
{
	private int immediate;

	public Immediate(int immediate)
	{
		if (isValidImmediate(immediate))
		{
			this.immediate = immediate;
		} else
		{
			throw new IllegalArgumentException("The immediate is " + immediate
					+ " but must be between " + Short.MIN_VALUE + " and "
					+ Short.MAX_VALUE + "!");
		}
	}

	public int getValue()
	{
		return immediate;
	}

	private boolean isValidImmediate(int immediate)
	{
		return (immediate <= Short.MAX_VALUE) && (immediate >= Short.MIN_VALUE);
	}

	public static Immediate parse(String immediateArgument)
	{
		Immediate immediate;

		if (immediateArgument.contains("0x"))
		{
			immediateArgument = immediateArgument.replace("0x", "");
			immediate = new Immediate(Integer.parseInt(immediateArgument, 16));
		} else
		{
			immediate = new Immediate(Integer.parseInt((immediateArgument)));
		}

		return immediate;
	}

	@Override
	public String toString()
	{
		return Integer.toString(immediate);
	}
}