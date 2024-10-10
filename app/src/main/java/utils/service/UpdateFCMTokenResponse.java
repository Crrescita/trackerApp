package utils.service;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateFCMTokenResponse implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Object> data;
    @SerializedName("code")
    @Expose
    private Integer code;
    public final static Creator<UpdateFCMTokenResponse> CREATOR = new Creator<UpdateFCMTokenResponse>() {


        public UpdateFCMTokenResponse createFromParcel(android.os.Parcel in) {
            return new UpdateFCMTokenResponse(in);
        }

        public UpdateFCMTokenResponse[] newArray(int size) {
            return (new UpdateFCMTokenResponse[size]);
        }

    };


    @SuppressWarnings({
            "unchecked"
    })
    protected UpdateFCMTokenResponse(android.os.Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (java.lang.Object.class.getClassLoader()));
        this.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public UpdateFCMTokenResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
        dest.writeValue(code);
    }

    public int describeContents() {
        return 0;
    }

}