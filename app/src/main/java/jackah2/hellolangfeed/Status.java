package jackah2.hellolangfeed;

import android.graphics.Color;

/**
 * Created by xmrpo on 10/16/2017.
 */

public enum Status {
    ONLINE(R.string.online, R.color.online),
    OFFLINE(R.string.offline, R.color.offline);

    private int strID, colorID;

    private Status(int strID, int colorID){
        this.strID = strID;
        this.colorID = colorID;
    }

    @Override
    public String toString() {
        return Feed.getContext().getResources().getString(strID);
    }

    public int getColor(){
        return Color.parseColor(Feed.getContext().getResources().getString(colorID));
    }

    public static Status match(String str){
        for(Status status : Status.values())
            if(status.toString().equalsIgnoreCase(str))
                return status;
        return null;
    }
}
