
package modelResponse.gps_geo_code;

import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GPSGeocodeResponse implements Parcelable
{

    @SerializedName("results")
    @Expose
    private List<Result> results;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<GPSGeocodeResponse> CREATOR = new Creator<GPSGeocodeResponse>() {


        public GPSGeocodeResponse createFromParcel(android.os.Parcel in) {
            return new GPSGeocodeResponse(in);
        }

        public GPSGeocodeResponse[] newArray(int size) {
            return (new GPSGeocodeResponse[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected GPSGeocodeResponse(android.os.Parcel in) {
        in.readList(this.results, (Result.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public GPSGeocodeResponse() {
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(results);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
