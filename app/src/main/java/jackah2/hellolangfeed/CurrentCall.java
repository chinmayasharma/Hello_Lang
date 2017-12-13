package jackah2.hellolangfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CurrentCall extends AppCompatActivity {

    Button hangupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_call);
        hangupButton = (Button) findViewById(R.id.hangup_button);
        hangupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Feed.getCaller().callIsNull()){
                    Feed.getCaller().hangup();
                    Feed.getCaller().setCallNull();
                    startActivity(new Intent(getApplicationContext(), Feed.class));
                }
            }
        });
    }
}
