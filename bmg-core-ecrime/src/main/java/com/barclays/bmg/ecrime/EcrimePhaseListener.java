package com.barclays.bmg.ecrime;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.barclays.bmg.utils.SpringBeansUtils;

/**
 * This JSF Phase Listener is used to populate the Response according to JSF ViewRoot.
 * 
 * @author
 */
public class EcrimePhaseListener implements PhaseListener {

    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = 1L;

    // private final static Logger logger =
    // Logger.getLogger(EcrimeFacadePhaseListener.class);

    /**
     * @param event
     * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
     */

    public void afterPhase(PhaseEvent event) {

	EcrimeFlowManager flowManager = SpringBeansUtils.getBeanForType(event.getFacesContext(), EcrimeFlowManager.class);
	if (flowManager != null) {
	    flowManager.executeResponse(event);
	}
    }

    /**
     * @param event
     * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
     */
    public void beforePhase(PhaseEvent event) {
	// TODO Auto-generated method stub

    }

    /**
     * @return
     * @see javax.faces.event.PhaseListener#getPhaseId()
     */
    public PhaseId getPhaseId() {
	return PhaseId.ANY_PHASE;
    }

}
