import java.io.IOException;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
    public class test {
    static String command = "java -jar C:\\Matthias\\GIT\\ModifyJsonSchema\\JSONIX\\jsonix-schema-compiler-full-2.3.9.jar -compact -generateJsonSchema C:\\Matthias\\GIT\\ModifyJsonSchema\\test\\Note.xsd -d C:\\Matthias\\GIT\\ModifyJsonSchema\\test\\jsonixout -p Note";
    static String[] commandArray = new String[]{"java", "-jar", "C:\\Matthias\\GIT\\ModifyJsonSchema\\JSONIX\\jsonix-schema-compiler-full-2.3.9.jar", "-compact", "-generateJsonSchema", "C:\\Matthias\\GIT\\ModifyJsonSchema\\test\\Note.xsd", "-d","C:\\Matthias\\GIT\\ModifyJsonSchema\\test\\jsonixout", "-p", "Note"};
    static int iExitValue;
    static String sCommandString;

    public static void runScript(String command) throws IOException {
        sCommandString = command;
        CommandLine oCmdLine = CommandLine.parse("C:\\Matthias\\GIT\\ModifyJsonSchema\\scripts\\run.cmd");
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        runScript(command);
    }
}
