package TickTackToe;

import InstanceObjects.GameInstance;
import org.json.JSONObject;

import static TickTackToe.ticktacktoe.GL;
import static TickTackToe.ticktacktoe.ulo;
import static java.lang.System.out;

public class adduser implements processinput {

    public void process(String data, String host, GameInstance GI)
    {
        out.println(data+" working");
        JSONObject jsonObj = new JSONObject(data);

        String name=jsonObj.getString("name");
        String uuid=jsonObj.getString("uuid");
        String password=jsonObj.getString("password");


        if(ticktacktoe.password!=null &&  ticktacktoe.password.compareTo(password)!=0)
        {
            String returnstring="{\"error\":\"Wrong Password\",\"uuid\":\""+uuid+"\"}";

            String clientqueue="from_"+ulo.getGI().getuuid();
            String EX_name="gameserver";

            out.println(ticktacktoe.password);
            out.println(password);
            new sendinqueue().sendinexchange(host, clientqueue, returnstring,EX_name);
            return;
        }



        if(ulo.adduser(name,uuid))
            GL.adduser(host); //game logic to send data to clients
        else
        {
            String returnstring="{\"error\":\"Slots Full or User exist\",\"uuid\":\""+uuid+"\"}";

            String clientqueue="from_"+ulo.getGI().getuuid();
            String EX_name="gameserver";
            new sendinqueue().sendinexchange(host, clientqueue, returnstring,EX_name);
        }

    }


}

