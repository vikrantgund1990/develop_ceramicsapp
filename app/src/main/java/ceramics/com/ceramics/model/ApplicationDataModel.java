package ceramics.com.ceramics.model;

import java.io.Serializable;

/**
 * Created by vikrantg on 24-03-2017.
 */

public class ApplicationDataModel implements Serializable {
    private boolean isReferCodeShow = true;
    private UserLocationData userLocationData;

    public boolean isReferCodeShow() {
        return isReferCodeShow;
    }

    public void setReferCodeShow(boolean referCodeShow) {
        isReferCodeShow = referCodeShow;
    }

    public UserLocationData getUserLocationData() {
        return userLocationData;
    }

    public void setUserLocationData(UserLocationData userLocationData) {
        this.userLocationData = userLocationData;
    }
}
