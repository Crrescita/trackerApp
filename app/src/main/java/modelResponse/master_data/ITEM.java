
package modelResponse.master_data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ITEM implements Parcelable
{

    @SerializedName("MASTER_ID")
    @Expose
    private String mASTERID;
    @SerializedName("MASTER_NAME")
    @Expose
    private String mASTERNAME;
    @SerializedName("ITEM_ID")
    @Expose
    private String iTEMID;
    @SerializedName("ITEM_NAME")
    @Expose
    private String iTEMNAME;
    public final static Creator<ITEM> CREATOR = new Creator<ITEM>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ITEM createFromParcel(Parcel in) {
            return new ITEM(in);
        }

        public ITEM[] newArray(int size) {
            return (new ITEM[size]);
        }

    }
    ;

    protected ITEM(Parcel in) {
        this.mASTERID = ((String) in.readValue((String.class.getClassLoader())));
        this.mASTERNAME = ((String) in.readValue((String.class.getClassLoader())));
        this.iTEMID = ((String) in.readValue((String.class.getClassLoader())));
        this.iTEMNAME = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ITEM() {
    }

    public String getMASTERID() {
        return mASTERID;
    }

    public void setMASTERID(String mASTERID) {
        this.mASTERID = mASTERID;
    }

    public String getMASTERNAME() {
        return mASTERNAME;
    }

    public void setMASTERNAME(String mASTERNAME) {
        this.mASTERNAME = mASTERNAME;
    }

    public String getITEMID() {
        return iTEMID;
    }

    public void setITEMID(String iTEMID) {
        this.iTEMID = iTEMID;
    }

    public String getITEMNAME() {
        return iTEMNAME;
    }

    public void setITEMNAME(String iTEMNAME) {
        this.iTEMNAME = iTEMNAME;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mASTERID);
        dest.writeValue(mASTERNAME);
        dest.writeValue(iTEMID);
        dest.writeValue(iTEMNAME);
    }

    public int describeContents() {
        return  0;
    }

}
