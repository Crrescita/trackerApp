
package modelResponse.feassigned_check_response;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FEAssignedChecksResponse implements Parcelable
{

    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    @SerializedName("disabled_checks")
    private  List<DisabledChecks> disabledChecks;


    public final static Creator<FEAssignedChecksResponse> CREATOR = new Creator<FEAssignedChecksResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FEAssignedChecksResponse createFromParcel(Parcel in) {
            return new FEAssignedChecksResponse(in);
        }

        public FEAssignedChecksResponse[] newArray(int size) {
            return (new FEAssignedChecksResponse[size]);
        }

    }
    ;

    protected FEAssignedChecksResponse(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (Datum.class.getClassLoader()));
        in.readList(this.disabledChecks, (DisabledChecks.class.getClassLoader()));

        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.msg = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FEAssignedChecksResponse() {
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<DisabledChecks> getDisabledChecks() {
        return disabledChecks;
    }

    public void setDisabledChecks(List<DisabledChecks> disabledChecks) {
        this.disabledChecks = disabledChecks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
        dest.writeList(disabledChecks);
        dest.writeValue(code);
        dest.writeValue(msg);
    }

    public int describeContents() {
        return  0;
    }

}
