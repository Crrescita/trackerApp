package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kapil Rathee on 13/9/18.
 */

public class CheckFieldsResponse  implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<Data> data;

    @SerializedName("check_dispositions")
    private List<CheckDispositions> dispositions;

    @SerializedName("photo_settings")
    private List<PhotoSetting> photoSettings = null;

    protected CheckFieldsResponse(Parcel in) {
        status = in.readString();
        code = in.readString();
        this.data = (List<Data>) in.readValue((Data.class.getClassLoader()));
        this.dispositions = (List<CheckDispositions>) in.readValue((Data.class.getClassLoader()));
        this.photoSettings = (List<PhotoSetting>) in.readValue((Data.class.getClassLoader()));
    }

    public static final Creator<CheckFieldsResponse> CREATOR = new Creator<CheckFieldsResponse>() {
        @Override
        public CheckFieldsResponse createFromParcel(Parcel in) {
            return new CheckFieldsResponse(in);
        }

        @Override
        public CheckFieldsResponse[] newArray(int size) {
            return new CheckFieldsResponse[size];
        }
    };

    public List<PhotoSetting> getPhotoSettings() {
        return photoSettings;
    }

    public void setPhotoSettings(List<PhotoSetting> photoSettings) {
        this.photoSettings = photoSettings;
    }

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

    public List<CheckDispositions> getDispositions() {
        return dispositions;
    }

    public void setDispositions(List<CheckDispositions> dispositions) {
        this.dispositions = dispositions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(code);
        parcel.writeValue(data);
        parcel.writeValue(dispositions);
        parcel.writeValue(photoSettings);
    }

    public class Data implements Parcelable{
        @SerializedName("CHECK_ID")
        private String check_id;
        @SerializedName("FIELD_ID")
        private String field_id;
        @SerializedName("FIELD_NAME")
        private String field_name;
        @SerializedName("IS_MANDATORY")
        private String is_mandatory;
        @SerializedName("FIELD_INPUT_TYPE")
        private String field_input_type;
        @SerializedName("FIELD_MASTER")
        private String field_master;

        protected Data(Parcel in) {
            check_id = in.readString();
            field_id = in.readString();
            field_name = in.readString();
            is_mandatory = in.readString();
            field_input_type = in.readString();
            field_master = in.readString();
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

        public String getField_id() {
            return field_id;
        }

        public void setField_id(String field_id) {
            this.field_id = field_id;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public String getIs_mandatory() {
            return is_mandatory;
        }

        public void setIs_mandatory(String is_mandatory) {
            this.is_mandatory = is_mandatory;
        }

        public String getField_input_type() {
            return field_input_type;
        }

        public void setField_input_type(String field_input_type) {
            this.field_input_type = field_input_type;
        }

        public String getField_master() {
            return field_master;
        }

        public void setField_master(String field_master) {
            this.field_master = field_master;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(check_id);
            parcel.writeString(field_id);
            parcel.writeString(field_name);
            parcel.writeString(is_mandatory);
            parcel.writeString(field_input_type);
            parcel.writeString(field_master);
        }
    }

    public class CheckDispositions implements Parcelable{

        @SerializedName("CHECK_ID")
        private int CHECK_ID;

        @SerializedName("DISPOSITION_ID")
        private int DISPOSITION_ID;

        @SerializedName("DISPOSITION")
        private String DISPOSITION_NAME;

        protected CheckDispositions(Parcel in) {
            CHECK_ID = in.readInt();
            DISPOSITION_ID = in.readInt();
            DISPOSITION_NAME = in.readString();
        }

        public final Creator<CheckDispositions> CREATOR = new Creator<CheckDispositions>() {
            @Override
            public CheckDispositions createFromParcel(Parcel in) {
                return new CheckDispositions(in);
            }

            @Override
            public CheckDispositions[] newArray(int size) {
                return new CheckDispositions[size];
            }
        };

        public int getCHECK_ID() {
            return CHECK_ID;
        }

        public void setCHECK_ID(int CHECK_ID) {
            this.CHECK_ID = CHECK_ID;
        }

        public int getDISPOSITION_ID() {
            return DISPOSITION_ID;
        }

        public void setDISPOSITION_ID(int DISPOSITION_ID) {
            this.DISPOSITION_ID = DISPOSITION_ID;
        }

        public String getDISPOSITION_NAME() {
            return DISPOSITION_NAME;
        }

        public void setDISPOSITION_NAME(String DISPOSITION_NAME) {
            this.DISPOSITION_NAME = DISPOSITION_NAME;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(CHECK_ID);
            parcel.writeInt(DISPOSITION_ID);
            parcel.writeString(DISPOSITION_NAME);
        }
    }

    public class PhotoSetting implements Parcelable{

        @SerializedName("CLIENT_ID")
        private String cLIENTID;
        @SerializedName("CHECK_ID")
        private String cHECKID;
        @SerializedName("PHOTO_FLAG")
        private Integer pHOTOFLAG;
        @SerializedName("PHOTO_DETAILS")
        private ArrayList<PHOTODETAIL> pHOTODETAILS = null;

        protected PhotoSetting(Parcel in) {
            cLIENTID = in.readString();
            cHECKID = in.readString();
            if (in.readByte() == 0) {
                pHOTOFLAG = null;
            } else {
                pHOTOFLAG = in.readInt();
            }
            this.pHOTODETAILS = (ArrayList<PHOTODETAIL>) in.readValue((PHOTODETAIL.class.getClassLoader()));
        }

        public final Creator<PhotoSetting> CREATOR = new Creator<PhotoSetting>() {
            @Override
            public PhotoSetting createFromParcel(Parcel in) {
                return new PhotoSetting(in);
            }

            @Override
            public PhotoSetting[] newArray(int size) {
                return new PhotoSetting[size];
            }
        };

        public String getCLIENTID() {
            return cLIENTID;
        }

        public void setCLIENTID(String cLIENTID) {
            this.cLIENTID = cLIENTID;
        }

        public String getCHECKID() {
            return cHECKID;
        }

        public void setCHECKID(String cHECKID) {
            this.cHECKID = cHECKID;
        }

        public Integer getPHOTOFLAG() {
            return pHOTOFLAG;
        }

        public void setPHOTOFLAG(Integer pHOTOFLAG) {
            this.pHOTOFLAG = pHOTOFLAG;
        }

        public ArrayList<PHOTODETAIL> getPHOTODETAILS() {
            return pHOTODETAILS;
        }

        public void setPHOTODETAILS(ArrayList<PHOTODETAIL> pHOTODETAILS) {
            this.pHOTODETAILS = pHOTODETAILS;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(cLIENTID);
            parcel.writeString(cHECKID);
            if (pHOTOFLAG == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(pHOTOFLAG);
            }
            parcel.writeTypedList(pHOTODETAILS);
        }
    }

    public class PHOTODETAIL implements Parcelable {

        @SerializedName("DOCUMENT_ID")
        private Integer dOCUMENTID;
        @SerializedName("DOCUMENT_NAME")
        private String dOCUMENTNAME;
        @SerializedName("IS_MANDATORY")
        private Integer iSMANDATORY;

        protected PHOTODETAIL(Parcel in) {
            if (in.readByte() == 0) {
                dOCUMENTID = null;
            } else {
                dOCUMENTID = in.readInt();
            }
            dOCUMENTNAME = in.readString();
            if (in.readByte() == 0) {
                iSMANDATORY = null;
            } else {
                iSMANDATORY = in.readInt();
            }
        }

        public  final Creator<PHOTODETAIL> CREATOR = new Creator<PHOTODETAIL>() {
            @Override
            public PHOTODETAIL createFromParcel(Parcel in) {
                return new PHOTODETAIL(in);
            }

            @Override
            public PHOTODETAIL[] newArray(int size) {
                return new PHOTODETAIL[size];
            }
        };

        public Integer getDOCUMENTID() {
            return dOCUMENTID;
        }

        public void setDOCUMENTID(Integer dOCUMENTID) {
            this.dOCUMENTID = dOCUMENTID;
        }

        public String getDOCUMENTNAME() {
            return dOCUMENTNAME;
        }

        public void setDOCUMENTNAME(String dOCUMENTNAME) {
            this.dOCUMENTNAME = dOCUMENTNAME;
        }

        public Integer getISMANDATORY() {
            return iSMANDATORY;
        }

        public void setISMANDATORY(Integer iSMANDATORY) {
            this.iSMANDATORY = iSMANDATORY;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (dOCUMENTID == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(dOCUMENTID);
            }
            parcel.writeString(dOCUMENTNAME);
            if (iSMANDATORY == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(iSMANDATORY);
            }
        }
    }

}
