package TickTackToe;

import InstanceObjects.GameInstance;

public interface processinput {

    default void process(String data, String host, GameInstance GI)
    {
        System.out.println("Data not processed no implementation created");
    }
}
