package com.kevin.webrtcdemo.callback;

import com.kevin.webrtcdemo.bean.SignalingParameters;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

/**
 * date:2021/3/7
 * user:Kevin
 * desc:信令回调
 */
public interface SignalingEvents {

    /**
     * Callback fired once the room's signaling parameters
     * SignalingParameters are extracted.
     */
    void onConnectedToRoom(SignalingParameters params);

    /**
     * Callback fired once remote SDP is received.
     */
    void onRemoteDescription(final SessionDescription sdp);

    /**
     * Callback fired once remote Ice candidate is received.
     */
    void onRemoteIceCandidate(final IceCandidate candidate);

    /**
     * Callback fired once remote Ice candidate removals are received.
     */
    void onRemoteIceCandidatesRemoved(final IceCandidate[] candidates);

    /**
     * Callback fired once channel is closed.
     */
    void onChannelClose();

    /**
     * Callback fired once channel error happened.
     */
    void onChannelError(final String description);
}
