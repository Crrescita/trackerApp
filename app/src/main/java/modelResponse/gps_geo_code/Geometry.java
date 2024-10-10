
package modelResponse.gps_geo_code;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry implements Parcelable
{

    @SerializedName("bounds")
    @Expose
    private Bounds bounds;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;
    public final static Creator<Geometry> CREATOR = new Creator<Geometry>() {


        public Geometry createFromParcel(android.os.Parcel in) {
            return new Geometry(in);
        }

        public Geometry[] newArray(int size) {
            return (new Geometry[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected Geometry(android.os.Parcel in) {
        this.bounds = ((Bounds) in.readValue((Bounds.class.getClassLoader())));
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
        this.locationType = ((String) in.readValue((String.class.getClassLoader())));
        this.viewport = ((Viewport) in.readValue((Viewport.class.getClassLoader())));
    }

    public Geometry() {
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(bounds);
        dest.writeValue(location);
        dest.writeValue(locationType);
        dest.writeValue(viewport);
    }

    public int describeContents() {
        return  0;
    }

}
