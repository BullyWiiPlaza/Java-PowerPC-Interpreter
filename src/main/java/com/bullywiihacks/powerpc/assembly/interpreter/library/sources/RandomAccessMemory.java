package com.bullywiihacks.powerpc.assembly.interpreter.library.sources;

import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

import java.nio.ByteBuffer;

public class RandomAccessMemory
{
	public static int SIZE = 0x1000;

	private ByteBuffer randomAccessMemory;

	private int minimumOffset = 0;
	private int maximumByteOffset;
	private int maximumShortOffset;
	private int maximumIntegerOffset;

	public RandomAccessMemory(int size)
	{
		randomAccessMemory = ByteBuffer.allocate(size * 4);
		maximumByteOffset = randomAccessMemory.capacity() - 1;
		maximumShortOffset = maximumByteOffset - 1;
		maximumIntegerOffset = maximumShortOffset - 2;
	}

	public RandomAccessMemory()
	{
		this(SIZE);
	}

	public String[] getValues()
	{
		randomAccessMemory.position(0);
		String[] values = new String[randomAccessMemory.limit() / 4];

		int memoryAddressIndex = 0;
		while (randomAccessMemory.position() < randomAccessMemory.limit() - 4)
		{
			values[memoryAddressIndex] = ValueConversions.to32BitHexadecimal(randomAccessMemory.getInt());
			memoryAddressIndex++;
		}

		return values;
	}

	public void setInteger(int value, int offset)
	{
		verifyOffsetValidity(offset, maximumIntegerOffset);

		randomAccessMemory.putInt(offset, value);
	}

	public int getInteger(int offset)
	{
		verifyOffsetValidity(offset, maximumIntegerOffset);

		return randomAccessMemory.getInt(offset);
	}

	public void setShort(short value, int offset)
	{
		verifyOffsetValidity(offset, maximumShortOffset);

		randomAccessMemory.putShort(offset, value);
	}

	public short getShort(int offset)
	{
		verifyOffsetValidity(offset, maximumShortOffset);

		return randomAccessMemory.getShort(offset);
	}

	public void setByte(byte value, int offset)
	{
		verifyOffsetValidity(offset, maximumByteOffset);

		randomAccessMemory.put(offset, value);
	}

	public void getByte(int offset)
	{
		verifyOffsetValidity(offset, maximumByteOffset);

		randomAccessMemory.get(offset);
	}

	private boolean isValidOffset(int offset, int maximumOffset)
	{
		boolean isAtLeastZero = offset >= minimumOffset;
		boolean isNotGreaterThanMaximum = offset <= maximumOffset;

		return isAtLeastZero && isNotGreaterThanMaximum;
	}

	private void verifyOffsetValidity(int offset, int maximumOffset)
	{
		if (!isValidOffset(offset, maximumOffset))
		{
			throw new IllegalArgumentException("The offset was " + offset
					+ " but has to be between " + minimumOffset + " and "
					+ maximumOffset + "!");
		}
	}
}