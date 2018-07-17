package GameLogic;

import TickTackToe.sendinqueue;
import UserInsatance.Userlistoperations;
import com.google.gson.Gson;

import java.util.LinkedList;

import static TickTackToe.ticktacktoe.GI;
import static TickTackToe.ticktacktoe.GL;
import static TickTackToe.ticktacktoe.ulo;
import static java.lang.System.out;


public class GameLogic {

    public static GameProcess GP;
    public static String presentuser="";

    public GameLogic(){}

    public GameLogic(String host, Userlistoperations ulo)
    {
        String returnstring="{\"user\":\"Created Instance\",\"data\":\"{}\"}";
        String clientqueue="from_"+ulo.getGI().getuuid();
        String EX_name="gameserver";
        new sendinqueue().sendinexchange(host, clientqueue, returnstring,EX_name);
        GP=new GameProcess();

    }

    private String getturn()
    {
        LinkedList<String> users=ulo.getUuids();
        if(presentuser.compareTo(users.get(0))==0)
            presentuser=users.get(1);
        else
            presentuser=users.get(0);
        return presentuser;

    }
    private void updatestatus()
    {
        int slots=ulo.getemptyslots();
        if(slots!=0)
        {
            GP.gd.status="open";
        }
        else
        {
            GP.gd.status="playing";
        }
    }
    private void updatestatus(String up)
    {
        GP.gd.status=up;
    }

    public void adduser(String host)
    {
        updatestatus();
        presentuser=getturn();
        String returnstring=processreturnstring();

        sendupdates(host,returnstring);

    }

    private void sendupdates(String host,String returnstring)
    {
        String clientqueue="from_"+ulo.getGI().getuuid();
        String EX_name="gameserver";
        new sendinqueue().sendinexchange(host, clientqueue, returnstring,EX_name);

    }

    public String processreturnstring()
    {
        String user=presentuser;
        GameData gd=GP.gd;

        String returnstring= new Gson().toJson(gd).replace('"','\'');

        String out="{\"user\":\""+user+"\",\"data\":\""+returnstring+"\"}";

        return out;
    }

    public void click(String host,int click)
    {
        GP.gd.boxlist.get(click).setValue(presentuser);

        boolean reset=false;
        if(win(presentuser))
        {
            reset=true;
            updatestatus(presentuser);

        }
        else if(draw(presentuser))
        {
            reset=true;
            updatestatus("draw");

        }

        presentuser=getturn();
        String returnstring=processreturnstring();

        sendupdates(host,returnstring);
        if(reset)
        {
            GI.resetuser();
            GL.GP.gd.resetboxlist();
        }


    }


    private boolean win(String presentuser) {
        LinkedList<Box> bl=GP.gd.boxlist;
        String [][] valuearray=new String[3][3];

        for(int i=0;i<bl.size();i++)
        {
            valuearray[i%3][i/3]=bl.get(i).getValue();
        }


        return firstloop(valuearray,presentuser);

    }

    public boolean firstloop(String [][] valuearray,String user)
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                out.print(i);
                out.print(j);
                out.println(valuearray[i][j]);
                if(valuearray[i][j].compareTo(user)==0)
                    if(loopfind(valuearray,i,j))
                        return true;
            }
        }
        return false;
    }

    private boolean loopfind(String[][] valuearray, int I, int J)
    {
        for(int i=-1;i<2;i++)
        {
            for(int j=-1;j<2;j++)
            {
                if(i==0 && j==0)
                    continue;
//                out.print("move ");
//                out.print(i);
//                out.println(j);
                if(ifwinmatch(valuearray,I,J,i,j,0))
                    return true;
            }
        }
        return false;
    }

    private boolean ifwinmatch(String[][] valuearray, int i, int j, int i1, int j1,int count ) {

        if(count==2)
            return true;

        int I=i+i1;
        int J=j+j1;

//        out.print("points ");
//        out.print("i j ");
//        out.print(i);
//        out.print(j);
//        out.print(" I J ");
//        out.print(I);
//        out.println(J);
        if(I<0 || I>2 || J<0 || J>2 || i<0 || i>2 || j<0 || j>2)
            return false;

//        out.print(valuearray[i][j]);
//        out.println(valuearray[I][J]);
        if(valuearray[i][j].compareTo(valuearray[I][J])==0)
            return ifwinmatch(valuearray,I,J,i1,j1,count+1);

        return false;
    }

    private boolean draw(String presentuser) {

        for(Box o : GP.gd.boxlist)
        {
            out.println(o.getValue());
            if(o.getValue().compareTo("open")==0)
                return false;
        }
        return true;
    }

    public void removeuser(String host) {
        updatestatus("User Exited");
        presentuser="";

        String returnstring=processreturnstring();

        sendupdates(host,returnstring);

    }
}
