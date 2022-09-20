package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.utilities.JTableUtilities;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public abstract class DataTable extends JTable
{
	private JTextField textField;
	private TableCellEditor cellEditor;

	public DataTable(String[] strings)
	{
		setColumnHeaderNames(strings);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		textField = createTextField();
		cellEditor = new DefaultCellEditor(textField);
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

	@Override
	public TableCellEditor getCellEditor(int row, int column)
	{
		int modelColumn = convertColumnIndexToModel(column);

		if (modelColumn == 0)
		{
			return cellEditor;
		} else
		{
			return super.getCellEditor(row, column);
		}
	}

	private JTextField createTextField()
	{
		JTextField textField = new JTextField();

		((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter()
		{
			@Override
			public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
					throws BadLocationException
			{
				fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
			}

			@Override
			public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
					throws BadLocationException
			{
				fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // remove non-digits
			}
		});

		return textField;
	}
}