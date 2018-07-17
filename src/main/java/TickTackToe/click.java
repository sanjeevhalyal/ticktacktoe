package TickTackToe;

import InstanceObjects.GameInstance;
import org.json.JSONObject;

import static TickTackToe.ticktacktoe.GL;

public class click implements processinput {
    public void process(String data, String host, GameInstance GI) {

        JSONObject jsonObj = new JSONObject(data);
        int click=jsonObj.getInt("click");
        GL.click(host,click);
    }
}
