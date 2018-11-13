package com.barclays.bmg.ecrime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EcrimeService {

    public void executeRequest(EcrimeContext context, HttpServletRequest request, HttpServletResponse response);

    public void executeResponse(EcrimeContext context, Object bean);

    public Runnable createRequestTask(EcrimeContext context);

    public Runnable createResponseTask(EcrimeContext context, Object bean);

    public void setTransactionStatus(EcrimeContext context, boolean status, String description);

}
