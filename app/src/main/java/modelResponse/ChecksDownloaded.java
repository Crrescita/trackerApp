package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

/**
 * Created by Kapil Rathee on 11/10/18.
 */

public class ChecksDownloaded implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private  String msg;

    protected ChecksDownloaded(Parcel in) {
        status = in.readString();
        code = in.readString();
        msg = in.readString();
    }

    public static final Creator<ChecksDownloaded> CREATOR = new Creator<ChecksDownloaded>() {
        @Override
        public ChecksDownloaded createFromParcel(Parcel in) {
            return new ChecksDownloaded(in);
        }

        @Override
        public ChecksDownloaded[] newArray(int size) {
            return new ChecksDownloaded[size];
        }
    };

    /*@SerializedName("Vendor_Request_IDS")
        private Array requestIds;
    */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(code);
        parcel.writeString(msg);
    }

    /*public Array getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(Array requestIds) {
        this.requestIds = requestIds;
    }*/
}
