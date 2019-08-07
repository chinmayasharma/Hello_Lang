package jackah2.hellolangfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sinch.android.rtc.calling.Call;

public class IncomingCall extends AppCompatActivity {

    Button acceptCall, declineCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        acceptCall = (Button) findViewById(R.id.accept_call_button);
        declineCall = (Button) findViewById(R.id.decline_call_button);

        acceptCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Feed.getCaller().callIsNull()) return;
                Call call = Feed.getCaller().getCurrentCall();
                call.answer();
                startActivity(new Intent(getApplicationContext(), CurrentCall.class));
            }
        });

        declineCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Feed.getCaller().callIsNull()) return;
                Call call = Feed.getCaller().getCurrentCall();
                call.hangup();
                Feed.getCaller().setCallNull();
                startActivity(new Intent(getApplicationContext(), Feed.class));
            }
        });
    }
}
