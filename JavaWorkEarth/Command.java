import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Command {
    
    public Command(){
    }
    public Command(String name, List<Object> params){
        this.name = name;
        this.params = params;
    }

    //Attribute
    private String name ;
    private List<Object> params ;
    
    //Get , Set {Method}
    public void setName(String name)
    {
        this.name = name;
    }
    public void setParams(List<Object> params)
    {
        this.params = params;
    }
    
    public String getName()
    {
        return name;
    }
    public List<Object> getParams()
    {
        return params;
    }

    //Method to Check String from .txt
    public void getStringLine(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String infoLine;
            while ((infoLine = reader.readLine()) != null) {
               System.out.println(infoLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method ReadFile and Spilt to make a command
    public List<Command> getCommandsFromFileName(String fileName) {

        List<Command> commands = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String infoLine;
            while ((infoLine = reader.readLine()) != null) {
                // System.out.println("Before cut");
                // System.out.println(infoLine);
                String[] parts = infoLine.split(" ");
                // System.out.println("------------------------------");
                // for (String part : parts) {
                //     System.out.println(part);
                // }
                String commandName = parts[0];
                //System.out.println(parts.length);
                String[] params = new String[parts.length - 1];

                for (int i = 1; i < parts.length; i++) {
                    params[i - 1] = parts[i];
                }
                // System.out.println("++++++++++++++++");
                // for (String parm : params) {
                //     System.out.println(parm);
                // }
                // System.out.println("++++++++++++++++");
                List<Object> parsedParams = new ArrayList<>();

                for (String param : params) {
                    try {
                        parsedParams.add(Integer.parseInt(param));
                    } catch (NumberFormatException e) {
                        parsedParams.add(param);
                    }
                }
                commands.add(new Command(commandName, parsedParams));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }
}
