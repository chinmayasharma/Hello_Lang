package jackah2.hellolangfeed;

import android.widget.Toast;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

/**
 * Created by xmrpo on 12/5/2017.
 */

public class Caller {

    private static final String APP_KEY = "4b2d8673-ac2a-4e39-9444-2ab06d7c6337";
    private static final String APP_SECRET = "AKIl3giez0aGTQm+TAGJUw==";
    private static final String ENVIRONMENT = "clientapi.sinch.com";

    private SinchClient sinchClient;
    private Feed context;

    public Caller(Feed context){

        sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                .userId("JACK")
                .build();
        //TODO: Change userId to be accurate with the user using the app

        sinchClient.setSupportCalling(true);

        sinchClient.startListeningOnActiveConnection();

        sinchClient.addSinchClientListener(new SinchClientListener() {
            public void onClientStarted(SinchClient client) { }
            public void onClientStopped(SinchClient client) { }
            public void onClientFailed(SinchClient client, SinchError error) { }
            public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration registrationCallback) { }
            public void onLogMessage(int level, String area, String message) { }
        });
        sinchClient.start();
    }

    public void call(String remoteID){
        CallClient callClient = sinchClient.getCallClient();
        Call call = callClient.callUser(remoteID);
        call.addCallListener(new CallListener() {
            @Override
            public void onCallProgressing(Call call) {
                Toast.makeText(context, "Calling user...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCallEstablished(Call call) {
                Toast.makeText(context, "Call established!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCallEnded(Call call) {
                Toast.makeText(context, "Call ended.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) { }
        });
    }
}
