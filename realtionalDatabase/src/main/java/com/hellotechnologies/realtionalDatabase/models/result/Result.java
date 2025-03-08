package com.hellotechnologies.realtionalDatabase.models.result;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Result<T> {
    T data;
    String errMsg;
    ResultStatus status;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder().data(data).status(ResultStatus.SUCCESS).build();
    }

    public static <T> Result<T> failure(String errMsg) {
        return Result.<T>builder().errMsg(errMsg).status(ResultStatus.FAILURE).build();
    }

    public boolean isSuccess() {
        return this.status == ResultStatus.SUCCESS;
    }

    public boolean isFailure() {
        return this.status == ResultStatus.FAILURE;
    }
}