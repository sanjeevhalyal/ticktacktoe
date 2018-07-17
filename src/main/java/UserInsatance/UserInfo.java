package UserInsatance;

import InstanceObjects.GameInstance;

public class UserInfo {
    private String name;
    private String uuid;


    public UserInfo(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;

    }
    public String getuuid()
    {
        return this.uuid;
    }

    public String getuser()
    {
        return this.name;
    }
}
