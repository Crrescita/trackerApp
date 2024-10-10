
package modelResponse.notification.parameter;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData implements Parcelable
{

    @SerializedName("fe_id")
    @Expose
    private String feId;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("id")
    @Expose
    private List<Integer> id = null;
    public final static Creator<NotificationData> CREATOR = new Creator<NotificationData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        public NotificationData[] newArray(int size) {
            return (new NotificationData[size]);
        }

    };

    protected NotificationData(Parcel in) {
        this.feId = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.id, (Integer.class.getClassLoader()));
    }

    public NotificationData() {
    }

    public String getFeId() {
        return feId;
    }

    public void setFeId(String feId) {
        this.feId = feId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(feId);
        dest.writeValue(vendorId);
        dest.writeValue(userId);
        dest.writeList(id);
    }

    public int describeContents() {
        return  0;
    }

}
