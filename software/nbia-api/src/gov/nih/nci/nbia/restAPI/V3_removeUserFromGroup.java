//To Test: http://localhost:8080/nbia-auth/services/v3/removeUserFromPG?loginName=authTest&PGName=NCIA.Test

package gov.nih.nci.nbia.restAPI;

import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/v3/removeUserFromGroup")
public class V3_removeUserFromGroup extends getData{
	@Context private HttpServletRequest httpRequest;

	/**
	 * This method remove an user to a user group
	 *
	 * @return String - the status of operation 
	 */
	@POST
	@Produces({MediaType.APPLICATION_JSON})

	public Response  constructResponse(@QueryParam("loginName") String loginName, @QueryParam("groupName") String groupName) {
		try {
			UserProvisioningManager upm = getUpm();
			//getProtection using protection group name
			Group group = getGroupByGroupName(groupName);
			User user = getUserByLoginName(loginName);
			upm.removeUserFromGroup(group.getGroupId().toString(), user.getUserId().toString());
		} catch (CSConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok("Submited the removal request.").type("application/json").build();
	}	
}
