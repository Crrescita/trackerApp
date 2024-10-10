
package modelResponse.places_api_response;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours implements Parcelable
{

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    public final static Creator<OpeningHours> CREATOR = new Creator<OpeningHours>() {


        public OpeningHours createFromParcel(android.os.Parcel in) {
            return new OpeningHours(in);
        }

        public OpeningHours[] newArray(int size) {
            return (new OpeningHours[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected OpeningHours(android.os.Parcel in) {
        this.openNow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public OpeningHours() {
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(openNow);
    }

    public int describeContents() {
        return  0;
    }

}
