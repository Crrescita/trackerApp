
package modelResponse.way_point_sequence;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocodedWaypoint implements Parcelable
{

    @SerializedName("geocoder_status")
    @Expose
    private String geocoderStatus;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("types")
    @Expose
    private List<String> types;
    public final static Creator<GeocodedWaypoint> CREATOR = new Creator<GeocodedWaypoint>() {


        public GeocodedWaypoint createFromParcel(android.os.Parcel in) {
            return new GeocodedWaypoint(in);
        }

        public GeocodedWaypoint[] newArray(int size) {
            return (new GeocodedWaypoint[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected GeocodedWaypoint(android.os.Parcel in) {
        this.geocoderStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.placeId = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.types, (String.class.getClassLoader()));
    }

    public GeocodedWaypoint() {
    }

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(geocoderStatus);
        dest.writeValue(placeId);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}
