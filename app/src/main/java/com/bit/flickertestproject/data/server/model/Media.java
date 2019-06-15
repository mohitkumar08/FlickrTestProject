
package com.bit.flickertestproject.data.server.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "m"
})
public class Media {

    @JsonProperty("m")
    private String m;

    @JsonIgnore
    private String hqImage;

    @JsonIgnore
    private String cImage;


    @JsonProperty("m")
    public String getM() {
        return m;
    }

    @JsonProperty("m")
    public void setM(String m) {
        this.m = m;
        setHqImage(this.m);
        setcImage(this.m);

    }

    public String getHqImage() {
        return hqImage;
    }

    public void setHqImage(final String mImage) {
        int pos = mImage.lastIndexOf("_");
        StringBuilder temp = new StringBuilder();
        temp.append(mImage.substring(0, pos));
        temp.append("_b");
        temp.append(mImage.substring(pos + 2));
        this.hqImage = temp.toString();
    }

    public String getcImage() {
        return cImage;
    }

    public void setcImage(final String mImage) {
        int pos = mImage.lastIndexOf("_");
        StringBuilder temp = new StringBuilder();
        temp.append(mImage.substring(0, pos));
        temp.append(mImage.substring(pos + 2));
        this.cImage = temp.toString();
    }
}
