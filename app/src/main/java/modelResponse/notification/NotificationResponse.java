
package modelResponse.notification;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<NotificationList> data = null;
    @SerializedName("code")
    @Expose
    private Integer code;

    protected NotificationResponse(Parcel in) {
        status = in.readString();
        data = in.createTypedArrayList(NotificationList.CREATOR);
        if (in.readByte() == 0) {
            code = null;
        } else {
            code = in.readInt();
        }
    }

    public static final Creator<NotificationResponse> CREATOR = new Creator<NotificationResponse>() {
        @Override
        public NotificationResponse createFromParcel(Parcel in) {
            return new NotificationResponse(in);
        }

        @Override
        public NotificationResponse[] newArray(int size) {
            return new NotificationResponse[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NotificationList> getData() {
        return data;
    }

    public void setData(List<NotificationList> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeTypedList(data);
        if (code == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(code);
        }
    }
}
