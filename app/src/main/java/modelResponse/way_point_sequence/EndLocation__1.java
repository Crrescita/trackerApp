
package modelResponse.way_point_sequence;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndLocation__1 implements Parcelable
{

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    public final static Creator<EndLocation__1> CREATOR = new Creator<EndLocation__1>() {


        public EndLocation__1 createFromParcel(android.os.Parcel in) {
            return new EndLocation__1(in);
        }

        public EndLocation__1 [] newArray(int size) {
            return (new EndLocation__1[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected EndLocation__1(android.os.Parcel in) {
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lng = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public EndLocation__1() {
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
