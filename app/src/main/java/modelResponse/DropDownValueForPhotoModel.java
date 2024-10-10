package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

public class DropDownValueForPhotoModel implements Parcelable {
    private int id;
    private String dropDownValueId;
    private String tagName="";
    private boolean isSelected = false;

    public DropDownValueForPhotoModel(int id,String dropDownValueId, String tagName, boolean isSelected) {
        this.id = id;
        this.dropDownValueId = dropDownValueId;
        this.tagName = tagName;
        this.isSelected = isSelected;
    }

    protected DropDownValueForPhotoModel(Parcel in) {
        id = in.readInt();
        dropDownValueId = in.readString();
        tagName = in.readString();
        isSelected = in.readByte() != 0;

    }

    public static final Creator<DropDownValueForPhotoModel> CREATOR = new Creator<DropDownValueForPhotoModel>() {
        @Override
        public DropDownValueForPhotoModel createFromParcel(Parcel in) {
            return new DropDownValueForPhotoModel(in);
        }

        @Override
        public DropDownValueForPhotoModel[] newArray(int size) {
            return new DropDownValueForPhotoModel[size];
        }
    };


    public String getDropDownValueId() {
        return dropDownValueId;
    }

    public void setDropDownValueId(String dropDownId) {
        this.dropDownValueId = dropDownId;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(tagName);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
