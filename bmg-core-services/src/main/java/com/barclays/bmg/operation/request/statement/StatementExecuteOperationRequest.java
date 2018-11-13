package com.barclays.bmg.operation.request.statement;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.StatementRequestDTO;

public class StatementExecuteOperationRequest extends RequestContext {

    private StatementRequestDTO statementRequestDTO;

    /**
     * @return the statementRequestDTO
     */
    public StatementRequestDTO getStatementRequestDTO() {
	return statementRequestDTO;
    }

    /**
     * @param statementRequestDTO
     *            the statementRequestDTO to set
     */
    public void setStatementRequestDTO(StatementRequestDTO statementRequestDTO) {
	this.statementRequestDTO = statementRequestDTO;
    }

}
