package modelResponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PhotoDetailsData implements Parcelable {
    private ArrayList<CheckFieldsResponse.PHOTODETAIL> pHOTODETAILS = null;


    public static final Creator<PhotoDetailsData> CREATOR = new Creator<PhotoDetailsData>() {
        @Override
        public PhotoDetailsData createFromParcel(Parcel in) {
            return new PhotoDetailsData(in);
        }

        @Override
        public PhotoDetailsData[] newArray(int size) {
            return new PhotoDetailsData[size];
        }
    };


    public PhotoDetailsData() {
    }

    protected PhotoDetailsData(Parcel in) {
        this.pHOTODETAILS = (ArrayList<CheckFieldsResponse.PHOTODETAIL>) in.readValue((CheckFieldsResponse.PHOTODETAIL.class.getClassLoader()));
    }

    public ArrayList<CheckFieldsResponse.PHOTODETAIL> getpHOTODETAILS() {
        return pHOTODETAILS;
    }

    public void setpHOTODETAILS(ArrayList<CheckFieldsResponse.PHOTODETAIL> pHOTODETAILS) {
        this.pHOTODETAILS = pHOTODETAILS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pHOTODETAILS);
    }
}
