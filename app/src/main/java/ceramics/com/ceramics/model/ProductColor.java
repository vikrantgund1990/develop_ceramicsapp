package ceramics.com.ceramics.model;

import java.io.Serializable;

/**
 * Created by vikrantg on 18-06-2017.
 */

public class ProductColor implements Serializable{

    private int colourId;
    private String colourName;

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }
}
