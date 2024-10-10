
package modelResponse.gps_geo_code;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Viewport implements Parcelable
{

    @SerializedName("northeast")
    @Expose
    private Northeast__1 northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest__1 southwest;
    public final static Creator<Viewport> CREATOR = new Creator<Viewport>() {


        public Viewport createFromParcel(android.os.Parcel in) {
            return new Viewport(in);
        }

        public Viewport[] newArray(int size) {
            return (new Viewport[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected Viewport(android.os.Parcel in) {
        this.northeast = ((Northeast__1) in.readValue((Northeast__1.class.getClassLoader())));
        this.southwest = ((Southwest__1) in.readValue((Southwest__1.class.getClassLoader())));
    }

    public Viewport() {
    }

    public Northeast__1 getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast__1 northeast) {
        this.northeast = northeast;
    }

    public Southwest__1 getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest__1 southwest) {
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
