package jackah2.hellolangfeed;

import android.content.Intent;
import android.widget.Toast;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

/**
 * Created by xmrpo on 12/5/2017.
 */

public class Caller {

    private static final String APP_KEY = "7a6093eb-aef9-46f9-b733-02795bd9a330";
    private static final String APP_SECRET = "D1AU/p9tNUWvtfv0XaUsZA==";
    private static final String ENVIRONMENT = "clientapi.sinch.com";

    private SinchClient sinchClient;
    private Feed context;

    private Call currentCall = null;

    public Caller(final Feed context, String userID){

        this.context = context;

        sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                .userId(userID)
                .build();
        //TODO: Change userId to be accurate with the user using the app

        sinchClient.getCallClient().setRespectNativeCalls(false);

        sinchClient.setSupportCalling(true);

        sinchClient.addSinchClientListener(new SinchClientListener() {
            public void onClientStarted(SinchClient client) {
                //Toast.makeText(context, "Sinch Started", Toast.LENGTH_SHORT).show();
            }
            public void onClientStopped(SinchClient client) {
                //Toast.makeText(context, "Sinch stopped", Toast.LENGTH_SHORT).show();
            }
            public void onClientFailed(SinchClient client, SinchError error) {
                //Toast.makeText(context, "SINCH FAILED", Toast.LENGTH_SHORT).show();
            }
            public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration registrationCallback) {
                //Toast.makeText(context, "Sinch client registration required", Toast.LENGTH_SHORT).show();
            }
            public void onLogMessage(int level, String area, String message) {
                //Toast.makeText(context, "Message: " + message, Toast.LENGTH_SHORT).show();
            }
        });

        sinchClient.startListeningOnActiveConnection();

        sinchClient.start();

        sinchClient.getCallClient().addCallClientListener(new CallClientListener() {
            @Override
            public void onIncomingCall(CallClient callClient, Call incomingCall) {
                Toast.makeText(context, "Receiving call!", Toast.LENGTH_SHORT).show();

                incomingCall.addCallListener(new CallListener() {
                    @Override
                    public void onCallProgressing(Call call) {
                        Toast.makeText(context, "Incoming progressing", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCallEstablished(Call call) {
                        Toast.makeText(context, "Incoming established", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCallEnded(Call call) {
                        Toast.makeText(context, "Incoming ended", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShouldSendPushNotification(Call call, List<PushPair> list) {
                        Toast.makeText(context, "should send notification I guess", Toast.LENGTH_SHORT).show();
                    }
                });
                currentCall = incomingCall;
                context.startActivity(new Intent(context, IncomingCall.class));
            }
        });
    }

    public Call getCurrentCall(){
        return currentCall;
    }

    public void setCallNull(){
        currentCall = null;
    }

    public void hangup(){
        currentCall.hangup();
        currentCall = null;
    }

    public boolean callIsNull(){
        return currentCall == null;
    }

    public void call(String remoteID){
        CallClient callClient = sinchClient.getCallClient();
        Call call = callClient.callUser(remoteID);
        //Toast.makeText(context, "Attempting to call...", Toast.LENGTH_SHORT).show();
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

    public SinchClient getClient() {
        return sinchClient;
    }
}
