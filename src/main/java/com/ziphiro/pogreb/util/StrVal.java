package com.ziphiro.pogreb.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StrVal {

    THREAD_POOL_IS_FULL("Task rejected, thread pool is full and queue is also full"),
    SLASH("/"),
    UPLOAD_ERROR("upload error"),
    UPLOAD_START("upload start"),
    UPLOAD_SUCCESS("upload success"),
    STORAGE_DIR("/home/ziphiro/myBotStorage/"),
    LOGIN_FAILED("login failed "),
    LOGIN_SUCCESS("login success "),
    REGISTERED(" registered"),
    FAILED_REGISTRATION(" failed registration"),
    PLUS("+"),
    STARTING_DOWNLOAD(" downloading file: "),
    COMPLETE_DOWNLOAD(" downloaded file: "),
    ATTACHMENT_FILENAME("attachment; filename=");

    private final String value;
}
