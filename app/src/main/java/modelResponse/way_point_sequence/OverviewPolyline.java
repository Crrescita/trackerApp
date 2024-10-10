
package modelResponse.way_point_sequence;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverviewPolyline implements Parcelable
{

    @SerializedName("points")
    @Expose
    private String points;
    public final static Creator<OverviewPolyline> CREATOR = new Creator<OverviewPolyline>() {


        public OverviewPolyline createFromParcel(android.os.Parcel in) {
            return new OverviewPolyline(in);
        }

        public OverviewPolyline[] newArray(int size) {
            return (new OverviewPolyline[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected OverviewPolyline(android.os.Parcel in) {
        this.points = ((String) in.readValue((String.class.getClassLoader())));
    }

    public OverviewPolyline() {
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(points);
    }

    public int describeContents() {
        return  0;
    }

}
