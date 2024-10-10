package modelResponse.way_point_sequence.own_array;

public class MyRequestedSequenceData {

    private String rowId;
    private String latLong;
    private int sequence;
    private String addreses;

    public String getAddreses() {
        return addreses;
    }

    public void setAddreses(String addreses) {
        this.addreses = addreses;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
