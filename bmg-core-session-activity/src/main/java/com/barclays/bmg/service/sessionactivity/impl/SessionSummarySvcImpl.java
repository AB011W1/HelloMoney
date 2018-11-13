package com.barclays.bmg.service.sessionactivity.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.SessionActivityType;
import com.barclays.bmg.service.sessionactivity.SessionSummaryService;
import com.barclays.bmg.service.sessionactivity.SessionSummaryServiceRequest;
import com.barclays.bmg.service.sessionactivity.dao.SessionActivityDAO;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.sessionactivity.dto.SessionSummaryDTO;

public class SessionSummarySvcImpl implements SessionSummaryService {

	@Autowired
	private SessionActivityDAO sessionActivityDAO;

	@Override
	public SessionSummaryDTO getSessionSummary(SessionSummaryServiceRequest req) {

		List<SessionActivityDTO> sesList = sessionActivityDAO
				.findBySession(req);
		return composeSummary(sesList);
	}

	protected SessionSummaryDTO composeSummary(List<SessionActivityDTO> list) {
		SessionSummaryDTO sessionSummary = new SessionSummaryDTO();
		SessionActivityDTO loginTmp = null;
		SessionActivityDTO logoutTmp = null;

		if (list == null) {
			return sessionSummary;
		}
		// set session id for session summary
		if (list != null && list.size() > 0) {
			sessionSummary.setSessionId(list.get(0).getSessionId());
		}

		//StringBuilder logMsg = new StringBuilder();
		for (SessionActivityDTO activity : list) {
			if (activity.getTxnTyp()
					.equalsIgnoreCase(SessionActivityType.LOGIN)) {
				if (sessionSummary.getLoginTime() == null
						|| sessionSummary.getLoginTime().after(
								activity.getTxnTime())) {
					sessionSummary.setLoginTime(activity.getTxnTime());
				}
				loginTmp = activity;
			} else if (activity.getTxnTyp().equalsIgnoreCase(
					SessionActivityType.LOGOUT)) {
				if (sessionSummary.getLogoutTime() == null
						|| sessionSummary.getLogoutTime().before(
								activity.getTxnTime())) {
					sessionSummary.setLogoutTime(activity.getTxnTime());
				}
				logoutTmp = activity;
			}
		}
		if (loginTmp != null)
			list.remove(loginTmp);
		if (logoutTmp != null)
			list.remove(logoutTmp);

		if (sessionSummary.getLoginTime() == null) {
			if (list != null && list.size() > 0) {
				sessionSummary.setLoginTime(addMinutes(
						list.get(0).getTxnTime(), -10));
			} else if (sessionSummary.getLogoutTime() != null) {
				sessionSummary.setLoginTime(addMinutes(sessionSummary
						.getLogoutTime(), -10));
			} else {
				sessionSummary.setLoginTime(addMinutes(new Date(), -10));
			}
		}

		if (sessionSummary.getLogoutTime() == null) {
			if (list.size() > 0) {
				Date logoutTime = addMinutes(list.get(list.size() - 1)
						.getTxnTime(), 10);
				sessionSummary.setLogoutTime(logoutTime);
			} else if (sessionSummary.getLoginTime() != null) {
				Date logoutTime = addMinutes(sessionSummary.getLoginTime(), 10);
				sessionSummary.setLogoutTime(logoutTime);
			} else {
				sessionSummary.setLogoutTime(new Date());
			}
		}

		long start = sessionSummary.getLoginTime().getTime();
		long end = sessionSummary.getLogoutTime().getTime();
		long duration = (end - start) / SessionSummaryDTO.ONE_MINUTE;
		if ((end - start) % SessionSummaryDTO.ONE_MINUTE != 0) {
			duration = duration + 1;
		}
		long totalDuration = end - start;
		long durationHours = totalDuration / SessionSummaryDTO.ONE_HOUR;
		totalDuration -= durationHours * SessionSummaryDTO.ONE_HOUR;
		long durationMinutes = totalDuration / SessionSummaryDTO.ONE_MINUTE;
		totalDuration -= durationMinutes * SessionSummaryDTO.ONE_MINUTE;
		long durationSeconds = totalDuration / 1000;
		sessionSummary.setDuration(duration);
		sessionSummary.setDurationHours(durationHours == 0 ? "" : ""
				+ durationHours);
		sessionSummary.setDurationMinutes(durationMinutes == 0 ? "" : ""
				+ durationMinutes);
		sessionSummary.setDurationSeconds(durationSeconds == 0 ? "" : ""
				+ durationSeconds);

		// sort session activity according txnTime
		sessionSummary.setSessionActivityList(list);
		sortSessionActivity(list);
		return sessionSummary;
	}

	/**
	 * sort session activity according txnTime
	 *
	 * @param list
	 */
	private void sortSessionActivity(List<SessionActivityDTO> list) {
		Collections.sort(list, new Comparator<SessionActivityDTO>() {
			public int compare(SessionActivityDTO s1, SessionActivityDTO s2) {
				return s1.getTxnTime().compareTo(s2.getTxnTime());
			}
		});
	}

	/**
	 * sort session summary according loginTime
	 *
	 * @param list
	 */
	private void sortSessionSummary(List<SessionSummaryDTO> list) {
		Collections.sort(list, new Comparator<SessionSummaryDTO>() {
			public int compare(SessionSummaryDTO s1, SessionSummaryDTO s2) {
				return s1.getLoginTime().compareTo(s2.getLoginTime());
			}
		});
	}

	/**
	 * get first day of within n months
	 *
	 * @param lastMonths
	 * @return
	 */
	private Date getWithinMonthsFirstDay(int lastMonths) {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MONTH, 1 - lastMonths);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * @param sessionSummary
	 * @return
	 */
	private Date addMinutes(Date firstDate, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		cal.add(Calendar.MINUTE, minutes);
		Date returnTime = cal.getTime();
		if (returnTime.after(new Date())) {
			returnTime = new Date();
		}
		return returnTime;
	}

	public SessionActivityDAO getSessionActivityDAO() {
		return sessionActivityDAO;
	}

	public void setSessionActivityDAO(SessionActivityDAO sessionActivityDAO) {
		this.sessionActivityDAO = sessionActivityDAO;
	}

}
