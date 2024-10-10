package modelResponse.feassigned_check_response;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisabledChecks implements Parcelable {
        @SerializedName("VENDOR_REQUEST_ID")
        private String vendor_request_ID;
        @SerializedName("CASE_CHECK_ID")
        private String case_check_id;
        @SerializedName("FE_ID")
        private String fe_id;
        @SerializedName("SOURCE_ID")
        private String source_id;

        protected DisabledChecks(Parcel in) {
            vendor_request_ID = in.readString();
            case_check_id = in.readString();
            fe_id = in.readString();
            source_id = in.readString();
        }

        public  final Creator<DisabledChecks> CREATOR = new Creator<DisabledChecks>() {
            @Override
            public DisabledChecks createFromParcel(Parcel in) {
                return new DisabledChecks(in);
            }

            @Override
            public DisabledChecks[] newArray(int size) {
                return new DisabledChecks[size];
            }
        };

        public String getVendor_request_ID() {
            return vendor_request_ID;
        }

        public void setVendor_request_ID(String vendor_request_ID) {
            this.vendor_request_ID = vendor_request_ID;
        }

        public String getCase_check_id() {
            return case_check_id;
        }

        public void setCase_check_id(String case_check_id) {
            this.case_check_id = case_check_id;
        }

        public String getFe_id() {
            return fe_id;
        }

        public void setFe_id(String fe_id) {
            this.fe_id = fe_id;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(vendor_request_ID);
            parcel.writeString(case_check_id);
            parcel.writeString(fe_id);
            parcel.writeString(source_id);
        }
    }