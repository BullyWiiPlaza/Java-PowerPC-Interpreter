import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyInterpreter;
import com.bullywiihacks.powerpc.assembly.interpreter.library.PPCAssemblyParser;
import com.bullywiihacks.powerpc.assembly.interpreter.graphical_interface.AssemblyInterpreterGUI;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Testing
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
		PPCAssemblyInterpreter assemblyInterpreter = new PPCAssemblyInterpreter();
		PPCAssemblyParser parser = new PPCAssemblyParser();
		List<AssemblyInstruction> assemblyInstructions = parser.parseAssembly(new File("assembly.txt"));
		for (AssemblyInstruction assemblyInstruction : assemblyInstructions)
		{
			System.out.println(assemblyInstruction);
			assemblyInstruction.execute(assemblyInterpreter);
		}

		System.out.println();

		assemblyInterpreter.printAllDataRegisters();
	}
}
