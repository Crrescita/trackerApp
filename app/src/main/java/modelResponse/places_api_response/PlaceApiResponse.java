
package modelResponse.places_api_response;

import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceApiResponse implements Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions;
    @SerializedName("results")
    @Expose
    private List<Result> results;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<PlaceApiResponse> CREATOR = new Creator<PlaceApiResponse>() {


        public PlaceApiResponse createFromParcel(android.os.Parcel in) {
            return new PlaceApiResponse(in);
        }

        public PlaceApiResponse[] newArray(int size) {
            return (new PlaceApiResponse[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected PlaceApiResponse(android.os.Parcel in) {
        in.readList(this.htmlAttributions, (Object.class.getClassLoader()));
        in.readList(this.results, (Result.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PlaceApiResponse() {
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
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
        dest.writeList(htmlAttributions);
        dest.writeList(results);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
