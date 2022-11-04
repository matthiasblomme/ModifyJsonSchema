package utils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Command
{
    static StreamGobbler errorGobbler;
    static StreamGobbler outputGobbler;

    public static ArrayList<> getOutput() {
        return outputGobbler.getOutput();
    }

    public static ArrayList getError() {
        return errorGobbler.getOutput();
    }

    public static int Exec(String[] args){
        return Exec("cmd.exe /c", args);
    }

    public static int Exec(String executable, String[] args){
        return Exec(System.getProperty("user.home"), executable, args);

    }

    public static int Exec(String runDirectory, String executable, String[] args)
    {
        try
        {
            ArrayList<String> command = new ArrayList<>();
            command.add(executable);
            command.addAll(Arrays.asList(args));
            System.out.println(command);

            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new File(runDirectory));
            builder.command(command);

            Process proc = builder.start();
            // any error message?
            errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            return proc.waitFor();
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
        return -1;
    }

    public static void CommandLineRunner(String command){
        int exitValue = 0;
        CommandLine oCmdLine = CommandLine.parse(command);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            exitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
    }
}
