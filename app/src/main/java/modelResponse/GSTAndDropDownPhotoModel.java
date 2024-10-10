package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

public class GSTAndDropDownPhotoModel implements Parcelable {
    private int id;
    private String tagName="";
    private String imagePath="";
    private boolean isMandatory = true;
    private int dropDownId;
    private String dropDownName = "";
    private String dropDownvalueId = "";

    public GSTAndDropDownPhotoModel(int id, String tagName, String imagePath, boolean isMandatory,int dropDownId,String dropDownName,String dropDownvalueId) {
        this.id = id;
        this.tagName = tagName;
        this.imagePath = imagePath;
        this.isMandatory = isMandatory;
        this.dropDownId = dropDownId;
        this.dropDownName = dropDownName;
        this.dropDownvalueId = dropDownvalueId;
    }

    protected GSTAndDropDownPhotoModel(Parcel in) {
        id = in.readInt();
        tagName = in.readString();
        imagePath = in.readString();
        isMandatory = in.readByte() != 0;
        dropDownId = in.readInt();
        dropDownName = in.readString();
        dropDownvalueId = in.readString();
    }

    public static final Creator<GSTAndDropDownPhotoModel> CREATOR = new Creator<GSTAndDropDownPhotoModel>() {
        @Override
        public GSTAndDropDownPhotoModel createFromParcel(Parcel in) {
            return new GSTAndDropDownPhotoModel(in);
        }

        @Override
        public GSTAndDropDownPhotoModel[] newArray(int size) {
            return new GSTAndDropDownPhotoModel[size];
        }
    };

    public String getDropDownvalueId() {
        return dropDownvalueId;
    }

    public void setDropDownvalueId(String dropDownvalueId) {
        this.dropDownvalueId = dropDownvalueId;
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

    public int getDropDownId() {
        return dropDownId;
    }

    public void setDropDownId(int dropDownId) {
        this.dropDownId = dropDownId;
    }

    public String getDropDownName() {
        return dropDownName;
    }

    public void setDropDownName(String dropDownName) {
        this.dropDownName = dropDownName;
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
        parcel.writeByte((byte) (isMandatory ? 1 : 0));
        parcel.writeInt(dropDownId);
        parcel.writeString(dropDownName);
    }
}
