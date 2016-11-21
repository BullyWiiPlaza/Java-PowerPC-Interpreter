package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.registers.GeneralPurposeRegister;

import javax.swing.table.DefaultTableModel;

public class RegistersTable extends DataTable
{
	public RegistersTable()
	{
		super(new String[]{"Register", "Value"});
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
}