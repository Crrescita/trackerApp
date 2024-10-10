package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kapil Rathee on 6/12/18.
 */

public class FEDetailsResponse implements Parcelable {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private Data data;

    protected FEDetailsResponse(Parcel in) {
        status = in.readString();
        code = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<FEDetailsResponse> CREATOR = new Creator<FEDetailsResponse>() {
        @Override
        public FEDetailsResponse createFromParcel(Parcel in) {
            return new FEDetailsResponse(in);
        }

        @Override
        public FEDetailsResponse[] newArray(int size) {
            return new FEDetailsResponse[size];
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(code);
        parcel.writeParcelable(data, i);
    }

    public class Data implements Parcelable {

        @SerializedName("fe_id")
        private String fe_id;

        @SerializedName("fe_first_name")
        private String fe_first_name;

        @SerializedName("fe_last_name")
        private String fe_last_name;

        @SerializedName("emp_id")
        private String emp_id;

        @SerializedName("photo")
        private String photo;

        @SerializedName("vendor_id")
        private String vendor_id;

        @SerializedName("vendor_name")
        private String vendor_name;

        @SerializedName("vendor_address")
        private String vendor_address;

        protected Data(Parcel in) {
            fe_id = in.readString();
            fe_first_name = in.readString();
            fe_last_name = in.readString();
            emp_id = in.readString();
            photo = in.readString();
            vendor_id = in.readString();
            vendor_name = in.readString();
            vendor_address = in.readString();
        }

        public final Creator<Data> CREATOR = new Creator<Data>() {
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

        public String getFe_first_name() {
            return fe_first_name;
        }

        public void setFe_first_name(String fe_first_name) {
            this.fe_first_name = fe_first_name;
        }

        public String getFe_last_name() {
            return fe_last_name;
        }

        public void setFe_last_name(String fe_last_name) {
            this.fe_last_name = fe_last_name;
        }

        public String getEmp_id() {
            return emp_id;
        }

        public void setEmp_id(String emp_id) {
            this.emp_id = emp_id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVendor_address() {
            return vendor_address;
        }

        public void setVendor_address(String vendor_address) {
            this.vendor_address = vendor_address;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(fe_id);
            parcel.writeString(fe_first_name);
            parcel.writeString(fe_last_name);
            parcel.writeString(emp_id);
            parcel.writeString(photo);
            parcel.writeString(vendor_id);
            parcel.writeString(vendor_name);
            parcel.writeString(vendor_address);
        }
    }
}
