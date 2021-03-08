package com.kevin.webrtcdemo.callback;

import com.kevin.webrtcdemo.bean.SignalingParameters;

/**
 * date:2021/3/7
 * user:Kevin
 * desc:房间参数回调
 */
public interface RoomParametersFetcherEvents {
    /**
     * Callback fired once the room's signaling parameters
     * SignalingParameters are extracted.
     */
    void onSignalingParametersReady(final SignalingParameters params);

    /**
     * Callback for room parameters extraction error.
     */
    void onSignalingParametersError(final String description);
}
