
package modelResponse.way_point_sequence;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Polyline implements Parcelable
{

    @SerializedName("points")
    @Expose
    private String points;
    public final static Creator<Polyline> CREATOR = new Creator<Polyline>() {


        public Polyline createFromParcel(android.os.Parcel in) {
            return new Polyline(in);
        }

        public Polyline[] newArray(int size) {
            return (new Polyline[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected Polyline(android.os.Parcel in) {
        this.points = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Polyline() {
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
