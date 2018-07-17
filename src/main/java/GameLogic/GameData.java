package GameLogic;

import java.util.LinkedList;

import static java.lang.System.out;

public class GameData {
    LinkedList<Box> boxlist;
    String status;

    public GameData()
    {
        resetboxlist();

    }

    public void resetboxlist()
    {
        boxlist=new LinkedList<>();
        for(int i=0;i<9;i++)
            boxlist.add(new Box());
        status="open";
    }
}

