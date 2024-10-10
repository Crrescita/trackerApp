package adapter.modelAdapter;

/**
 * Created by Kapil Rathee on 20/9/18.
 */

public class AssignedRecycleModel {

    private String client_id, candidate_name, check_id, case_check_id, address, landmark, distance, vendor_request_id,
            status, source_id, fe_table_id, flag, appointment, appointment_slot,processId,invoiceDetailsOne,invoiceDetailsTwo,
            invoiceDetailsThree,documentsMinLimit,documentsMaxLimit,familyId,routeOrder,isRouteOptimized,isCoordinateMissing,
            city,state,country,groupId,km_obtian_from_way_point,is_auto_closure,candidate_photo,nid_photo, house_photo,
            address_proof, landmark_photo, is_candidate_signature_required;
    private int tempPosition;

    public AssignedRecycleModel() {
    }

    public AssignedRecycleModel(String clientID, String candidate_name, String check_id, String case_check_id,
                                String address, String landmark, String distance, String vendor_request_id, String status,
                                String source_id, String fe_table_id, String flag, String appointment, String appointment_slot,String processId,
                                String invoiceDetailsOne,String invoiceDetailsTwo, String invoiceDetailsThree,
                                String documentsMinLimit,String documentsMaxLimit,String familyId,String routeOrder,
                                String isRouteOptimized,String isCoordinateMissing,String city,String state,String
                                        country,String groupId,String km_obtian_from_way_point,String is_auto_closure,int tempPosition,
                                String candidate_photo,String nid_photo,String house_photo,String address_proof,String landmark_photo,String is_candidate_signature_required
    ) {
        this.candidate_photo = candidate_photo;
        this.nid_photo = nid_photo;
        this.house_photo = house_photo;
        this.address_proof = address_proof;
        this.landmark_photo = landmark_photo;
        this.is_candidate_signature_required=is_candidate_signature_required;
        this.client_id = clientID;
        this.candidate_name = candidate_name;
        this.check_id = check_id;
        this.case_check_id = case_check_id;
        this.address = address;
        this.landmark = landmark;
        this.distance = distance;
        this.vendor_request_id = vendor_request_id;
        this.status = status;
        this.source_id = source_id;
        this.fe_table_id = fe_table_id;
        this.flag = flag;
        this.appointment = appointment;
        this.appointment_slot = appointment_slot;
        this.processId = processId;
        this.invoiceDetailsOne = invoiceDetailsOne;
        this.invoiceDetailsTwo = invoiceDetailsTwo;
        this.invoiceDetailsThree = invoiceDetailsThree;
        this.documentsMinLimit = documentsMinLimit;
        this.documentsMaxLimit = documentsMaxLimit;
        this.familyId = familyId;
        this.routeOrder = routeOrder;
        this.isRouteOptimized = isRouteOptimized;
        this.isCoordinateMissing = isCoordinateMissing;
        this.city = city;
        this.state = state;
        this.country = country;
        this.groupId = groupId;
        this.km_obtian_from_way_point = km_obtian_from_way_point;
        this.tempPosition = tempPosition;
        this.is_auto_closure = is_auto_closure;
    }

    public int getTempPosition() {
        return tempPosition;
    }

    public void setTempPosition(int tempPosition) {
        this.tempPosition = tempPosition;
    }

    public String getKm_obtian_from_way_point() {
        return km_obtian_from_way_point;
    }

    public void setKm_obtian_from_way_point(String km_obtian_from_way_point) {
        this.km_obtian_from_way_point = km_obtian_from_way_point;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsRouteOptimized() {
        return isRouteOptimized;
    }

    public void setIsRouteOptimized(String isRouteOptimized) {
        this.isRouteOptimized = isRouteOptimized;
    }

    public String getIsCoordinateMissing() {
        return isCoordinateMissing;
    }

    public void setIsCoordinateMissing(String isCoordinateMissing) {
        this.isCoordinateMissing = isCoordinateMissing;
    }

    public String getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(String routeOrder) {
        this.routeOrder = routeOrder;
    }

    public String getInvoiceDetailsOne() {
        return invoiceDetailsOne;
    }

    public void setInvoiceDetailsOne(String invoiceDetailsOne) {
        this.invoiceDetailsOne = invoiceDetailsOne;
    }

    public String getInvoiceDetailsTwo() {
        return invoiceDetailsTwo;
    }

    public void setInvoiceDetailsTwo(String invoiceDetailsTwo) {
        this.invoiceDetailsTwo = invoiceDetailsTwo;
    }

    public String getInvoiceDetailsThree() {
        return invoiceDetailsThree;
    }

    public void setInvoiceDetailsThree(String invoiceDetailsThree) {
        this.invoiceDetailsThree = invoiceDetailsThree;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getClientID() {
        return client_id;
    }

    public void setClientID(String clientID) {
        this.client_id = clientID;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getCase_check_id() {
        return case_check_id;
    }

    public void setCase_check_id(String case_check_id) {
        this.case_check_id = case_check_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVendor_request_id() {
        return vendor_request_id;
    }

    public void setVendor_request_id(String vendor_request_id) {
        this.vendor_request_id = vendor_request_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getFe_table_id() {
        return fe_table_id;
    }

    public void setFe_table_id(String fe_table_id) {
        this.fe_table_id = fe_table_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getAppointment_slot() {
        return appointment_slot;
    }

    public void setAppointment_slot(String appointment_slot) {
        this.appointment_slot = appointment_slot;
    }

    public String getDocumentsMinLimit() {
        return documentsMinLimit;
    }

    public void setDocumentsMinLimit(String documentsMinLimit) {
        this.documentsMinLimit = documentsMinLimit;
    }

    public String getDocumentsMaxLimit() {
        return documentsMaxLimit;
    }

    public String getIs_auto_closure() {
        return is_auto_closure;
    }

    public void setIs_auto_closure(String is_auto_closure) {
        this.is_auto_closure = is_auto_closure;
    }

    public void setDocumentsMaxLimit(String documentsMaxLimit) {
        this.documentsMaxLimit = documentsMaxLimit;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getCandidate_photo() {
        return candidate_photo;
    }

    public void setCandidate_photo(String candidate_photo) {
        this.candidate_photo = candidate_photo;
    }

    public String getNid_photo() {
        return nid_photo;
    }

    public void setNid_photo(String nid_photo) {
        this.nid_photo = nid_photo;
    }

    public String getHouse_photo() {
        return house_photo;
    }

    public void setHouse_photo(String house_photo) {
        this.house_photo = house_photo;
    }

    public String getAddress_proof() {
        return address_proof;
    }

    public void setAddress_proof(String address_proof) {
        this.address_proof = address_proof;
    }

    public String getLandmark_photo() {
        return landmark_photo;
    }

    public void setLandmark_photo(String landmark_photo) {
        this.landmark_photo = landmark_photo;
    }

    public String getIs_candidate_signature_required() {
        return is_candidate_signature_required;
    }

    public void setIs_candidate_signature_required(String is_candidate_signature_required) {
        this.is_candidate_signature_required = is_candidate_signature_required;
    }
}
