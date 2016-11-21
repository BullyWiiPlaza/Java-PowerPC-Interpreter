package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.utilities.HexadecimalInputFilter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyParser;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;
import com.bullywiihacks.powerpc.assembly.interpreter.library.sources.RandomAccessMemory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.net.URI;
import java.util.List;

public class AssemblyInterpreterGUI extends JFrame
{
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 600;

	private RSyntaxTextArea assemblyArea;
	private JPanel rootPanel;
	private JLabel statusLabel;
	private JScrollPane assemblyAreaScrollPane;
	private RegistersTable registersTable;
	private JButton interpretAssemblyButton;
	private MemoryTable memoryTable;
	private JButton resetGeneralPurposeRegistersButton;
	private JButton assemblyDocumentationButton;
	private JTextField startingAddressField;
	private JTextField endingAddressField;
	private JButton resetMemoryButton;
	private JButton informationButton;
	private AssemblyParser parser;
	private boolean canInterpret;
	private AssemblyInterpreter interpreter;

	public AssemblyInterpreterGUI()
	{
		add(rootPanel);
		setFrameProperties();

		initializeTables();
		configureAssemblyArea();
		addResetButtonListener();
		parseInputAsynchronously();
		addRunAssemblyButtonListener();
		addRegistersTableListener();
		addMemoryTableListener();
		addAssemblyDocumentationButtonListener();
		configureStartingAddressField();
		configureEndingAddressField();
		validateAddressRange();
		addResetMemoryButtonListener();
		addInformationButtonListener();
	}

	private void addResetButtonListener()
	{
		resetGeneralPurposeRegistersButton.addActionListener(actionEvent -> resetGeneralPurposeRegisters());
	}

	private void resetGeneralPurposeRegisters()
	{
		registersTable.removeAllRows();
		interpreter.setGeneralPurposeRegisters();
		registersTable.addRows(interpreter.getGeneralPurposeRegisters());
	}

	private void configureAssemblyArea()
	{
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

		addAssemblyAreaScrollPane();
	}

	private void addRunAssemblyButtonListener()
	{
		interpretAssemblyButton.addActionListener(actionEvent -> handleAssemblyInterpretation());
	}

	private void addAssemblyAreaScrollPane()
	{
		TextLineNumber textLineNumber = new TextLineNumber(assemblyArea);
		assemblyAreaScrollPane.setRowHeaderView(textLineNumber);
		assemblyAreaScrollPane.setPreferredSize(new Dimension(FRAME_WIDTH / 2, FRAME_HEIGHT));
	}

	private void addInformationButtonListener()
	{
		informationButton.addActionListener(actionEvent ->
		{
			try
			{
				Desktop.getDesktop().browse(new URI("https://github.com/BullyWiiPlaza/Java-PowerPC-Interpreter"));
			} catch (Exception exception)
			{
				exception.printStackTrace();
			}
		});
	}

	private void addResetMemoryButtonListener()
	{
		resetMemoryButton.addActionListener(actionEvent ->
		{
			memoryTable.removeAllRows();
			int startingOffset = Integer.parseInt(startingAddressField.getText(), 16);
			int endingOffset = Integer.parseInt(endingAddressField.getText(), 16);
			int length = endingOffset - startingOffset;
			RandomAccessMemory randomAccessMemory = new RandomAccessMemory(length);
			interpreter.setMemory(randomAccessMemory);
			memoryTable.addRows(startingOffset, interpreter.getMemory());
		});
	}

	private void configureEndingAddressField()
	{
		configureAddressRangeField(endingAddressField);
	}

	private void configureStartingAddressField()
	{
		configureAddressRangeField(startingAddressField);
	}

