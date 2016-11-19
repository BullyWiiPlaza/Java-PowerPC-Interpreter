package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import com.bullywiihacks.powerpc.assembly.interpreter.library.utilities.ValueConversions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemoryTable extends JTable
{
	public MemoryTable()
	{
		String[] columnNames = new String[]{"Address", "Value"};
		setColumnHeaderNames(columnNames);
	}

	public void setColumnHeaderNames(String[] names)
	{
		JTableUtilities.configureTable(this, names);
	}

	public void addRows(int startingOffset, RandomAccessMemory memory)
	{
		String[] values = memory.getValues();

		for (int memoryAddressIndex = startingOffset; memoryAddressIndex < values.length; memoryAddressIndex += 4)
		{
			String value = values[memoryAddressIndex];
			Object[] row = new Object[]{ValueConversions.to32BitHexadecimal(memoryAddressIndex), value};
			DefaultTableModel tableModel = (DefaultTableModel) getModel();
			tableModel.addRow(row);
		}
	}

	public void removeAllRows()
	{
		JTableUtilities.deleteAllRows(this);
	}
}