package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kapil Rathee on 13/9/18.
 */

public class ModelError implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("statusCode")
    private String statusCode;
    @SerializedName("message")
    private  String message;

    protected ModelError(Parcel in) {
        status = in.readString();
        statusCode = in.readString();
        message = in.readString();
    }

    public static final Creator<ModelError> CREATOR = new Creator<ModelError>() {
        @Override
        public ModelError createFromParcel(Parcel in) {
            return new ModelError(in);
        }

        @Override
        public ModelError[] newArray(int size) {
            return new ModelError[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return statusCode;
    }

    public void setCode(String code) {
        this.statusCode = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(statusCode);
        parcel.writeString(message);
    }
}
