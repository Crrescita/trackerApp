package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

public class MultiCallModel implements Parcelable {
    private int id;
    private int thumnail;
    private String candidateNumber;
    private boolean isSelected = false;
    private String callTitle;

    public MultiCallModel() {
    }

    public MultiCallModel(int id, int thumnail, String candidateNumber, boolean isSelected, String callTitle) {
        this.id =id;
        this.thumnail = thumnail;
        this.candidateNumber = candidateNumber;
        this.isSelected = isSelected;
        this.callTitle = callTitle;
    }

    protected MultiCallModel(Parcel in) {
        id = in.readInt();
        thumnail = in.readInt();
        candidateNumber = in.readString();
        isSelected = in.readByte() != 0;
        callTitle = in.readString();
    }

    public static final Creator<MultiCallModel> CREATOR = new Creator<MultiCallModel>() {
        @Override
        public MultiCallModel createFromParcel(Parcel in) {
            return new MultiCallModel(in);
        }

        @Override
        public MultiCallModel[] newArray(int size) {
            return new MultiCallModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCallTitle() {
        return callTitle;
    }

    public void setCallTitle(String callTitle) {
        this.callTitle = callTitle;
    }

    public int getThumnail() {
        return thumnail;
    }

    public void setThumnail(int thumnail) {
        this.thumnail = thumnail;
    }

    public String getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(String candidateNumber) {
        this.candidateNumber = candidateNumber;
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
        parcel.writeString(candidateNumber);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(callTitle);
    }
}
