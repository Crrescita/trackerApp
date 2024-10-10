package modelResponse.way_point_sequence;


import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WayPointSequenceError implements Parcelable
{

@SerializedName("error_message")
@Expose
private String errorMessage;
@SerializedName("routes")
@Expose
private List<Object> routes;
@SerializedName("status")
@Expose
private String status;
public final static Creator<WayPointSequenceError> CREATOR = new Creator<WayPointSequenceError>() {


public WayPointSequenceError createFromParcel(android.os.Parcel in) {
return new WayPointSequenceError(in);
}

public WayPointSequenceError[] newArray(int size) {
return (new WayPointSequenceError[size]);
}

}
;

@SuppressWarnings({
"unchecked"
})
protected WayPointSequenceError(android.os.Parcel in) {
this.errorMessage = ((String) in.readValue((String.class.getClassLoader())));
in.readList(this.routes, (java.lang.Object.class.getClassLoader()));
this.status = ((String) in.readValue((String.class.getClassLoader())));
}

public WayPointSequenceError() {
}

public String getErrorMessage() {
return errorMessage;
}

public void setErrorMessage(String errorMessage) {
this.errorMessage = errorMessage;
}

public List<Object> getRoutes() {
return routes;
}

public void setRoutes(List<Object> routes) {
this.routes = routes;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public void writeToParcel(android.os.Parcel dest, int flags) {
dest.writeValue(errorMessage);
dest.writeList(routes);
dest.writeValue(status);
}

public int describeContents() {
return 0;
}

}