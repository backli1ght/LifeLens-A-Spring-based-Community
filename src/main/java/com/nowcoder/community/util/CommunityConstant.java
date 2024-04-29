package com.nowcoder.community.util;

public interface CommunityConstant {
    /**
     * Activation Success
     */
    int ACTIVATION_SUCCESS = 0;

    // repeat
    int ACTIVATION_REPEAT = 1;

    // fail
    int ACTIVATION_FAILURE = 2;

    // default expiration time
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // remember me expiration time
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * entityType: discussPost
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * entityType: comment
     */
    int ENTITY_TYPE_COMMENT = 2;
}
