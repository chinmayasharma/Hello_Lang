package jackah2.hellolangfeed;

import java.io.Serializable;

/**
 * Created by xmrpo on 10/6/2017.
 */

public class User implements Serializable{

    private final String userName;
    private final Language mainLanguage;
    private final UserType userType;
    private Status status;
    private String description;

    public User(String userName, Language mainLanguage, UserType userType, Status status){
        this.userName = userName;
        this.mainLanguage = mainLanguage;
        this.userType = userType;
        this.status = status;
        this.description = "This is the default description right now."
                + "\nThis will be modifiable later on."
                + "\nFiller filler filler filler filler filler.";
    }

    public String getUserName(){
        return userName;
    }

    public Language getMainLanguage(){
        return mainLanguage;
    }

    public UserType getType(){
        return userType;
    }

    public Status getStatus(){
        return status;
    }

    public String getDescription(){
        return description;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
