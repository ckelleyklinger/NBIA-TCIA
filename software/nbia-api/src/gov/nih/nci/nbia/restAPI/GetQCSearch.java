package gov.nih.nci.nbia.restAPI;

import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import gov.nih.nci.nbia.util.SpringApplicationContext;
import gov.nih.nci.nbia.security.*;
import gov.nih.nci.nbia.util.NCIAConfig;
import gov.nih.nci.nbia.restUtil.JSONUtil;
import gov.nih.nci.nbia.dto.QcSearchResultDTO;
import gov.nih.nci.nbia.dto.QcSearchResultDTOLight;
import gov.nih.nci.nbia.dao.QcStatusDAO;
import gov.nih.nci.nbia.restUtil.QAUserUtil;

@Path("/getQCSearch")
public class GetQCSearch extends getData{

	/**
	 * This method get a set of all collection names
	 *
	 * @return String - set of collection names
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)

	public Response constructResponse(@FormParam("collectionSite") String collectionSite,
			                          @FormParam("released") String released,
			                          @FormParam("batch") String batch,
			                          @FormParam("confirmedComplete") String confirmedComplete,
			                          @FormParam("subjectIds") List<String> subjectIds,
			                          @FormParam("visibilities") List<String> visibilities,
			                          @FormParam("fromDate") String fromDate,
			                          @FormParam("toDate") String toDate) {
		String user = getUserName(); 

        if (!QAUserUtil.isUserQA(user)) {
        	System.out.println("Not QA User!!!!");
		    NCIASecurityManager sm = (NCIASecurityManager)SpringApplicationContext.getBean("nciaSecurityManager");
			 //  if (!sm.hasQaRole(user)) {
			 // 	  return Response.status(401)
			//			.entity("Insufficiant Privileges").build();
			 //  } else {
			//	   QAUserUtil.setUserQA(user);
			 //  }
        }
        String[] additionalQcFlagList = new String[3];
        additionalQcFlagList[0] = batch;
        if (confirmedComplete!=null&&confirmedComplete.equalsIgnoreCase("Complete")) {
        	confirmedComplete="yes";
        }
        additionalQcFlagList[1] = confirmedComplete;
        additionalQcFlagList[2] = released;
        String [] qcStatus = null;
        Date fromDateValue=null;
        Date toDateValue=null;
        if ((fromDate!=null&&!fromDate.equals(""))||(toDate!=null&&!toDate.equals(""))) {
        	if ((fromDate!=null&&!fromDate.equals(""))){
        		fromDateValue=getDate(fromDate);
        	} else {
        		fromDateValue=getDate("01-01-1980");
        	}
        	if ((toDate!=null&&!toDate.equals(""))){
        		toDateValue=getDate(toDate);
        	} else {
        		toDateValue=getDate(null);
        	}
        }
        QcStatusDAO qcStatusDAO = (QcStatusDAO)SpringApplicationContext.getBean("qcStatusDAO");
        List<String> collectionSites=new  ArrayList<String>();
        if (collectionSite==null||collectionSite.equals("")) {
        	return Response.status(500)
					.entity("collectionSite is required").build();
        } else {
        	collectionSites.add(collectionSite);
        }
        if (visibilities==null) {
        	return Response.status(500)
					.entity("visibilities is required").build();
        } else {
        	qcStatus=visibilities.toArray(new String[visibilities.size()]);
        }
        String[] subjectIdArray=null;
        if (subjectIds!=null) {
        	subjectIdArray=subjectIds.toArray(new String[0]);
        }
        String maxRows=NCIAConfig.getQctoolSearchResultsMaxNumberOfRows();
        List<QcSearchResultDTO> qsrDTOList = qcStatusDAO.findSeries(qcStatus, collectionSites, additionalQcFlagList, subjectIdArray, fromDateValue, toDateValue,  Integer.valueOf(maxRows));
        List<QcSearchResultDTOLight> qsrDTOListLight=new ArrayList<QcSearchResultDTOLight>();
		for (QcSearchResultDTO dto:qsrDTOList){
			QcSearchResultDTOLight dtol=new QcSearchResultDTOLight(dto);
			qsrDTOListLight.add(dtol);
		}

		return Response.ok(JSONUtil.getJSONforQCListLight(qsrDTOListLight)).type("application/json")
				.build();
	}

	private Date getDate(String date) {
		Date returnValue=null;

		if (date==null)
		{
			return Calendar.getInstance().getTime();
		}
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		try {
		returnValue=format.parse(date);
		} catch (Exception e) {
			format = new SimpleDateFormat("MM/dd/yyyy");
			try {
				returnValue=format.parse(date);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
		}
		return returnValue;
	}
}
