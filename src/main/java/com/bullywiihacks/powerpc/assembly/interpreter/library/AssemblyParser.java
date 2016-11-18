package com.bullywiihacks.powerpc.assembly.interpreter.library;

import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.implementations.*;
import com.bullywiihacks.powerpc.assembly.interpreter.library.instructions.AssemblyInstruction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AssemblyParser
{
	private List<AssemblyInstruction> existingAssemblyInstructions;

	public AssemblyParser()
	{
		existingAssemblyInstructions = new ArrayList<>();
		existingAssemblyInstructions.add(new LoadImmediate(null, null));
		existingAssemblyInstructions.add(new LoadImmediateShifted(null, null));
		existingAssemblyInstructions.add(new Add(null, null, null));
		existingAssemblyInstructions.add(new Subtract(null, null, null));
		existingAssemblyInstructions.add(new NoOperation());
	}

	public List<AssemblyInstruction> parseAssembly(File assemblyFile)
			throws IOException
	{
		String assemblyCode = new String(Files.readAllBytes(assemblyFile.toPath()));

		return parseAssembly(assemblyCode);
	}

	public List<AssemblyInstruction> parseAssembly(String assemblyCode)
	{
		String[] assemblyLines = assemblyCode.split("\n");

		int assemblyLinesCount = assemblyLines.length;

		List<AssemblyInstruction> assemblyInstructions = new ArrayList<>();

		for (int assemblyInstructionsIndex = 0; assemblyInstructionsIndex < assemblyLinesCount; assemblyInstructionsIndex++)
		{
			String currentAssemblyLine = assemblyLines[assemblyInstructionsIndex];

			// Ignore single line comments
			if (!currentAssemblyLine.startsWith("#"))
			{
				int assemblyInstructionsCount = assemblyInstructions.size();
				parse(assemblyInstructions, currentAssemblyLine);

				if (assemblyInstructions.size() == assemblyInstructionsCount)
				{
					// Nothing has been added
					throw new IllegalArgumentException(
							"Unknown or unimplemented command \""
									+ currentAssemblyLine + "\" in line "
									+ (assemblyInstructionsIndex + 1) + "!");
				}
			}
		}

		return assemblyInstructions;
	}

	private void parse(List<AssemblyInstruction> assemblyInstructions, String currentAssemblyLine)
	{
		existingAssemblyInstructions.stream().filter(assemblyInstruction -> startsWith(currentAssemblyLine, assemblyInstruction)).forEach(assemblyInstruction ->
		{
			assemblyInstruction = assemblyInstruction.parse(currentAssemblyLine);
			assemblyInstructions.add(assemblyInstruction);
		});
	}

	private boolean startsWith(String line, AssemblyInstruction assemblyInstruction)
	{
		return line.startsWith(assemblyInstruction.getMnemonic());
	}
}