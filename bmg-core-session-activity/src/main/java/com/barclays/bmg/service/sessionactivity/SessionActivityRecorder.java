package com.barclays.bmg.service.sessionactivity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;
import com.barclays.bmg.service.sessionactivity.builder.BMGSessionActivityBuilder;
import com.barclays.bmg.service.sessionactivity.dao.SessionActivityDAO;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.sessionactivity.util.SessionActivityUtility;

@Aspect
public class SessionActivityRecorder implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	// @Autowired
	SessionActivityDAO sessionActivityDAO;

	public SessionActivityDAO getSessionActivityDAO() {
		return sessionActivityDAO;
	}

	public void setSessionActivityDAO(SessionActivityDAO sessionActivityDAO) {
		this.sessionActivityDAO = sessionActivityDAO;
	}

	/**
	 * This is the entry point for the SessionActivityRecorder. The
	 * responsibility of this method is to a)Invoke the actual target.
	 * b)Retrieve the SessionDTOBuilder from application context and invoke it.
	 * c)Call the SessionActivityDAO to persist SessionActivity. Note: The
	 * Builder needs to configured as per the activity type.
	 *
	 * @param proceedingJoinPoint
	 * @param sessionActivitySupport
	 * @return
	 * @throws Exception
	 */
	@Around("@annotation(sessionActivitySupport)")
	public Object recordSessionActivity(
			ProceedingJoinPoint proceedingJoinPoint,
			SessionActivitySupport sessionActivitySupport) throws Exception {

		Object result = null;
		String activityType = sessionActivitySupport.activityType();
		String status = null;

		try {

			result = proceedingJoinPoint.proceed();
			status = "SUCCESS";

		} catch (Throwable e) {
			status = "FAILURE";

			throw (Exception) e;
		}

		finally {

			try {
				BMGSessionActivityBuilder builder = (BMGSessionActivityBuilder) applicationContext
						.getBean(activityType);

				SessionActivityDTO sessionActivityDTO = builder.build(
						proceedingJoinPoint.getArgs(), result);

				sessionActivityDTO.setStatus(status);
				setCommonAttributes(sessionActivityDTO);

				sessionActivityDTO.setDetails(SessionActivityUtility
						.detail2Xml(sessionActivityDTO.getTxnDetails()));

				sessionActivityDAO.insert(sessionActivityDTO);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private void setCommonAttributes(SessionActivityDTO sessionActivityDTO) {

		BMGGlobalContext bmgglContext = BMGContextHolder.getLogContext();
		sessionActivityDTO.setBusinessId(bmgglContext.getBusinessId());
		sessionActivityDTO.setSystemId(bmgglContext.getSystemId());

		// FIXME Set the activityreference number in sessionactivitydto
		// sessionActivityDTO.setRefNo(bmgglContext.)
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

}
