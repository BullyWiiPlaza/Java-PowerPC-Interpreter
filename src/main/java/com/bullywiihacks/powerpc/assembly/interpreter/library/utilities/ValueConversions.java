package com.bullywiihacks.powerpc.assembly.interpreter.library.utilities;

public class ValueConversions
{
	public static String to32BitHexadecimal(int input)
	{
		String hexadecimal = Integer.toHexString(input);

		hexadecimal = String.format("%8s", hexadecimal);
		hexadecimal = hexadecimal.replaceAll(" ", "0");
		hexadecimal = hexadecimal.toUpperCase();

		return hexadecimal;
	}

	public static String toHexadecimalNotation(int input)
	{
		String hexadecimal = Integer.toHexString(input);
		hexadecimal = hexadecimal.toUpperCase();
		hexadecimal = "0x" + hexadecimal;

		return hexadecimal;
	}
}