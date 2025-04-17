package com.pal.taxi.system.comm.internal;

import com.pal.taxi.system.notification.IBookingRequestNotifier;

/**
 * This is an internal class, should not be used outside the system.<br>
 * The one who starts notifying that there is a booking rquest.
 * 
 * @author Palraj
 */
public interface IInternalBookingRequestNotifier {

	/**
	 * @param notifier which will be notified, whenever there is a booking request
	 *                 into the system.
	 */
	public void addRequestNotifier(IBookingRequestNotifier notifier);
	

}
