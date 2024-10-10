
package modelResponse.proximity;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProximityStatusResponse implements Parcelable
{

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("code")
    @Expose
    public Integer code;
    public final static Creator<ProximityStatusResponse> CREATOR = new Creator<ProximityStatusResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProximityStatusResponse createFromParcel(android.os.Parcel in) {
            return new ProximityStatusResponse(in);
        }

        public ProximityStatusResponse[] newArray(int size) {
            return (new ProximityStatusResponse[size]);
        }

    }
    ;

    protected ProximityStatusResponse(android.os.Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ProximityStatusResponse() {
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
        dest.writeValue(code);
    }

    public int describeContents() {
        return  0;
    }

}
