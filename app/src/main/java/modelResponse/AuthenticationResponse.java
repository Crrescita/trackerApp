package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

/**
 * Created by hp on 9/10/2018.
 */

public class AuthenticationResponse implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private Data data;

    protected AuthenticationResponse(Parcel in) {
        status = in.readString();
        code = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<AuthenticationResponse> CREATOR = new Creator<AuthenticationResponse>() {
        @Override
        public AuthenticationResponse createFromParcel(Parcel in) {
            return new AuthenticationResponse(in);
        }

        @Override
        public AuthenticationResponse[] newArray(int size) {
            return new AuthenticationResponse[size];
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
        @SerializedName("fe_mode")
        private String fe_mode;
        @SerializedName("vendor_id")
        private String vendor_id;
        @SerializedName("user_id")
        private String user_id;

        @SerializedName("fe_first_name")
        private  String fe_first_name;

        @SerializedName("fe_last_name")
        private String fe_last_name;

        @SerializedName("fe_number")
        private String fe_number;

        @SerializedName("app_version")
        private String app_version;

        @SerializedName("version_update")
        private String version_update;

        @SerializedName("auto_filled_ids")
        private Object auto_filled_ids;

        protected Data(Parcel in) {
            fe_id = in.readString();
            fe_mode = in.readString();
            vendor_id = in.readString();
            user_id = in.readString();
            fe_first_name = in.readString();
            fe_last_name = in.readString();
            fe_number = in.readString();
            app_version = in.readString();
            version_update = in.readString();
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

        public String getFe_mode() {
            return fe_mode;
        }

        public void setFe_mode(String fe_mode) {
            this.fe_mode = fe_mode;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getFe_number() {
            return fe_number;
        }

        public void setFe_number(String fe_number) {
            this.fe_number = fe_number;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getVersion_update() {
            return version_update;
        }

        public void setVersion_update(String version_update) {
            this.version_update = version_update;
        }

        public Object getAuto_filled_ids() {
            return auto_filled_ids;
        }

        public void setAuto_filled_ids(Object auto_filled_ids) {
            this.auto_filled_ids = auto_filled_ids;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(fe_id);
            parcel.writeString(fe_mode);
            parcel.writeString(vendor_id);
            parcel.writeString(user_id);
            parcel.writeString(fe_first_name);
            parcel.writeString(fe_last_name);
            parcel.writeString(fe_number);
            parcel.writeString(app_version);
            parcel.writeString(version_update);
        }
    }
}



