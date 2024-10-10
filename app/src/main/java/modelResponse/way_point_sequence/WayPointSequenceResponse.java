
package modelResponse.way_point_sequence;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WayPointSequenceResponse implements Parcelable
{

    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWaypoint> geocodedWaypoints;
    @SerializedName("routes")
    @Expose
    private List<Route> routes;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<WayPointSequenceResponse> CREATOR = new Creator<WayPointSequenceResponse>() {


        public WayPointSequenceResponse createFromParcel(android.os.Parcel in) {
            return new WayPointSequenceResponse(in);
        }

        public WayPointSequenceResponse[] newArray(int size) {
            return (new WayPointSequenceResponse[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected WayPointSequenceResponse(android.os.Parcel in) {
        in.readList(this.geocodedWaypoints, (GeocodedWaypoint.class.getClassLoader()));
        in.readList(this.routes, (Route.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public WayPointSequenceResponse() {
    }

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(geocodedWaypoints);
        dest.writeList(routes);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
