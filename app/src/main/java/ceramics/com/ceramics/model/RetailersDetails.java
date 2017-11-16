package ceramics.com.ceramics.model;

import java.io.Serializable;

/**
 * Created by vikrantg on 16-11-2017.
 */

public class RetailersDetails implements Serializable {

    private int retailerId;
    private String retailerNumber;
    private String retailerName;
    private String address01;
    private String address02;
    private String address03;
    private String address04;
    private int cityId;
    private String landLine1;
    private String landLine2;
    private String mobile1;
    private String mobile2;
    private String email;
    private String website;
    private String timeOpen;
    private String timeClose;
    private String dayClose;
    private String createdBy;
    private String createdOn;
    private String dealer;
    private String city;
    private String dealerId;

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public String getRetailerNumber() {
        return retailerNumber;
    }

    public void setRetailerNumber(String retailerNumber) {
        this.retailerNumber = retailerNumber;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getAddress01() {
        return address01;
    }

    public void setAddress01(String address01) {
        this.address01 = address01;
    }

    public String getAddress02() {
        return address02;
    }

    public void setAddress02(String address02) {
        this.address02 = address02;
    }

    public String getAddress03() {
        return address03;
    }

    public void setAddress03(String address03) {
        this.address03 = address03;
    }

    public String getAddress04() {
        return address04;
    }

    public void setAddress04(String address04) {
        this.address04 = address04;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getLandLine1() {
        return landLine1;
    }

    public void setLandLine1(String landLine1) {
        this.landLine1 = landLine1;
    }

    public String getLandLine2() {
        return landLine2;
    }

    public void setLandLine2(String landLine2) {
        this.landLine2 = landLine2;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public String getDayClose() {
        return dayClose;
    }

    public void setDayClose(String dayClose) {
        this.dayClose = dayClose;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}
