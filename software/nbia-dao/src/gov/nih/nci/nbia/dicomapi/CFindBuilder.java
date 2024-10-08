/*  Copyright   2009 - IEETA
 *
 *  This file is part of Dicoogle.
 *
 *  Author: Luís A. Bastião Silva <bastiao@ua.pt>
 *
 *  Dicoogle is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Dicoogle is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Dicoogle.  If not, see <http://www.gnu.org/licenses/>.
 */

package gov.nih.nci.nbia.dicomapi;

//import pt.ua.dicoogle.core.exceptions.CFindNotSupportedException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
// import pt.ua.dicoogle.DebugManager;
//import pt.ua.dicoogle.core.ServerSettings;
//import pt.ua.dicoogle.sdk.Utils.TagValue;
//import pt.ua.dicoogle.sdk.Utils.TagsStruct;

/**
 *
 *  [SOP Class name] -  [SOP Class UID]
 *   Patient Root Q/R Find - 1.2.840.10008.5.1.4.1.2.1.1
 *   Study Root Q/R Find - 1.2.840.10008.5.1.4.1.2.2.1
 *   NOT SUPPORTED: @depracated
 *   Patient-Study Root Q/R Find (Retired) - 1.2.840.10008.5.1.4.1.2.3.1
 *
 *
 *
 * @author Luís A. Bastião Silva <bastiao@ua.pt>
 */
public class CFindBuilder
{

    private boolean patientRoot = false ;
    private boolean studyRoot = false ;

    private DICOMParameters query = null;
    
    static Logger log = LogManager.getLogger(CFindBuilder.class);
    public CFindBuilder(DicomObject key, DicomObject rsp) throws Exception
    {

        //if (!setRoot(rsp))
        //    throw new CFindNotSupportedException() ;

              
        /**
         * Sample output
        (0008,0005) CS #10 [ISO_IR 100] Specific Character Set
        (0008,0020) DA #18 [20090531-20090531] Study Date
        (0008,0030) TM #22 [000000.000-120000.000] Study Time
        (0008,0050) SH #0 [] Accession Number
        (0008,0052) CS #6 [STUDY] Query/Retrieve Level
        (0008,0061) CS #2 [XA] Modalities in Study
        (0008,1030) LO #0 [] Study Description
        (0010,0010) PN #0 [] Patient's Name
        (0010,0020) LO #0 [] Patient ID
        (0010,0030) DA #0 [] Patient's Birth Date
        (0020,000D) UI #0 [] Study Instance UID
        (0020,0010) SH #0 [] Study ID
        (0020,1208) IS #0 [] Number of Study Related Instances
         */

        /** Search by required fields

         *
         *
        */

        // Get Affected SOP Classs UID
        DicomElement elem = rsp.get(Integer.parseInt("00000002", 16));
        String affectedSOP = new String(elem.getBytes());
        log.info("affectedSOP:"+affectedSOP);


        //TagsStruct.getInstance().toStringNew();
        Hashtable <Integer, TagValue> t =  TagsStruct.getInstance().getDimFields() ;
        Set <Integer> mapKeys = t.keySet();
        Iterator<Integer> itKeys = mapKeys.iterator(); 

        boolean all=false ;
        String append = "" ;
        DICOMParameters params = new DICOMParameters();
        while(itKeys.hasNext())
        {
            /** Verify if this tags exists
            */
            int k = itKeys.next() ;
            DicomElement e = key.get(k);
            log.info("get key::"+ k );
            if (e!=null)
            {
                String value = new String(e.getBytes());
                log.info("Value getted in CFIND RP:<"+t.get(k).getAlias() + "> "+ value );
                if (value.equals(""))
                {
                	log.info("no value for"+t.get(k).getAlias());
                    continue ;
                }
                else if (t.get(k).getAlias().equals("PatientID"))
                {
                	params.setPatientID(value);
                }
                else if (t.get(k).getAlias().equals("PatientName"))
                {
                	params.setPatientName(value);
                }
                else if (t.get(k).getAlias().equals("InstitutionName"))
                {
                	params.setInstitutionName(value);
                }
                else if (t.get(k).getAlias().equals("AccessionNumber"))
                {
                	params.setAccessionNumber(value);
                }
                else if (t.get(k).getAlias().equals("StudyDate"))
                {
                	params.setStudyDate(value);
                }
                else if (t.get(k).getAlias().equals("PatientAge"))
                {
                	params.setPatientAge(value);
                }
                else if (t.get(k).getAlias().equals("StudyID"))
                {
                	params.setStudyID(value);
                }
                else if (t.get(k).getAlias().equals("StudyInstanceUID"))
                {
                	params.setStudyInstanceUID(value);
                }
                else if (t.get(k).getAlias().equals("StudyDescription"))
                {
                	params.setStudyDescription(value);
                }
                else if (t.get(k).getAlias().equals("SeriesInstanceUID"))
                {
                	params.setSeriesInstanceUID(value);
                }


            }
        }
        

        log.info(">> Query DICOM: "+ params.toString());
        query=params;

    }

