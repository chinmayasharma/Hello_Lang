package jackah2.hellolangfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActvity extends AppCompatActivity {


    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_actvity);

        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra(Feed.USER_OBJECT);

        TextView indUserName, indLanguage, indStatus, indType, indDescription;
        indUserName = (TextView) findViewById(R.id.ind_user_name);
        indLanguage = (TextView) findViewById(R.id.ind_language);
        indStatus = (TextView) findViewById(R.id.ind_status);
        indType = (TextView) findViewById(R.id.ind_user_type);
        indDescription = (TextView) findViewById(R.id.ind_description);

        indUserName.setText(user.getUserName());
        indLanguage.setText(user.getMainLanguage().toString());
        indStatus.setText(user.getStatus().toString());
        indStatus.setTextColor(user.getStatus().getColor());
        indType.setText(user.getType().toString());
        indDescription.setText(user.getDescription());

        callButton = (Button) findViewById(R.id.call_user_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feed.getCaller().call("caller1");
            }
        });
    }
}
