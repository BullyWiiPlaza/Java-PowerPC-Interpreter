package com.bullywiihacks.powerpc.assembly.interpreter.library.sources;

public class MemoryOutOfBoundsException extends RuntimeException
{
	public MemoryOutOfBoundsException(int offset, int minimumOffset, int maximumOffset)
	{
		super("The address was 0x" + Integer.toHexString(offset).toUpperCase()
				+ " but has to be between 0x" + Integer.toHexString(minimumOffset).toUpperCase()
				+ " and 0x" + Integer.toHexString(maximumOffset).toUpperCase() + "!");
	}
}