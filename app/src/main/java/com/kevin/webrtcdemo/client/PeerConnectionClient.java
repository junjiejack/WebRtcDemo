package com.kevin.webrtcdemo.client;

import android.content.Context;

import com.kevin.webrtcdemo.callback.PeerConnectionEvents;

import org.webrtc.PeerConnectionFactory;
import org.webrtc.voiceengine.WebRtcAudioUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * date:2021/3/7
 * user:Kevin
 * desc:客户端连接类
 */
public class PeerConnectionClient {

    // 客户端单例
    private static PeerConnectionClient peerConnectionClient;

    // 线程池
    private ScheduledExecutorService executorService;

    // peerConnectionFactory参数
    private PeerConnectionFactory.Options options = null;
    private PeerConnectionFactory peerConnectionFactory;

    private PeerConnectionClient() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static PeerConnectionClient getInstance() {
       if (null == peerConnectionClient) {
           synchronized (PeerConnectionClient.class) {
               if (null == peerConnectionClient) {
                   return new PeerConnectionClient();
               }
           }
       }
       return peerConnectionClient;
    }

    // 创建工厂(包含连接参数)
    public void createFactory(final Context context, PeerConnectionParameters parameters, PeerConnectionEvents events) {
        // 连接属于耗时操作
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // 1. 创建通道--> Native方法
                PeerConnectionFactory.initializeInternalTracer();
                // 2. 测试专用--> Native方法 TODO 待理解
                PeerConnectionFactory.initializeFieldTrials("");
                // 3. 自动取消(一方挂断情况)
                WebRtcAudioUtils.setWebRtcBasedAcousticEchoCanceler(true);
                // TODO 待理解
                WebRtcAudioUtils.setWebRtcBasedAutomaticGainControl(true);
                // 4. 消噪
                WebRtcAudioUtils.setWebRtcBasedNoiseSuppressor(true);
                // 5. 初始化android
                PeerConnectionFactory.initializeAndroidGlobals(context,true,true,true);
                // 6. 创建factory
                peerConnectionFactory = new PeerConnectionFactory(options);
            }
        });
    }


    // 连接参数
    public static class PeerConnectionParameters {
        public final boolean videoCallEnabled;
        // 回拨的意思
        public final boolean loopback;
        public final boolean tracing;
        public final int videoWidth;
        public final int videoHeight;
        // 帧率
        public final int videoFps;
        // 比特率60kb
        public final int videoMaxBitrate;
        public final String videoCodec;//视频编码
        public final boolean videoCodecHwAcceleration;//硬编码
        public final boolean videoFlexfecEnabled;
        public final int audioStartBitrate;
        public final String audioCodec;
        public final boolean noAudioProcessing;
        public final boolean aecDump;
        public final boolean enableLevelControl;
        private final DataChannelParameters dataChannelParameters;

        public static PeerConnectionParameters createDefault() {
            return new PeerConnectionParameters(true, false,
                    false, 0, 0, 0,
                    0, "VP8",
                    true,
                    false,
                    0, "OPUS",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false);
        }

        public PeerConnectionParameters(boolean videoCallEnabled, boolean loopback, boolean tracing,
                                        int videoWidth, int videoHeight, int videoFps, int videoMaxBitrate, String videoCodec,
                                        boolean videoCodecHwAcceleration, boolean videoFlexfecEnabled, int audioStartBitrate,
                                        String audioCodec, boolean noAudioProcessing, boolean aecDump, boolean useOpenSLES,
                                        boolean disableBuiltInAEC, boolean disableBuiltInAGC, boolean disableBuiltInNS,
                                        boolean enableLevelControl) {
            this(videoCallEnabled, loopback, tracing, videoWidth, videoHeight, videoFps, videoMaxBitrate,
                    videoCodec, videoCodecHwAcceleration, videoFlexfecEnabled, audioStartBitrate, audioCodec,
                    noAudioProcessing, aecDump, useOpenSLES, disableBuiltInAEC, disableBuiltInAGC,
                    disableBuiltInNS, enableLevelControl, null);
        }

        public PeerConnectionParameters(boolean videoCallEnabled, boolean loopback, boolean tracing,
                                        int videoWidth, int videoHeight, int videoFps, int videoMaxBitrate, String videoCodec,
                                        boolean videoCodecHwAcceleration, boolean videoFlexfecEnabled, int audioStartBitrate,
                                        String audioCodec, boolean noAudioProcessing, boolean aecDump, boolean useOpenSLES,
                                        boolean disableBuiltInAEC, boolean disableBuiltInAGC, boolean disableBuiltInNS,
                                        boolean enableLevelControl, DataChannelParameters dataChannelParameters) {
            this.videoCallEnabled = videoCallEnabled;
            this.loopback = loopback;
            this.tracing = tracing;
            this.videoWidth = videoWidth;
            this.videoHeight = videoHeight;
            this.videoFps = videoFps;
            this.videoMaxBitrate = videoMaxBitrate;
            this.videoCodec = videoCodec;
            this.videoFlexfecEnabled = videoFlexfecEnabled;
            this.videoCodecHwAcceleration = videoCodecHwAcceleration;
            this.audioStartBitrate = audioStartBitrate;
            this.audioCodec = audioCodec;
            this.noAudioProcessing = noAudioProcessing;
            this.aecDump = aecDump;
            this.enableLevelControl = enableLevelControl;
            this.dataChannelParameters = dataChannelParameters;

        }
    }

    public static class DataChannelParameters {
        public final boolean ordered;
        public final int maxRetransmitTimeMs;
        public final int maxRetransmits;
        public final String protocol;
        public final boolean negotiated;
        public final int id;

        public DataChannelParameters(boolean ordered, int maxRetransmitTimeMs, int maxRetransmits,
                                     String protocol, boolean negotiated, int id) {
            this.ordered = ordered;
            this.maxRetransmitTimeMs = maxRetransmitTimeMs;
            this.maxRetransmits = maxRetransmits;
            this.protocol = protocol;
            this.negotiated = negotiated;
            this.id = id;
        }
    }
}
