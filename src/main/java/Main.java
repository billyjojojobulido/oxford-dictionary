import controller.RequestWindowController;
import model.Database;
import model.HTTPManager;
import model.Model;
import model.ModelFacade;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String[] arg = new String[2];
        if (args.length > 0) {
            if (args[0].equals("offline")) {
                System.out.println("OFFLINE");
                arg[0] = "offline";
            } else if (args[0].equals("online")) {
                System.out.println("ONLINE");
                arg[0] = "online";
            } else {
                System.out.println("Invalid Parameter");
            }
        }

        String apiId = "";
        String apiKey = "";
        String emailKey = "";
        String emailFrom = "";
        String emailTo = "";
        String emailReply = "";
        String senderName = "";
        String targetName = "";
        String replyName = "";

        String config = "";
        try{
            File jsonFile = new File("src/main/resources/configFile.json");
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1){
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            config = sb.toString();
        } catch (IOException e){
            return;
        }

        JSONParser parser = new JSONParser();

        try{
            JSONObject obj = (JSONObject) parser.parse(config);
            apiId = (String) obj.get("id");
            apiKey = (String) obj.get("key");
            emailKey = (String) obj.get("emailKey");
            emailFrom = (String) obj.get("emailFrom");
            emailTo = (String) obj.get("emailTo");
            emailReply = (String) obj.get("emailReply");
            senderName = (String) obj.get("senderName");
            targetName = (String) obj.get("targetName");
            replyName = (String) obj.get("replyName");
        } catch (ParseException e){
            return;
        }


        Database db = new Database();

        HTTPManager manager = new HTTPManager();

        Model model =  new ModelFacade(manager, db);

        model.logIn(apiId, apiKey);

        RequestWindowController controller = new RequestWindowController(model);

        controller.logIn(emailKey, emailFrom, emailTo, emailReply, senderName, targetName, replyName);

        controller.run();


    }

}
