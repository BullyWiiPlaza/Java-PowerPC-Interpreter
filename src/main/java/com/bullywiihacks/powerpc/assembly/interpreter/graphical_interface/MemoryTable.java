package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

import javax.swing.table.DefaultTableModel;

public class MemoryTable extends DataTable
{
	public MemoryTable()
	{
		super(new String[]{"Address", "Value"});
	}

	public void addRows(int startingOffset, RandomAccessMemory memory)
	{
		String[] values = memory.getValues();

		for (int memoryAddressIndex = 0; memoryAddressIndex < values.length; memoryAddressIndex++)
		{
			String value = values[memoryAddressIndex];
			Object[] row = new Object[]{ValueConversions.to32BitHexadecimal(startingOffset + memoryAddressIndex * 4), value};
			DefaultTableModel tableModel = (DefaultTableModel) getModel();
			tableModel.addRow(row);
		}
	}
}