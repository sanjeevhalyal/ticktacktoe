package GameLogic;


import  org.junit.*;

import static java.lang.System.out;

public class GameLogicTest {


    @Test
    public void firstloop() {
        out.println("test");
        String[][] pass=new String[][]{
                {"1","0","0"},
                {"0","0","0"},
                {"1","1","1"}
        };

        out.println(new GameLogic().firstloop(pass,"1"));
    }
}
