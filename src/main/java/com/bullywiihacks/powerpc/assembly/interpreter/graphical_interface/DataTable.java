package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.utilities.JTableUtilities;

import javax.swing.*;

public abstract class DataTable extends JTable
{
	public DataTable(String[] strings)
	{
		setColumnHeaderNames(strings);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void setColumnHeaderNames(String[] names)
	{
		JTableUtilities.configureTable(this, names);
	}

	public void removeAllRows()
	{
		JTableUtilities.deleteAllRows(this);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex == 1;
	}
}