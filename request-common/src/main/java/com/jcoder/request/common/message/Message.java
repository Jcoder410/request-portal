package com.jcoder.request.common.message;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Message {

    public static final Message.Type DEFAULT_TYPE;
    private String code;
    private String desc;
    private String type;

    public Message() {
    }

    public Message(String code, String desc) {
        this.code = code;
        this.desc = desc;
        String[] arr = StringUtils.split(code, ".");
        if (arr != null && arr.length > 2) {
            Message.Type t = Message.Type.match(arr[1]);
            if (t != null) {
                this.type = t.code;
            }
        }

        if (StringUtils.isBlank(this.type)) {
            this.type = DEFAULT_TYPE.code();
        }

    }

    public Message(String code, String desc, Message.Type type) {
        this.code = code;
        this.desc = desc;
        this.type = type.code();
    }

    public String code() {
        return this.code;
    }

    public Message setCode(String code) {
        this.code = code;
        return this;
    }

    public String desc() {
        return this.desc;
    }

    public Message setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String type() {
        return this.type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getType() {
        return this.type;
    }

    static {
        DEFAULT_TYPE = Message.Type.WARN;
    }

    public static enum Type {
        INFO("info"),
        WARN("warn"),
        ERROR("error");

        private String code;
        static final Map<String, Type> MAP = new HashMap();

        private Type(String code) {
            this.code = code;
        }

        public String code() {
            return this.code;
        }

        public static Message.Type match(String code) {
            return (Message.Type)MAP.get(StringUtils.lowerCase(code));
        }

        static {
            Message.Type[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Message.Type value = var0[var2];
                MAP.put(value.code, value);
            }

        }
    }

}
