package be.ehb.filesdemo.model;

import java.io.Serializable;

public class OurObject implements Serializable {

    String data;

    public OurObject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
