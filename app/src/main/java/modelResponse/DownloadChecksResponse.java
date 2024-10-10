package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kapil Rathee on 11/9/18.
 */

public class DownloadChecksResponse implements Parcelable{
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<Data> data;

    protected DownloadChecksResponse(Parcel in) {
        status = in.readString();
        code = in.readString();
        this.data = (List<Data>) in.readValue((Data.class.getClassLoader()));
    }

    public static final Creator<DownloadChecksResponse> CREATOR = new Creator<DownloadChecksResponse>() {
        @Override
        public DownloadChecksResponse createFromParcel(Parcel in) {
            return new DownloadChecksResponse(in);
        }

        @Override
        public DownloadChecksResponse[] newArray(int size) {
            return new DownloadChecksResponse[size];
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
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
        parcel.writeTypedList(data);
    }

    public class Data implements Parcelable {
        @SerializedName("CHECK_ID")
        private String check_id;
        @SerializedName("CHECK_NAME")
        private String check_name;

        protected Data(Parcel in) {
            check_id = in.readString();
            check_name = in.readString();
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

        public String getCheck_id() {
            return check_id;
        }

        public void setCheck_id(String check_id) {
            this.check_id = check_id;
        }

        public String getCheck_name() {
            return check_name;
        }

        public void setCheck_name(String check_name) {
            this.check_name = check_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(check_id);
            parcel.writeString(check_name);
        }
    }

}

