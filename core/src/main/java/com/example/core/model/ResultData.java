package com.example.core.model;

import lombok.Data;

@Data
public class ResultData<T> {
    private Boolean success = true;
    private String message = "";

    private T data;

    public ResultData() {
    }

    public ResultData(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ResultDataBuilder builder() {
        return new ResultDataBuilder();
    }

    public static class ResultDataBuilder<T> {

        private Boolean success = true;
        private String message = "";

        private T data;

        public ResultDataBuilder() {

        }

        public ResultDataBuilder success(Boolean success) {
            this.success = success;
            return this;
        }

        public ResultDataBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResultDataBuilder data(T data) {
            this.data = data;
            return this;
        }

        public ResultData build() {
            return new ResultData<>(success, message, data);
        }

        public String toString() {
            return "ResultData.ResultDataBuilder(success=" + this.success + ", message=" + this.message + ", data=" + this.data + ")";
        }
    }
}