    public DICOMParameters getQueryString()
            {
        return this.query;
    }

    private synchronized  boolean setRoot(DicomObject rsp)
    {
        /**
         * Verify if it is inside of:
         * Affected SOP Class UID (0000,0002)
         * Checked in C-FIND RQ in DICOM
         * It is one of three ([P|S|PS] Root )
         */

        DicomElement elem = rsp.get(Integer.parseInt("00000002", 16));
        String affectedSOP = new String(elem.getBytes());



        //DebugManager.getInstance().debug(">" + affectedSOP);
        //DebugManager.getInstance().debug(">> "+ServerSettings.getInstance().getSOPClass());
        


        boolean found = false;

        for (String i : ServerSettings.getInstance().getSOPClass().split("\\|"))
        {
            //DebugManager.getInstance().debug("It have in settings:>: " + i);

            if (affectedSOP.equals(i))
            {
                /**
                1.2.840.10008.5.1.4.1.2.1.1 (Patient)
                1.2.840.10008.5.1.4.1.2.2.1 (Study)
                 */
                //DebugManager.getInstance().debug(">>> Affected SOPs in ");
                if (affectedSOP.equals("1.2.840.10008.5.1.4.1.2.1.1"))
                {
                    this.patientRoot = true ;
                    found = true ;
                }
                else if (affectedSOP.equals("1.2.840.10008.5.1.4.1.2.2.1"))
                {
                    this.studyRoot = true ;
                    found = true  ;
                }
                break ;
            }
        }

        return found ;

    }



    /**
     * @return the patientRoot
     */
    public boolean isPatientRoot()
    {
        return patientRoot;
    }

    /**
     * @param patientRoot the patientRoot to set
     */
    public void setPatientRoot(boolean patientRoot)
    {
        this.patientRoot = patientRoot;
    }

    /**
     * @return the studyRoot
     */
    public boolean isStudyRoot()
    {
        return studyRoot;
    }

    /**
     * @param studyRoot the studyRoot to set
     */
    public void setStudyRoot(boolean studyRoot)
    {
        this.studyRoot = studyRoot;
    }

    /**
     * @return the query
     */
    public DICOMParameters getQuery()
    {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(DICOMParameters query)
    {
        this.query = query;
    }


    public static boolean isPatientRoot(DicomObject rsp)
    {
        DicomElement elem = rsp.get(Integer.parseInt("00000002", 16));
        String affectedSOP = new String(elem.getBytes());

        if (affectedSOP.equals("1.2.840.10008.5.1.4.1.2.2.1"))
        {
            return true;
        }
        return false;
    }

    public static boolean isStudyRoot(DicomObject rsp)
    {
        DicomElement elem = rsp.get(Integer.parseInt("00000002", 16));
        String affectedSOP = new String(elem.getBytes());
        if (affectedSOP.equals("1.2.840.10008.5.1.4.1.2.1.1"))
        {
            return true;
        }
        return false;
    }
    

}
