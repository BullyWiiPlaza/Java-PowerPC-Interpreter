package com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.utilities;

import javax.swing.text.*;
import java.awt.*;

/**
 * An input filter for a text field to make sure only valid input is given.
 * Also, lowercase letters will be converted to uppercase automatically.
 */
public class HexadecimalInputFilter extends DocumentFilter
{
	private int maximumLength;

	public HexadecimalInputFilter(int maximumLength)
	{
		this.maximumLength = maximumLength;
	}

	@Override
	public void insertString(FilterBypass filterBypass, int offset, String text,
	                         AttributeSet attributeSet) throws BadLocationException
	{
		text = trimHexadecimal(text);
		Document document = filterBypass.getDocument();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(document.getText(0, document.getLength()));
		stringBuilder.insert(offset, text);

		if (isValidInput(stringBuilder.toString(), 0))
		{
			super.insertString(filterBypass, offset, text, attributeSet);
		} else
		{
			Toolkit.getDefaultToolkit().beep();
		}
	}

	@Override
	public void replace(DocumentFilter.FilterBypass filterBypass, int offset, int length, String text,
	                    AttributeSet attributeSet) throws BadLocationException
	{
		text = trimHexadecimal(text);
		Document document = filterBypass.getDocument();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(document.getText(0, document.getLength()));
		stringBuilder.insert(offset, text);

		if (isValidInput(stringBuilder.toString(), length))
		{
			super.replace(filterBypass, offset, length, text, attributeSet);
		} else
		{
			Toolkit.getDefaultToolkit().beep();
		}
	}

	private static String trimHexadecimal(String text)
	{
		text = text.trim();
		text = text.replaceAll(" ", "");

		if (text.startsWith("0x"))
		{
			text = text.substring(2);
		}

		return text.toUpperCase();
	}

	public static void setHexadecimalInputFilter(JTextComponent textComponent)
	{
		setHexadecimalInputFilter(textComponent, 8);
	}

	public static void setHexadecimalInputFilter(JTextComponent textComponent, int maximumInputLength)
	{
		PlainDocument plainDocument = (PlainDocument) textComponent.getDocument();
		plainDocument.setDocumentFilter(new HexadecimalInputFilter(maximumInputLength));

		String text = textComponent.getText();
		text = trimHexadecimal(text);

		if (text.length() > maximumInputLength)
		{
			textComponent.setText(text.substring(text.length() - maximumInputLength, text.length()));
		} else
		{
			while (text.length() < maximumInputLength)
			{
				text = "0" + text;
			}

			textComponent.setText(text);
		}
	}

	private boolean isValidInput(String text, int replaceLength)
	{
		boolean isLengthOkay = text.length() - replaceLength <= maximumLength;
		boolean isHexadecimal = isHexadecimal(text);

		return isLengthOkay && isHexadecimal;
	}

	/**
	 * @param input The input to verify
	 * @return True if the String only contains valid hexadecimal characters
	 */
	public static boolean isHexadecimal(String input)
	{
		return input.matches("^[0-9a-fA-F]+$");
	}
}