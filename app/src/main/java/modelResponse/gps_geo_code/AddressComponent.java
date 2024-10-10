
package modelResponse.gps_geo_code;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressComponent implements Parcelable
{

    @SerializedName("long_name")
    @Expose
    private String longName;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("types")
    @Expose
    private List<String> types;
    public final static Creator<AddressComponent> CREATOR = new Creator<AddressComponent>() {


        public AddressComponent createFromParcel(android.os.Parcel in) {
            return new AddressComponent(in);
        }

        public AddressComponent[] newArray(int size) {
            return (new AddressComponent[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected AddressComponent(android.os.Parcel in) {
        this.longName = ((String) in.readValue((String.class.getClassLoader())));
        this.shortName = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.types, (String.class.getClassLoader()));
    }

    public AddressComponent() {
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(longName);
        dest.writeValue(shortName);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}
