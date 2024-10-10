package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hp on 12/06/2019.
 */

public class ResponseNew  implements Parcelable {
    @SerializedName("responseData")
    private String response;

    protected ResponseNew(Parcel in) {
        response = in.readString();
    }

    public static final Creator<ResponseNew> CREATOR = new Creator<ResponseNew>() {
        @Override
        public ResponseNew createFromParcel(Parcel in) {
            return new ResponseNew(in);
        }

        @Override
        public ResponseNew[] newArray(int size) {
            return new ResponseNew[size];
        }
    };

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(response);
    }
}
