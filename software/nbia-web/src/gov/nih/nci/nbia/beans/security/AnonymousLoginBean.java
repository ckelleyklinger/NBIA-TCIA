/*L
 *  Copyright SAIC, Ellumen and RSNA (CTP)
 *
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/national-biomedical-image-archive/LICENSE.txt for details.
 */

package gov.nih.nci.nbia.beans.security;

import gov.nih.nci.nbia.beans.BeanManager;
import gov.nih.nci.nbia.beans.searchform.SearchWorkflowBean;
import gov.nih.nci.nbia.util.MessageUtil;
import gov.nih.nci.nbia.util.NCIAConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * This is the Session scope bean that provides about anonymous loign information
 *
 * @author lethai
 */
public class AnonymousLoginBean {
	/**
     * Logger for class.
     */
    private static Logger logger = LogManager.getLogger(AnonymousLoginBean.class);

    private boolean isGuestEnabled = false;
    private boolean isExternalSearchLogin = false;
	private String guestUserName ="";
    //private String guestPassword ="";
    private boolean guestLoggedIn = false;

    public AnonymousLoginBean(){
    	String guestAccount  = NCIAConfig.getEnabledGuestAccount();
    	if(guestAccount.equalsIgnoreCase("yes")){
    		isGuestEnabled = true;
    	}else{
    		isGuestEnabled = false;
    	}
    	guestUserName  = NCIAConfig.getGuestUsername();
    	//guestPassword = NCIAConfig.getGuestPassword();
    }

    public boolean getIsGuestEnabled(){
    	return isGuestEnabled;
    }

    public String getGuestUserName(){
    	return guestUserName;
    }
    

   /* public String getGuestPassword(){
    	return guestPassword;
    }*/

    public String login() throws Exception {
    	SecurityBean securityBean = BeanManager.getSecurityBean();
    	logger.debug("anonymousloginbean............ guestusername: " + guestUserName );
    	guestLoggedIn = true;
    	String loginStatus = securityBean.login(guestUserName, "");
    	if(loginStatus != null && loginStatus.equals("loginFail")){
    		guestLoggedIn = false;
    		return loginStatus;
    	}
    	SearchWorkflowBean swfb = BeanManager.getSearchWorkflowBean();
    	return swfb.newSimpleSearch();
    }
    
	public String externalBypassLogin(String collectionName, String patientID) throws Exception {
		SecurityBean securityBean = BeanManager.getSecurityBean();
		logger.debug("anonymousloginbean/externalBypassLogin............ guestusername: " + guestUserName);
		guestLoggedIn = true;
		String loginStatus = securityBean.login(guestUserName, "");
		if (loginStatus != null && loginStatus.equals("loginFail")) {
			guestLoggedIn = false;
			return loginStatus;
		}
		SearchWorkflowBean swfb = BeanManager.getSearchWorkflowBean();
		String action = swfb.externalSimpleSearch(collectionName, patientID);

		if (action.equals("externalSearchLogin")) {
			guestLoggedIn = false;
			securityBean.setLoginFailure(false);
			securityBean.setUsername(null);
			securityBean.setLoggedIn(false);
			isExternalSearchLogin = true;
			MessageUtil.addErrorMessage("MAINbody:loginForm:pass", "patientNoFoundInPublicDomain");
		}

		return action;
	}
    
    public boolean getIsExternalSearchLogin() {
		return isExternalSearchLogin;
	}

	public void setIsExternalSearchLogin(boolean isExternalSearchLogin) {
		this.isExternalSearchLogin = isExternalSearchLogin;
	}

    
    public boolean getGuestLoggedIn() {
    	return guestLoggedIn;
    }

    public void setGuestLoggedIn(boolean loggedIn){
    	guestLoggedIn = loggedIn;
    }

    public void logout(){
    	guestLoggedIn = false;
    }

    private boolean loginLink = false;
	public void loginLink(){
		if(!loginLink){
			loginLink = true;
		}else{
			loginLink = false;
		}
	}
	public boolean getLoginLink(){
		return loginLink;
	}

	private boolean simpleSearchView = false;

	/* show the view for simpleSearch Link on the home page
	 * when isGuestEnabled is true and user hasn't logged in.
	 * Use anonymousLoginBean.login
	 * When they logged in either as guest or registered user,
	 * use workflowbean.simpleSearch action
	 */
	public boolean getSimpleSearchView(){
		SecurityBean sb = BeanManager.getSecurityBean();
		if(isGuestEnabled && !guestLoggedIn && !sb.getLoggedIn()){
			simpleSearchView = true;
		}else{
			simpleSearchView = false;
		}
		return simpleSearchView;
	}
}