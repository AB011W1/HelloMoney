package com.barclays.ussd.auth.response;

import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseBody.
 */
public class ResponseBody {

	/** The user profile. */
	private UserProfile userProfile;

	/** The menu item dto. */
	private MenuItemDTO menuItemDTO;

	/**
     * Gets the user profile.
     * 
     * @return the user profile
     */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
     * Sets the user profile.
     * 
     * @param userProfile
     *            the new user profile
     */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
     * Gets the menu item dto.
     * 
     * @return the menu item dto
     */
	public MenuItemDTO getMenuItemDTO() {
		return menuItemDTO;
	}

	/**
     * Sets the menu item dto.
     * 
     * @param menuItemDTO
     *            the new menu item dto
     */
	public void setMenuItemDTO(MenuItemDTO menuItemDTO) {
		this.menuItemDTO = menuItemDTO;
	}



}
