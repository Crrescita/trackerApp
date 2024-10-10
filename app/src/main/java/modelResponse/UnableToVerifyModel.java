package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

public class UnableToVerifyModel implements Parcelable {
    private int id;
    private int thumnail;
    private String reasonUnableToVerify;
    private boolean isSelected = false;
    private String messageUnableToVerify;

    public UnableToVerifyModel() {
    }

    public UnableToVerifyModel(int id, int thumnail, String reasonUnableToVerify, boolean isSelected, String messageUnableToVerify) {
        this.id =id;
        this.thumnail = thumnail;
        this.reasonUnableToVerify = reasonUnableToVerify;
        this.isSelected = isSelected;
        this.messageUnableToVerify = messageUnableToVerify;
    }

    protected UnableToVerifyModel(Parcel in) {
        id = in.readInt();
        thumnail = in.readInt();
        reasonUnableToVerify = in.readString();
        isSelected = in.readByte() != 0;
        messageUnableToVerify = in.readString();
    }

    public static final Creator<UnableToVerifyModel> CREATOR = new Creator<UnableToVerifyModel>() {
        @Override
        public UnableToVerifyModel createFromParcel(Parcel in) {
            return new UnableToVerifyModel(in);
        }

        @Override
        public UnableToVerifyModel[] newArray(int size) {
            return new UnableToVerifyModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageUnableToVerify() {
        return messageUnableToVerify;
    }

    public void setMessageUnableToVerify(String messageUnableToVerify) {
        this.messageUnableToVerify = messageUnableToVerify;
    }

    public int getThumnail() {
        return thumnail;
    }

    public void setThumnail(int thumnail) {
        this.thumnail = thumnail;
    }

    public String getReasonUnableToVerify() {
        return reasonUnableToVerify;
    }

    public void setReasonUnableToVerify(String reasonUnableToVerify) {
        this.reasonUnableToVerify = reasonUnableToVerify;
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
        parcel.writeInt(thumnail);
        parcel.writeString(reasonUnableToVerify);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(messageUnableToVerify);
    }
}
