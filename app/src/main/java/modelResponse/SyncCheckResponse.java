package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kapil Rathee on 5/10/18.
 */

public class SyncCheckResponse implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private  String msg;
    @SerializedName("Vendor_Request_ID")
    private String vendor_request_id;
    @SerializedName("Case_Check_ID")
    private String case_check_id;
    @SerializedName("source_id")
    private  String source_id;
    @SerializedName("Row_ID")
    private String row_id;

    protected SyncCheckResponse(Parcel in) {
        status = in.readString();
        code = in.readString();
        msg = in.readString();
        vendor_request_id = in.readString();
        case_check_id = in.readString();
        source_id = in.readString();
        row_id = in.readString();
    }

    public static final Creator<SyncCheckResponse> CREATOR = new Creator<SyncCheckResponse>() {
        @Override
        public SyncCheckResponse createFromParcel(Parcel in) {
            return new SyncCheckResponse(in);
        }

        @Override
        public SyncCheckResponse[] newArray(int size) {
            return new SyncCheckResponse[size];
        }
    };

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

    public String getVendor_request_id() {
        return vendor_request_id;
    }

    public void setVendor_request_id(String vendor_request_id) {
        this.vendor_request_id = vendor_request_id;
    }

    public String getCase_check_id() {
        return case_check_id;
    }

    public void setCase_check_id(String case_check_id) {
        this.case_check_id = case_check_id;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
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
        parcel.writeString(vendor_request_id);
        parcel.writeString(case_check_id);
        parcel.writeString(source_id);
        parcel.writeString(row_id);
    }
}