	private void configureAddressRangeField(JTextComponent endingAddressField)
	{
		HexadecimalInputFilter.setHexadecimalInputFilter(endingAddressField);
		endingAddressField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent documentEvent)
			{
				validateAddressRange();
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent)
			{
				validateAddressRange();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent)
			{
				validateAddressRange();
			}
		});
	}

	private void addAssemblyDocumentationButtonListener()
	{
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

	private void addMemoryTableListener()
	{
		memoryTable.addPropertyChangeListener(changeListener ->
		{
			if (!memoryTable.isEditing())
			{
				int rowIndex = memoryTable.getSelectedRow();
				int columnIndex = memoryTable.getSelectedColumn();

				if (rowIndex != -1)
				{
					int value = Integer.parseInt((String) memoryTable.getValueAt(rowIndex, columnIndex), 16);
					interpreter.setMemoryValue(rowIndex * 4, value);
				}
			}
		});
	}

	private void addRegistersTableListener()
	{
		registersTable.addPropertyChangeListener(changeListener ->
		{
			if (!registersTable.isEditing())
			{
				int rowIndex = registersTable.getSelectedRow();
				int columnIndex = registersTable.getSelectedColumn();

				if (rowIndex != -1)
				{
					int value = Integer.parseInt((String) registersTable.getValueAt(rowIndex, columnIndex), 16);
					interpreter.setGeneralPurposeRegisterValue(rowIndex, value);
				}
			}
		});
	}

	private void validateAddressRange()
	{
		int startingOffset = Integer.parseInt(startingAddressField.getText(), 16);
		int endingOffset = Integer.parseInt(endingAddressField.getText(), 16);
		boolean validRange = endingOffset - startingOffset > 0;
		startingAddressField.setBackground(validRange ? Color.GREEN : Color.RED);
		endingAddressField.setBackground(validRange ? Color.GREEN : Color.RED);
	}

	private void handleAssemblyInterpretation()
	{
		String interpretAssemblyButtonText = interpretAssemblyButton.getText();
		interpretAssemblyButton.setText("Interpreting...");
		canInterpret = false;
		registersTable.setEnabled(false);
		memoryTable.setEnabled(false);
		interpretAssemblyButton.setEnabled(false);

		new SwingWorker<String, String>()
		{
			@Override
			protected String doInBackground() throws Exception
			{
				interpretInputtedAssembly();

				return null;
			}

			@Override
			protected void done()
			{
				// Remove old data
				int selectedRegistersRow = registersTable.getSelectedRow();
				registersTable.removeAllRows();
				int selectedMemoryTableRow = memoryTable.getSelectedRow();
				memoryTable.removeAllRows();

				// Restore selected row
				int startingOffset = Integer.parseInt(startingAddressField.getText(), 16);

				registersTable.addRows(interpreter.getGeneralPurposeRegisters());
				if (selectedRegistersRow != -1)
				{
					registersTable.setRowSelectionInterval(selectedRegistersRow, selectedRegistersRow);
				}
				memoryTable.addRows(startingOffset, interpreter.getMemory());
				if (selectedMemoryTableRow != -1)
				{
					memoryTable.setRowSelectionInterval(selectedMemoryTableRow, selectedMemoryTableRow);
				}

				interpretAssemblyButton.setText(interpretAssemblyButtonText);

				canInterpret = true;
				registersTable.setEnabled(true);
				memoryTable.setEnabled(true);
				interpretAssemblyButton.setEnabled(true);
			}
		}.execute();
	}

	private void interpretInputtedAssembly()
	{
		String assembly = assemblyArea.getText();
		List<AssemblyInstruction> assemblyInstructions = parser.parse(assembly);

		String status = "OK!";
		int assemblyInstructionIndex = 0;

		try
		{
			for (AssemblyInstruction assemblyInstruction : assemblyInstructions)
			{
				assemblyInstruction.execute(interpreter);
				assemblyInstructionIndex++;
			}
		} catch (Exception exception)
		{
			int exceptionLine = assemblyInstructionIndex + 1;
			setCursorPosition(exceptionLine);
			status = getStatusFromException(exception);
			status = "Line " + exceptionLine + " " + status;
		}

		setStatusLabel(status);
	}

	private void setCursorPosition(int exceptionLine)
	{
		String assembly = assemblyArea.getText();
		int linesCount = 0;
		int caretPosition = 0;

		for (; caretPosition < assembly.length(); caretPosition++)
		{
			char character = assembly.charAt(caretPosition);

			if (character == '\n')
			{
				linesCount++;

				if (linesCount == exceptionLine)
				{
					break;
				}
			}
		}

		assemblyArea.setCaretPosition(caretPosition);
	}

	private void initializeTables()
	{
		int startingOffset = Integer.parseInt(startingAddressField.getText(), 16);
		interpreter = new AssemblyInterpreter();
		registersTable.removeAllRows();
		memoryTable.removeAllRows();
		registersTable.addRows(interpreter.getGeneralPurposeRegisters());
		memoryTable.addRows(startingOffset, interpreter.getMemory());
	}

	private void parseInputAsynchronously()
	{
		Thread parserThread = new Thread(() ->
		{
			String status = "OK!";

			try
			{
				parser = new AssemblyParser();
				String input = assemblyArea.getText();
				parser.parse(input);
				canInterpret = true;
			} catch (Exception exception)
			{
				status = getStatusFromException(exception);
				canInterpret = false;
			}

			interpretAssemblyButton.setEnabled(canInterpret);
			setStatusLabel(status);
		});

		parserThread.start();
	}

	private String getStatusFromException(Exception exception)
	{
		String exceptionClassName = exception.getClass().getName();
		String[] exceptionPathComponents = exceptionClassName.split("\\.");
		exceptionClassName = exceptionPathComponents[exceptionPathComponents.length - 1];
		String status = exceptionClassName + " " + exception.getMessage();
		int statusSizeLimit = FRAME_WIDTH / 10;

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
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Java PowerPC Interpreter by BullyWiiPlaza");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AssemblyInterpreterGUI.class.getResource("/Icon.png")));
	}

	private void setStatusLabel(String statusText)
	{
		statusLabel.setText(statusText);
	}
}