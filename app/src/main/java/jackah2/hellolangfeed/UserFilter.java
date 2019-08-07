package jackah2.hellolangfeed;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xmrpo on 10/20/2017.
 */

public class UserFilter implements Parcelable{

    private Language language;
    private Status status;
    private UserType type;

    public UserFilter(Language language, Status status, UserType type){
        this.language = language;
        this.status = status;
        this.type = type;
    }

    public UserFilter(){
        clear();
    }

    public void clear(){
        this.language = null;
        this.status = null;
        this.type = null;
    }

    public Language getLanguage(){
        return language;
    }

    public void setLanguage(Language language){
        this.language = language;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public UserType getType(){
        return type;
    }

    public void setType(UserType type){
        this.type = type;
    }

    public List<User> filter(List<User> users){
        List<User> filtered = new ArrayList<>(users);

        if(language == null && status == null && type == null){
            return filtered;
        }

        Iterator<User> filterIt = filtered.iterator();
        User current;

        while (filterIt.hasNext()) {

            current = filterIt.next();

            if (language != null){
                if (current.getMainLanguage() != language) {
                    filterIt.remove();
                    continue;
                }
            }

            if (status != null){
                if (current.getStatus() != status) {
                    filterIt.remove();
                    continue;
                }
            }

            if (type != null) {
                if (current.getType() != type) {
                    filterIt.remove();
                    continue;
                }
            }
        }

        return filtered;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeInt(this.language == null ? -1 : this.language.ordinal());
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected UserFilter(Parcel in) {
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
        int tmpLanguage = in.readInt();
        this.language = tmpLanguage == -1 ? null : Language.values()[tmpLanguage];
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : UserType.values()[tmpType];
    }

    public static final Creator<UserFilter> CREATOR = new Creator<UserFilter>() {
        @Override
        public UserFilter createFromParcel(Parcel source) {
            return new UserFilter(source);
        }

        @Override
        public UserFilter[] newArray(int size) {
            return new UserFilter[size];
        }
    };
}
