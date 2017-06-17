package ceramics.com.ceramics.model;

import java.io.Serializable;

/**
 * Created by vikrantg on 17-06-2017.
 */

public class FilterCategory implements Serializable {

    private int filterCategoryId;
    private String name;
    private String createdOn;

    public int getFilterCategoryId() {
        return filterCategoryId;
    }

    public void setFilterCategoryId(int filterCategoryId) {
        this.filterCategoryId = filterCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
