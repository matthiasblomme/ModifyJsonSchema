import utils.Command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JsonixRunner {
    static String[] commandString;
    static Command comm;
    static ArrayList<String> output;
    static ArrayList<String> error;
    static Integer exitValue;
    static String jsonSchema;
    static String tempDirectory;
    static String tempScript;
    public JsonixRunner(String inputXsdSchema, String outputDirectory, String jsonSchemaObject) throws Exception {
        //java -jar JSONIX/jsonix-schema-compiler-full-2.3.9.jar -compact -generateJsonSchema test\Note.xsd -d test\jsonixout -p Note
        String[] pathsToCheck = new String[]{"JSONIX/jsonix-schema-compiler-full-2.3.9.jar", inputXsdSchema, outputDirectory};
        checkPaths(pathsToCheck);
        commandString = new String[]{"java", "-jar", pathsToCheck[0], "-compact", "-generateJsonSchema", "\"" + pathsToCheck[1] + "\"", "-d", "\"" + pathsToCheck[2] + "\"" , "-p", jsonSchemaObject};
        tempDirectory = commandString[7].replace("\"", "") + File.separator + "tempScript";
        tempScript = tempDirectory + File.separator + "jsonix.bat";
    }

    private void checkPaths(String[] pathCheck) throws Exception {
        List<String> changedPaths = Arrays.stream(pathCheck).map(s -> {
            Path p = Paths.get(s);
            if (!p.isAbsolute()) s = p.toAbsolutePath().toString();
            if (!p.toFile().exists()) throw new RuntimeException(s + " not found on the system");
            return s;
        }).collect(Collectors.toList());
        changedPaths.toArray(pathCheck);
    }

    static void execute() throws Exception {
        //create dir
        File d = new File(tempDirectory);
        d.mkdirs();

        //create script
        File f = new File(tempScript);
        //System.out.println("Writing " + tempScript);
        f.createNewFile();

        //write script
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempScript));
        writer.write(String.join(" ", commandString));
        writer.close();

        //run script
        comm = new Command();
        //exitValue = comm.Exec("java -version", new String[]{tempScript});
        comm.CommandLineRunner(tempScript);

        //delete script
        Files.walk(d.toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    static ArrayList<String> getOutput(){
        ArrayList<String> output = comm.getOutput();
        return output;
    }

    static ArrayList<String> getError(){
        ArrayList<String> error = comm.getError();
        return error;
    }

    public String getJsonSchema() throws Exception {
        String outDir = commandString[7].replace("\"", "");
        String fileName = commandString[9] + ".jsonschema";
        String generatedJsonSchema = outDir + File.separator + fileName;

        File jsonSchemaFile = new File(generatedJsonSchema);
        if (!jsonSchemaFile.exists()) throw new Exception("File " + generatedJsonSchema + " not found on the system");

        return generatedJsonSchema;
    }


    public boolean failed() {
        if (exitValue > 0) return true;
        return false;
    }

    public void cleanUp() {
        File f = new File(commandString[7].replace("\"", "") + File.separator + commandString[9] + ".jsonschema");
        f.delete();
        f = new File(commandString[7].replace("\"", "") + File.separator + commandString[9] + ".js");
        f.delete();
    }
}
