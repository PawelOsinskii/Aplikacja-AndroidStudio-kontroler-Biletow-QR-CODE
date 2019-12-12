package com.example.appka;

public class BufferRecord {
    private int id;
    private String code;
    private int type;
    private String operationTime;


    public BufferRecord(int id, String code, int type, String operationTime) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.operationTime = operationTime;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getType() {
        return type;
    }

    public String getOperationTime() {
        return operationTime;
    }
}
