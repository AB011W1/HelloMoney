package com.barclays.ussd.utils;

public interface IDummyResponse {

    /**
     * populate Dummy BMG Response Add your opcode and the JsonResponse sent by BMG.
     *
     * @param opCode
     *            the op code
     * @param mobileNumber
     *            the mobile number
     * @return the string
     */
    public abstract String populateDummyBMGResponse(final String opCode, final String mobileNumber);

    /**
     * populate Dummy BMG Response Add your opcode and the JsonResponse sent by BMG.
     *
     * @param opCode
     *            the op code
     * @return the string
     */
    public abstract String populateDummyBMGResponse(final String opCode);

}