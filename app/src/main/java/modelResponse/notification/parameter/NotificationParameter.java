
package modelResponse.notification.parameter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationParameter implements Parcelable
{

    @SerializedName("data")
    @Expose
    private NotificationData data;
    public final static Creator<NotificationParameter> CREATOR = new Creator<NotificationParameter>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationParameter createFromParcel(Parcel in) {
            return new NotificationParameter(in);
        }

        public NotificationParameter[] newArray(int size) {
            return (new NotificationParameter[size]);
        }

    };

    protected NotificationParameter(Parcel in) {
        this.data = ((NotificationData) in.readValue((NotificationData.class.getClassLoader())));
    }

    public NotificationParameter() {
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
