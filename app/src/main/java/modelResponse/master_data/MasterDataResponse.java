
package modelResponse.master_data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDataResponse implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("code")
    @Expose
    private Integer code;
    public final static Creator<MasterDataResponse> CREATOR = new Creator<MasterDataResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MasterDataResponse createFromParcel(Parcel in) {
            return new MasterDataResponse(in);
        }

        public MasterDataResponse[] newArray(int size) {
            return (new MasterDataResponse[size]);
        }

    }
    ;

    protected MasterDataResponse(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public MasterDataResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
        dest.writeValue(code);
    }

    public int describeContents() {
        return  0;
    }

}
