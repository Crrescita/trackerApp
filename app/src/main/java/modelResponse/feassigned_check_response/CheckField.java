
package modelResponse.feassigned_check_response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckField implements Parcelable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Creator<CheckField> CREATOR = new Creator<CheckField>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CheckField createFromParcel(Parcel in) {
            return new CheckField(in);
        }

        public CheckField[] newArray(int size) {
            return (new CheckField[size]);
        }

    }
    ;

    protected CheckField(Parcel in) {
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CheckField() {
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
