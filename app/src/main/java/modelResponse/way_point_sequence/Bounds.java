
package modelResponse.way_point_sequence;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds implements Parcelable
{

    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;
    public final static Creator<Bounds> CREATOR = new Creator<Bounds>() {


        public Bounds createFromParcel(android.os.Parcel in) {
            return new Bounds(in);
        }

        public Bounds[] newArray(int size) {
            return (new Bounds[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected Bounds(android.os.Parcel in) {
        this.northeast = ((Northeast) in.readValue((Northeast.class.getClassLoader())));
        this.southwest = ((Southwest) in.readValue((Southwest.class.getClassLoader())));
    }

    public Bounds() {
    }

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(northeast);
        dest.writeValue(southwest);
    }

    public int describeContents() {
        return  0;
    }

}
