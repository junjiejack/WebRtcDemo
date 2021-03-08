package com.kevin.webrtcdemo.client;


import android.os.Handler;
import android.os.HandlerThread;

import com.kevin.webrtcdemo.bean.RoomConnectionParameters;
import com.kevin.webrtcdemo.bean.SignalingParameters;
import com.kevin.webrtcdemo.callback.RoomParametersFetcherEvents;
import com.kevin.webrtcdemo.callback.SignalingEvents;

/**
 * date:2021/3/7
 * user:Kevin
 * desc: socket客户端
 */
public class WebRtcSocketClient {

    private static final String TAG = "WSRTCClient";

    private enum ConnectionState {NEW,CONNECTED,CLOSED,ERROR}

    private final Handler handler;

    private SignalingEvents events; // 信令回调

    public WebRtcSocketClient(SignalingEvents events) {
        this.events = events;
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    // 连接room-->耗时操作,需要在子线程操作
    public void connectRoom(final RoomConnectionParameters parameters) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                String roomUrl = parameters.roomUrl+"/"+"join/"+parameters.roomId;

                RoomParametersFetcherEvents events = new RoomParametersFetcherEvents() {
                    @Override
                    public void onSignalingParametersReady(SignalingParameters params) {

                    }

                    @Override
                    public void onSignalingParametersError(String description) {

                    }
                };

                RoomParametersFetcher roomParametersFetcher = new RoomParametersFetcher(roomUrl,null,events);
                roomParametersFetcher.makeRequest();
            }
        });
    }
}
