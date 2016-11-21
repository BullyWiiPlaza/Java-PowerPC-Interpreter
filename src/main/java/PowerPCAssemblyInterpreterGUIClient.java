import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.AssemblyParser;
import com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.AssemblyInterpreterGUI;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class PowerPCAssemblyInterpreterGUIClient
{
	public static void main(String[] arguments) throws Exception
	{
		// Set system look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		// Start the application
		AssemblyInterpreterGUI assemblyInterpreterGUI = new AssemblyInterpreterGUI();
		assemblyInterpreterGUI.setVisible(true);
		// parserTesting();
	}

	private static void parserTesting() throws Exception
	{
		AssemblyInterpreter assemblyInterpreter = new AssemblyInterpreter();
		AssemblyParser parser = new AssemblyParser();
		List<AssemblyInstruction> assemblyInstructions = parser.parse(new File("assembly.txt"));
		for (AssemblyInstruction assemblyInstruction : assemblyInstructions)
		{
			System.out.println(assemblyInstruction);
			assemblyInstruction.execute(assemblyInterpreter);
		}

		System.out.println();

		assemblyInterpreter.printAllDataRegisters();
	}
}