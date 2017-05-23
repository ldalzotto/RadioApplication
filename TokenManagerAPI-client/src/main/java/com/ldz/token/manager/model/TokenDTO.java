package com.ldz.token.manager.model;

import java.util.Map;

/**
 * Created by loicd on 23/05/2017.
 */
public class TokenDTO {

    private String id;
    private String ts;
    private Map<String, String> data;

    @Override
    public String toString() {
        return "TokenDTO{" +
                "id='" + id + '\'' +
                ", ts='" + ts + '\'' +
                ", data=" + data +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
