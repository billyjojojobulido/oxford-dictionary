import controller.RequestWindowController;
import model.Database;
import model.HTTPManager;
import model.Model;
import model.ModelFacade;

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

        Database db = new Database();
        HTTPManager manager = new HTTPManager();

        Model model =  new ModelFacade(manager, db);

        model.logIn("fd4d90e2", "6d7c205689f3404cb617d0b0a402b8de");

        RequestWindowController controller = new RequestWindowController(model);

        controller.run();

    }

}
