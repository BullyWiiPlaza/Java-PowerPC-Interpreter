package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class JTableNumberColumn
{
	public JTableNumberColumn()
	{
		JFrame frame = new JFrame();
		JTextField field = createTextField();
		frame.add(new JScrollPane(createTable(field)));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JTextField createTextField()
	{
		JTextField field = new JTextField();
		((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter()
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
		return field;
	}

	private JTable createTable(JTextField field)
	{
		String[] cols = {"Only Numbers", "Col 2", "Col 3"};
		String[][] data = {{null, null, null}, {null, null, null}, {null, null, null}};
		TableCellEditor editor = new DefaultCellEditor(field);

		return new JTable(data, cols)
		{
			@Override
			public TableCellEditor getCellEditor(int row, int column)
			{
				int modelColumn = convertColumnIndexToModel(column);

				if (modelColumn == 0)
				{
					return editor;
				} else
				{
					return super.getCellEditor(row, column);
				}
			}
		};
	}

	public static void main(String[] arguments)
	{
		SwingUtilities.invokeLater(JTableNumberColumn::new);
	}
}