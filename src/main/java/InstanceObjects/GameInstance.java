package InstanceObjects;

import GameLogic.Box;
import TickTackToe.ticktacktoe;

import java.util.LinkedList;
import java.util.List;

public class GameInstance {
    private String name;
    private String uuid;
    private String type;
    private int totalslots;
    private List<String> users;
    private String password;

    void resetlist()
    {
        this.users= new LinkedList<String>();
    }
    public boolean isType(String type) {
        return this.type.compareTo(type)==0;
    }

    public GameInstance(String name, String uuid, String type, int totalslots) {
        this.name = name;
        this.uuid = uuid;
        this.type = type;
        this.totalslots=totalslots;
        this.users= new LinkedList<String>();
        if(ticktacktoe.password!=null)
            this.password="yes";
    }

    public int getnumofemptyslots()
    {
        return totalslots-users.size();
    }

    public boolean addusers(String user)
    {
        return users.add(user);
    }
    public boolean removeuser(String user)
    {
        return users.removeIf(s -> s.compareTo(user)==0);

    };


    public boolean matchuuid(String uuid)
    {
        return this.uuid.compareTo(uuid)==0;
    }

    public String getuuid()
    {
        return uuid;
    }
    public boolean resetuser()
    {
         users= new LinkedList<String>();
        return true;
    }



}
