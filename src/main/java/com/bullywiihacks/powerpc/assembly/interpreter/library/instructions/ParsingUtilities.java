package com.bullywiihacks.powerpc.assembly.interpreter.library.instructions;

public class ParsingUtilities
{
	private String input;
	private String[] components;
	private int argumentsIndex;

	public ParsingUtilities(String input)
	{
		this.input = input;
	}

	public void splitBlanks()
	{
		components = input.split(" ");
		argumentsIndex = 1;
	}

	public void removeCommas()
	{
		for (int splitIndex = 0; splitIndex < components.length; splitIndex++)
		{
			components[splitIndex] = components[splitIndex].replace(",", "");
		}
	}

	public String getNextArgument()
	{
		String currentArgument = components[argumentsIndex];

		argumentsIndex++;

		return currentArgument;
	}
}