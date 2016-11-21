package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.utilities.JTableUtilities;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RegistersTable extends JTable
{
	public RegistersTable()
	{
		String[] columnHeaderNames = new String[]{"Register", "Value"};
		setColumnHeaderNames(columnHeaderNames);
	}

	private void setColumnHeaderNames(String[] names)
	{
		JTableUtilities.configureTable(this, names);
	}

	public void addRows(GeneralPurposeRegister[] registers)
	{
		for (int registerIndex = 0; registerIndex < registers.length; registerIndex++)
		{
			GeneralPurposeRegister register = registers[registerIndex];
			Object[] row = new Object[]{registerIndex, register.getHexadecimalValue()};
			DefaultTableModel tableModel = (DefaultTableModel) getModel();
			tableModel.addRow(row);
		}
	}

	public void removeAllRows()
	{
		JTableUtilities.deleteAllRows(this);
	}
}