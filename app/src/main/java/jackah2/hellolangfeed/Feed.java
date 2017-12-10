package jackah2.hellolangfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    private static Context _context;

    private final List<User> users = new ArrayList<>();
    private final List<User> filteredUsers = new ArrayList<>();
    private static final UserFilter userFilter = new UserFilter();

    private FeedAdapter feedAdapter;
    private ListView listView;
    private Button filterUsersButton, editProfileButton;

    private TextView nameText, languageText, statusText, typeText;

    public static final String USER_OBJECT = "user_object";
    public static final String FILTER_OBJECT = "filter_object";

    private Caller caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        _context = this;

        this.caller = new Caller(this);

        nameText = (TextView) findViewById(R.id.personal_user_name);
        languageText = (TextView) findViewById(R.id.personal_language);
        typeText = (TextView) findViewById(R.id.personal_type);
        statusText = (TextView) findViewById(R.id.personal_status);

        applyUserOptions();

        feedAdapter = new FeedAdapter(this, filteredUsers);

        listView = (ListView) findViewById(R.id.user_list);
        listView.setAdapter(feedAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), UserActvity.class);

                intent.putExtra(USER_OBJECT, user);

                startActivity(intent);
            }
        });

        users.add(new User("Jack", Language.ENGLISH, UserType.STUDENT, Status.ONLINE));
        users.add(new User("Bob", Language.MANDARIN, UserType.STUDENT, Status.OFFLINE));
        users.add(new User("Nick", Language.SPANISH, UserType.STUDENT, Status.OFFLINE));
        users.add(new User("Charlie", Language.FRENCH, UserType.TEACHER, Status.ONLINE));
        users.add(new User("user1", Language.ENGLISH, UserType.TEACHER, Status.OFFLINE));
        users.add(new User("user2", Language.FRENCH, UserType.TEACHER, Status.ONLINE));
        users.add(new User("user3", Language.MANDARIN, UserType.TEACHER, Status.ONLINE));
        users.add(new User("user4", Language.SPANISH, UserType.TEACHER, Status.ONLINE));
        users.add(new User("user5", Language.ENGLISH, UserType.STUDENT, Status.ONLINE));
        users.add(new User("user6", Language.MANDARIN, UserType.STUDENT, Status.OFFLINE));
        users.add(new User("user7", Language.FRENCH, UserType.STUDENT, Status.ONLINE));
        users.add(new User("user8", Language.SPANISH, UserType.STUDENT, Status.OFFLINE));
        users.add(new User("user9", Language.ENGLISH, UserType.STUDENT, Status.ONLINE));


        filterUsersButton = (Button) findViewById(R.id.filter_users_button);
        filterUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilterChooser.class);

                ArrayList<UserFilter> filter = new ArrayList<>();
                filter.add(userFilter);

                intent.putParcelableArrayListExtra(FILTER_OBJECT, filter);

                startActivity(intent);
            }
        });

        editProfileButton = (Button) findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileEditor.class));
            }
        });

        updateFilter();
    }

    public static Context getContext(){
        return _context;
    }

    public void updateFilter(){
        filteredUsers.clear();
        filteredUsers.addAll(userFilter.filter(users));
        feedAdapter.notifyDataSetChanged();
    }

    private void applyUserOptions(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(ProfileEditor.PRER_FILE_NAME, MODE_PRIVATE);
        String name = pref.getString(ProfileEditor.NAME, ProfileEditor.DEF_NAME);
        Language language = Language.match(pref.getString(ProfileEditor.LANGUAGE, ProfileEditor.DEF_NAME));
        UserType type = UserType.match(pref.getString(ProfileEditor.TYPE, ProfileEditor.DEF_TYPE));
        Status status = Status.match(pref.getString(ProfileEditor.STATUS, ProfileEditor.DEF_STAT));

        nameText.setText(name);
        if (language != null) languageText.setText(language.toString());
        if (type != null) typeText.setText(type.toString());
        if (status != null) {
            statusText.setText(status.toString());
            statusText.setTextColor(status.getColor());
        }
    }

    public static UserFilter getUserFilter(){
        return userFilter;
    }

    public List<User> getUsers(){
        // TODO
        // Implement method to get users of app from database

        return null;
    }


}
