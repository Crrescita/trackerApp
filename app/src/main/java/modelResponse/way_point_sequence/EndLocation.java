
package modelResponse.way_point_sequence;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndLocation implements Parcelable
{

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    public final static Creator<EndLocation> CREATOR = new Creator<EndLocation>() {


        public EndLocation createFromParcel(android.os.Parcel in) {
            return new EndLocation(in);
        }

        public EndLocation[] newArray(int size) {
            return (new EndLocation[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected EndLocation(android.os.Parcel in) {
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lng = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public EndLocation() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(lat);
        dest.writeValue(lng);
    }

    public int describeContents() {
        return  0;
    }

}
