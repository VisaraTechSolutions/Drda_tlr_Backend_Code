package com.csr.csrwebapplication.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.csr.csrwebapplication.Model.RequestApplication;
import com.csr.csrwebapplication.Service.RequestApplicationService;

/**
 * Service class to handle scheduled tasks related to request applications.
 */
@Service
public class ApplicationSchedulerService {

	@Autowired
	private RequestApplicationService requestApplicationService;

	/**
	 * Scheduled task that runs every 5 minutes to remove applications older than 30
	 * days from the 'Selected' sponsor status.
	 */
	@Scheduled(fixedRate = 300000) // 300000 milliseconds = 5 minutes
	public void checkAndResetSponsorStatus() {
		List<RequestApplication> applications = requestApplicationService.getApplicationsWithSponsorStatusSelected();
		Date currentDate = new Date();

		for (RequestApplication application : applications) {
			if (application.getSelectedDateFromCommonpool() != null) {
				long diffInMillis = currentDate.getTime() - application.getSelectedDateFromCommonpool().getTime();
				long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);

				// If the application is older than 30 days, reset its sponsor status
				if (diffInDays >= 30) {
					application.setFundingStatus(null);
					application.setSponsorStatus(null);
					application.setSponsor(null);
					requestApplicationService.save(application);
				}
			}
		}
	}
}
