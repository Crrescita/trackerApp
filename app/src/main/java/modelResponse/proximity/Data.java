
package modelResponse.proximity;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("proximity_status")
    @Expose
    public Integer proximityStatus;
    @SerializedName("distance")
    @Expose
    public Integer distance;
    @SerializedName("msg")
    @Expose
    public String msg;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    protected Data(android.os.Parcel in) {
        this.proximityStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.distance = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.msg = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(proximityStatus);
        dest.writeValue(distance);
        dest.writeValue(msg);
    }

    public int describeContents() {
        return  0;
    }

}
