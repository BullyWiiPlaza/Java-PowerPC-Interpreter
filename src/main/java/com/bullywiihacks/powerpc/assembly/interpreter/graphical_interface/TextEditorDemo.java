package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface;

import javax.swing.*;

import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import java.awt.*;

public class TextEditorDemo extends JFrame
{
	public TextEditorDemo()
	{
		JPanel panel = new JPanel(new BorderLayout());

		RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
		textArea.setCodeFoldingEnabled(true);
		RTextScrollPane textScrollPane = new RTextScrollPane(textArea);
		textScrollPane.setLineNumbersEnabled(true);
		panel.add(textScrollPane);

		setContentPane(panel);
		setTitle("Text Editor Demo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	public static void main(String[] args)
	{
		// Start all Swing applications on the EDT.
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new TextEditorDemo().setVisible(true);
			}
		});
	}
}