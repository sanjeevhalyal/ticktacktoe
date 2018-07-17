package InstanceObjects;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

public class Instancelist {
    private List<GameInstance> list;

    public Instancelist() {
        this.list = new LinkedList<GameInstance>();
    }

    public boolean addusertoinstance(String uuid, String user) {
        for (GameInstance o : list) {
            if (o.matchuuid(uuid) && o.getnumofemptyslots()>0) {
                return o.addusers(user);
            }
        }
        return false;
    }

    public boolean removeuserinstance(String uuid, String user) {
        for (GameInstance o : list) {
            if (o.matchuuid(uuid)) {
                return o.removeuser(user);
            }
        }
        return false;
    }

    public boolean removeinstance(String uuid) {
        for (GameInstance o : list) {
            if (o.matchuuid(uuid)) {
                return list.remove(o);
            }
        }
        return false;
    }

    public String addgameinstance(String uuid, String type, int totalslots) {
        String name=null;

        if(getinstance(uuid)!=null) {
            out.println("found in system");
            return name;
        }
        name = type + ":" + Integer.toString(list.size() + 1);
        list.add(new GameInstance(name, uuid, type, totalslots));
        return name;
    }

    public boolean resetinstance(String uuid) {

        GameInstance i = getinstance(uuid);

        return i != null && i.resetuser();
    }

    public GameInstance getinstance(String uuid)
    {
        GameInstance i=null;
        for (GameInstance o : list) {
            if (o.matchuuid(uuid)) {
                i=o;
                out.println(i.getnumofemptyslots());
                break;
            }
        }
            return i;
    }

    public LinkedList<GameInstance> instanceoftype(String type) {
        LinkedList<GameInstance> out = new LinkedList<GameInstance>();
        for (GameInstance o : list) {
            if (o.isType(type)) {
                out.add(o);
            }
        }
        return out;
    }

    public String availableinstances(String type) {

        List<GameInstance> out = instanceoftype(type);

        LinkedList<GameInstance> foo = new LinkedList<GameInstance>();

        for (GameInstance o : out) {
            if (o.getnumofemptyslots() > 0) {
                foo.add(o);
            }
        }
        String json = new Gson().toJson(foo);
        return json;
    }
}
