
package modelResponse.master_data;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("ITEMS")
    @Expose
    private List<ITEM> iTEMS = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    protected Data(Parcel in) {
        in.readList(this.iTEMS, (ITEM.class.getClassLoader()));
    }

    public Data() {
    }

    public List<ITEM> getITEMS() {
        return iTEMS;
    }

    public void setITEMS(List<ITEM> iTEMS) {
        this.iTEMS = iTEMS;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(iTEMS);
    }

    public int describeContents() {
        return  0;
    }

}
