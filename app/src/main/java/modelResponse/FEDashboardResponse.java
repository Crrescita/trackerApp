package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kapil Rathee on 22/1/19.
 */

public class FEDashboardResponse implements Parcelable{

    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<Data> data;

    protected FEDashboardResponse(Parcel in) {
        status = in.readString();
        msg = in.readString();
        code = in.readString();
        data = (List<Data>) in.readValue((Data.class.getClassLoader()));
    }

    public static final Creator<FEDashboardResponse> CREATOR = new Creator<FEDashboardResponse>() {
        @Override
        public FEDashboardResponse createFromParcel(Parcel in) {
            return new FEDashboardResponse(in);
        }

        @Override
        public FEDashboardResponse[] newArray(int size) {
            return new FEDashboardResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(msg);
        parcel.writeString(code);
        parcel.writeTypedList(data);
    }

    public class Data implements Parcelable {
        @SerializedName("fe_id")
        private String fe_id;

        @SerializedName("fe_name")
        private String fe_name;

        @SerializedName("fe_login_status")
        private String fe_login_status;

        @SerializedName("total_wip")
        private String total_wip;

        protected Data(Parcel in) {
            fe_id = in.readString();
            fe_name = in.readString();
            fe_login_status = in.readString();
            total_wip = in.readString();
        }

        public  final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getFe_id() {
            return fe_id;
        }

        public void setFe_id(String fe_id) {
            this.fe_id = fe_id;
        }

        public String getFe_name() {
            return fe_name;
        }

        public void setFe_name(String fe_name) {
            this.fe_name = fe_name;
        }

        public String getFe_login_status() {
            return fe_login_status;
        }

        public void setFe_login_status(String fe_login_status) {
            this.fe_login_status = fe_login_status;
        }

        public String getTotal_wip() {
            return total_wip;
        }

        public void setTotal_wip(String total_wip) {
            this.total_wip = total_wip;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(fe_id);
            parcel.writeString(fe_name);
            parcel.writeString(fe_login_status);
            parcel.writeString(total_wip);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
