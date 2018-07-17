package TickTackToe;

import GameLogic.GameLogic;
import InstanceObjects.GameInstance;
import UserInsatance.UserInfo;
import UserInsatance.Userlistoperations;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import static java.lang.System.out;


public class ticktacktoe {



    private static String word(){
       String a=
               Character.toString((char) (new Random().nextInt(26) + 'A')) +
              Character.toString( (char) (new Random().nextInt(26) + 'a') )+
                Character.toString((char) (new Random().nextInt(26) + 'a'));
        return a;}

    public static GameLogic GL;

    public static String password=null;
        public static Userlistoperations ulo;

        public static GameInstance GI;
        static loadenv.loadenv le=new loadenv.loadenv();
    static String uid;
    static String type="ticktacktoe";
    static int totalslots=2;
    static String host=le.gethostip();

    public static void main(String... argv) throws IOException {
        if (argv.length==1)
        {
            password=argv[0];
            out.println("password set");
        }
        reset();
    }

    public static void  reset() throws IOException
    {
        out.println("System in Inital state");

        uid= UUID.randomUUID().toString();

        GI=new GameInstance(word(), uid, type, totalslots);

        ulo=new Userlistoperations(totalslots,GI);

        GL=new GameLogic(host,ulo);

        new recvinforequest(host,GI);

        new recv(host,uid,GI);

    }
}
