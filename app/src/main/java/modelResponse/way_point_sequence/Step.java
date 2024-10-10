
package modelResponse.way_point_sequence;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable
{

    @SerializedName("distance")
    @Expose
    private Distance__1 distance;
    @SerializedName("duration")
    @Expose
    private Duration__1 duration;
    @SerializedName("end_location")
    @Expose
    private EndLocation__1 endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private Polyline polyline;
    @SerializedName("start_location")
    @Expose
    private StartLocation__1 startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;
    public final static Creator<Step> CREATOR = new Creator<Step>() {


        public Step createFromParcel(android.os.Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return (new Step[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected Step(android.os.Parcel in) {
        this.distance = ((Distance__1) in.readValue((Distance__1.class.getClassLoader())));
        this.duration = ((Duration__1) in.readValue((Duration__1.class.getClassLoader())));
        this.endLocation = ((EndLocation__1) in.readValue((EndLocation__1.class.getClassLoader())));
        this.htmlInstructions = ((String) in.readValue((String.class.getClassLoader())));
        this.polyline = ((Polyline) in.readValue((Polyline.class.getClassLoader())));
        this.startLocation = ((StartLocation__1) in.readValue((StartLocation__1.class.getClassLoader())));
        this.travelMode = ((String) in.readValue((String.class.getClassLoader())));
        this.maneuver = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Step() {
    }

    public Distance__1 getDistance() {
        return distance;
    }

    public void setDistance(Distance__1 distance) {
        this.distance = distance;
    }

    public Duration__1 getDuration() {
        return duration;
    }

    public void setDuration(Duration__1 duration) {
        this.duration = duration;
    }

    public EndLocation__1 getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation__1 endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public StartLocation__1 getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation__1 startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(distance);
        dest.writeValue(duration);
        dest.writeValue(endLocation);
        dest.writeValue(htmlInstructions);
        dest.writeValue(polyline);
        dest.writeValue(startLocation);
        dest.writeValue(travelMode);
        dest.writeValue(maneuver);
    }

    public int describeContents() {
        return  0;
    }

}
