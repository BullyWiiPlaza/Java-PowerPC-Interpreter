package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyParser;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AssemblyInterpreterGUI extends JFrame
{
	private static int width = 1000;
	private static int height = 600;

	private RSyntaxTextArea assemblyArea;
	private JPanel rootPanel;
	private JLabel statusLabel;
	private JScrollPane assemblyAreaScrollPane;
	private RegistersTable registersTable;
	private JButton runAssemblyButton;
	private JScrollPane registersTableScrollPane;
	private MemoryTable memoryTable;
	private JButton resetButton;
	private JButton assemblyDocumentationButton;
	private PPCAssemblyParser parser;
	private boolean canInterpret;
	private PPCAssemblyInterpreter interpreter;

	public AssemblyInterpreterGUI()
	{
		add(rootPanel);
		setFrameProperties();

		assemblyArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
		assemblyArea.setCodeFoldingEnabled(true);
		assemblyArea.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent documentEvent)
			{
				parseInputAsynchronously();
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent)
			{
				parseInputAsynchronously();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent)
			{
				parseInputAsynchronously();
			}
		});

		resetButton.addActionListener(actionEvent -> initializeTables());

		parseInputAsynchronously();
		initializeTables();

		TextLineNumber textLineNumber = new TextLineNumber(assemblyArea);
		assemblyAreaScrollPane.setRowHeaderView(textLineNumber);
		assemblyAreaScrollPane.setPreferredSize(new Dimension(width / 2, height));

		runAssemblyButton.addActionListener(actionEvent -> interpretAssembly());

		registersTable.addPropertyChangeListener(changeListener ->
		{
			if ("tableCellEditor".equals(changeListener.getPropertyName()))
			{
				if (!registersTable.isEditing())
				{
					int index = registersTable.getSelectedRow();
					int column = registersTable.getSelectedColumn();
					int value = Integer.parseInt((String) registersTable.getValueAt(index, column), 16);
					interpreter.setGeneralPurposeRegisterValue(index, value);
				}
			}
		});

		memoryTable.addPropertyChangeListener(changeListener ->
		{
			if ("tableCellEditor".equals(changeListener.getPropertyName()))
			{
				if (!memoryTable.isEditing())
				{
					int index = memoryTable.getSelectedRow();
					int column = memoryTable.getSelectedColumn();
					int value = Integer.parseInt((String) memoryTable.getValueAt(index, column), 16);
					interpreter.setMemoryValue(index, value);
				}
			}
		});

		assemblyDocumentationButton.addActionListener(actionEvent ->
		{
			try
			{
				Desktop.getDesktop().browse(new URI("http://www.ds.ewi.tudelft.nl/vakken/in1006/instruction-set/"));
			} catch (Exception exception)
			{
				exception.printStackTrace();
			}
		});
	}

	private void interpretAssembly()
	{
		registersTable.removeAllRows();
		memoryTable.removeAllRows();

		String assembly = assemblyArea.getText();
		List<AssemblyInstruction> assemblyInstructions = parser.parseAssembly(assembly);

		for (AssemblyInstruction assemblyInstruction : assemblyInstructions)
		{
			System.out.println(assemblyInstruction);
			assemblyInstruction.execute(interpreter);
		}

		registersTable.addRows(interpreter.getGeneralPurposeRegisters());
		memoryTable.addRows(interpreter.getMemory());
	}

	private void initializeTables()
	{
		interpreter = new PPCAssemblyInterpreter();
		registersTable.removeAllRows();
		memoryTable.removeAllRows();
		registersTable.addRows(interpreter.getGeneralPurposeRegisters());
		memoryTable.addRows(interpreter.getMemory());
	}

	private void parseInputAsynchronously()
	{
		Thread thread = new Thread(() ->
		{
			String status = "OK!";

			try
			{
				parser = new PPCAssemblyParser();
				String inputtedAssembly = assemblyArea.getText();
				parser.parseAssembly(inputtedAssembly);
				canInterpret = true;
			} catch (Exception exception)
			{
				status = getStatusFromException(exception);
				canInterpret = false;
			}

			runAssemblyButton.setEnabled(canInterpret);
			setStatusLabel(status);
		});

		thread.start();
	}

	private String getStatusFromException(Exception exception)
	{
		String exceptionClassName = exception.getClass().getName();
		String[] exceptionPathComponents = exceptionClassName.split("\\.");
		exceptionClassName = exceptionPathComponents[exceptionPathComponents.length - 1];
		String status = exceptionClassName + " " + exception.getMessage();
		int statusSizeLimit = width / 10;

		if (status.length() > statusSizeLimit)
		{
			status = status.substring(0, statusSizeLimit);
			status += " [...]";
		}

		return status;
	}

	private void setFrameProperties()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setTitle("Java PowerPC Interpreter");
		setIconImage(this);
	}

	public static void setIconImage(Window window)
	{
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(AssemblyInterpreterGUI.class.getResource("/Icon.png")));
	}

	private void setStatusLabel(String statusText)
	{
		statusLabel.setText(statusText);
	}
}