package ceramics.com.ceramics.model;

import java.io.Serializable;

/**
 * Created by vikrantg on 17-06-2017.
 */

public class Filter implements Serializable {

    private int filterCategoryId;
    private int filterId;
    private String filterName;
    private String filterValue;
    private FilterCategory filterCategory;

    public int getFilterCategoryId() {
        return filterCategoryId;
    }

    public void setFilterCategoryId(int filterCategoryId) {
        this.filterCategoryId = filterCategoryId;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public FilterCategory getFilterCategory() {
        return filterCategory;
    }

    public void setFilterCategory(FilterCategory filterCategory) {
        this.filterCategory = filterCategory;
    }
}
