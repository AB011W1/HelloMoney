package com.barclays.bmg.service.sessionactivity.builder;

import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;

public interface BMGSessionActivityBuilder {

	SessionActivityDTO build(Object args[], Object result);

}
