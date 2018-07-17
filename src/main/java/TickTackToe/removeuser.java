package TickTackToe;

import InstanceObjects.GameInstance;
import org.json.JSONObject;

import java.io.IOException;

import static TickTackToe.ticktacktoe.GL;
import static TickTackToe.ticktacktoe.reset;
import static TickTackToe.ticktacktoe.ulo;

public class removeuser implements processinput {
    public void process(String data, String host, GameInstance GI) {
        System.out.println(data+" working");
        JSONObject jsonObj = new JSONObject(data);

        String name=jsonObj.getString("name");
        String uuid=jsonObj.getString("uuid");


        if(ulo.removeuser(uuid,name))
            GL.removeuser(host); //game logic to send data to clients
        else
        {
            String returnstring="{\"error\":\"Critical Error Removing Close the windows please\",\"uuid\":\""+uuid+"\"}";

            String clientqueue="from_"+ulo.getGI().getuuid();
            String EX_name="gameserver";
            new sendinqueue().sendinexchange(host, clientqueue, returnstring,EX_name);
        }

        if(ulo.getusers().size()==0)
        {
                GI.resetuser();
                GL.GP.gd.resetboxlist();

        }

    }

}
