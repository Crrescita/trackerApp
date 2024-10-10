package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

public class DynamicPhotoModel implements Parcelable {
    private int id;
    private String tagName="";
    private String imagePath="";
    private String imagePath_second="";
    private boolean isMandatory = true;
    private String capturedLatAndLong = "";
    private String docClassification = "";

    public DynamicPhotoModel(int id, String tagName, String imagePath,String imagePath_second, boolean isMandatory,String capturedLatAndLong,String docClassification) {
        this.id = id;
        this.tagName = tagName;
        this.imagePath = imagePath;
        this.imagePath_second = imagePath_second;
        this.isMandatory = isMandatory;
        this.capturedLatAndLong = capturedLatAndLong;
        this.docClassification = docClassification;
    }

    public String getDocClassification() {
        return docClassification;
    }

    public void setDocClassification(String docClassification) {
        this.docClassification = docClassification;
    }

    protected DynamicPhotoModel(Parcel in) {
        id = in.readInt();
        tagName = in.readString();
        imagePath = in.readString();
        imagePath_second = in.readString();
        docClassification = in.readString();
        capturedLatAndLong = in.readString();
        isMandatory = in.readByte() != 0;
    }


    public static final Creator<DynamicPhotoModel> CREATOR = new Creator<DynamicPhotoModel>() {
        @Override
        public DynamicPhotoModel createFromParcel(Parcel in) {
            return new DynamicPhotoModel(in);
        }

        @Override
        public DynamicPhotoModel[] newArray(int size) {
            return new DynamicPhotoModel[size];
        }
    };

    public String getImagePath_second() {
        return imagePath_second;
    }

    public void setImagePath_second(String imagePath_second) {
        this.imagePath_second = imagePath_second;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapturedLatAndLong() {
        return capturedLatAndLong;
    }

    public void setCapturedLatAndLong(String capturedLatAndLong) {
        this.capturedLatAndLong = capturedLatAndLong;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(tagName);
        parcel.writeString(imagePath);
        parcel.writeString(imagePath_second);
        parcel.writeString(capturedLatAndLong);
        parcel.writeByte((byte) (isMandatory ? 1 : 0));
    }
}
