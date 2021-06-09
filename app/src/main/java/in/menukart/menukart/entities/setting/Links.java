package in.menukart.menukart.entities.setting;

import java.io.Serializable;

public class Links implements Serializable {
    private String cancellation_and_refund;
    private String privacy_policy;
    private String terms_and_conditions;

    public String getCancellation_and_refund() {
        return cancellation_and_refund;
    }

    public void setCancellation_and_refund(String cancellation_and_refund) {
        this.cancellation_and_refund = cancellation_and_refund;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public String getTerms_and_conditions() {
        return terms_and_conditions;
    }

    public void setTerms_and_conditions(String terms_and_conditions) {
        this.terms_and_conditions = terms_and_conditions;
    }


}