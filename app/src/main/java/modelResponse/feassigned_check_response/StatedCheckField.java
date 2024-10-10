
package modelResponse.feassigned_check_response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatedCheckField implements Parcelable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Creator<StatedCheckField> CREATOR = new Creator<StatedCheckField>() {


        @SuppressWarnings({
            "unchecked"
        })
        public StatedCheckField createFromParcel(Parcel in) {
            return new StatedCheckField(in);
        }

        public StatedCheckField[] newArray(int size) {
            return (new StatedCheckField[size]);
        }

    }
    ;

    protected StatedCheckField(Parcel in) {
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public StatedCheckField() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(key);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
