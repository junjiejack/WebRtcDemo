package com.kevin.webrtcdemo;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.kevin.webrtcdemo.bean.RoomConnectionParameters;
import com.kevin.webrtcdemo.bean.SignalingParameters;
import com.kevin.webrtcdemo.callback.PeerConnectionEvents;
import com.kevin.webrtcdemo.callback.SignalingEvents;
import com.kevin.webrtcdemo.client.PeerConnectionClient;
import com.kevin.webrtcdemo.client.WebRtcSocketClient;
import com.kevin.webrtcdemo.view.PercentFrameLayout;

import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.RendererCommon;
import org.webrtc.SessionDescription;
import org.webrtc.StatsReport;
import org.webrtc.SurfaceViewRenderer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SignalingEvents, PeerConnectionEvents {

    // 远端
    SurfaceViewRenderer remoteView;
    PercentFrameLayout remotePercentLayout;
    // 本地端
    SurfaceViewRenderer localView;
    PercentFrameLayout localPercentLayout;
    // 连接客户端
    private PeerConnectionClient peerConnectionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 初始化界面
        initView();
        // 2. 初始化WebRtc
        initWebRtc();
        // 3. 连接room
        connectRoom();
    }

    private void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);
        remoteView = findViewById(R.id.remote_video_view);
        localView = findViewById(R.id.local_video_view);
        remotePercentLayout = findViewById(R.id.remote_video_layout);
        localPercentLayout = findViewById(R.id.local_video_layout);
    }

    private void initWebRtc() {
        // 一. 对控件进行初始化
        // 1. 创建openGl
        EglBase eglBase = EglBase.create();
        // 2. 获取上下文
        EglBase.Context eglBaseContext = eglBase.getEglBaseContext();
        // 3. 根据上下文初始化
        localView.init(eglBaseContext,null);
        remoteView.init(eglBaseContext,null);
        // 4. 设置本地端控件悬浮
        localView.setZOrderOnTop(true);
        localView.setEnableHardwareScaler(true);
        // 5. 设置远端缩放全屏以及镜像
        remoteView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        remoteView.setMirror(true);
        remoteView.setEnableHardwareScaler(true);

        // 二. 初始化连接参数
        // 1. 创建客户端单例
        peerConnectionClient = PeerConnectionClient.getInstance();
        // 2. 创建连接参数
        PeerConnectionClient.PeerConnectionParameters parameters = PeerConnectionClient.PeerConnectionParameters.createDefault();
        // 3. 创建工厂类,初始化连接参数
        peerConnectionClient.createFactory(this,parameters,this);
    }

    private void connectRoom() {
        RoomConnectionParameters parameters = new RoomConnectionParameters("https://47.111.68.11","8888",false);
        WebRtcSocketClient socketClient = new WebRtcSocketClient(this);
        socketClient.connectRoom(parameters);
    }

    // 房间回调
    @Override
    public void onConnectedToRoom(SignalingParameters params) {

    }

    @Override
    public void onRemoteDescription(SessionDescription sdp) {

    }

    @Override
    public void onRemoteIceCandidate(IceCandidate candidate) {

    }

    @Override
    public void onRemoteIceCandidatesRemoved(IceCandidate[] candidates) {

    }

    @Override
    public void onChannelClose() {

    }

    @Override
    public void onChannelError(String description) {

    }

    // 连接回调
    @Override
    public void onLocalDescription(SessionDescription sdp) {

    }

    @Override
    public void onIceCandidate(IceCandidate candidate) {

    }

    @Override
    public void onIceCandidatesRemoved(IceCandidate[] candidates) {

    }

    @Override
    public void onIceConnected() {

    }

    @Override
    public void onIceDisconnected() {

    }

    @Override
    public void onPeerConnectionClosed() {

    }

    @Override
    public void onPeerConnectionStatsReady(StatsReport[] reports) {

    }

    @Override
    public void onPeerConnectionError(String description) {

    }
}