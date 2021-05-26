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
    }

}
