package jackah2.hellolangfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jack on 10/8/2017.
 */

public class FeedAdapter extends ArrayAdapter<User> {

    public FeedAdapter(Context context, List<User> users){
        super(context, 0, users);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feed_users, parent, false);
        }

        TextView userName, spokenLanguage, status, userType, description;
        userName = (TextView) convertView.findViewById(R.id.user_name);
        spokenLanguage = (TextView) convertView.findViewById(R.id.spoken_language);
        status = (TextView) convertView.findViewById(R.id.status);
        userType =(TextView) convertView.findViewById(R.id.type_of_user);

        userName.setText(user.getUserName());
        spokenLanguage.setText(user.getMainLanguage().toString());
        status.setText(user.getStatus().toString());
        status.setTextColor(user.getStatus().getColor());
        userType.setText(user.getType().toString());

        return convertView;
    }
}
