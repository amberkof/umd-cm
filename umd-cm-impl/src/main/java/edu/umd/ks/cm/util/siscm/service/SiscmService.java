package edu.umd.ks.cm.util.siscm.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import edu.umd.ks.cm.util.siscm.dto.SisToCmImportCourseInfo;

/**
 * This service provides utilities to import and export data between SIS and UMDCM
 */
@WebService(name = "SiscmService", targetNamespace = "http://cm.ks.umd.edu/wsdl/siscm")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SiscmService {
	
    
    /**
     * 
     * This method will diff a course from SIS against a course in CM
     * <p>
     * Usage:
     * @param course
     * @param courseCd
     * @param startTerm
     * @return
     * @throws DataValidationErrorException
     * @throws Exception
     */
    public String diffCourse(@WebParam(name="course") SisToCmImportCourseInfo course, @WebParam(name="courseCd")String courseCd,@WebParam(name="startTerm")String startTerm, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, Exception;
    
    /**
     * 
     * Use this method to import a course from SCH that includes only the gened and core information.  This is
     * the only information we should be importing from SIS/SCH at the moment.  
     * 
     * @param course list of courses to import
     * @param batchJobId 
     * @param courseCd 
     * @param startTerm
     * @return String message stating what changed
     * @throws DataValidationErrorException
     * @throws Exception
     */
    public String importCourseGenedCoreDiversityOnly(@WebParam(name="course") SisToCmImportCourseInfo course,@WebParam(name="batchJobId")long batchJobId, @WebParam(name="courseCd")String courseCd,@WebParam(name="startTerm")String startTerm, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, Exception;
    
	/**
	 * @param courses list of sis data to load into UMDCM. all courses should have the same course code or this call will fail
	 * @return status if call is successful (should only return true or throw exception)
	 * @throws Exception 
	 */
    @Deprecated
	public boolean importCourse(@WebParam(name="course") SisToCmImportCourseInfo course,@WebParam(name="batchJobId")long batchJobId, @WebParam(name="courseCd")String courseCd,@WebParam(name="startTerm")String startTerm, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, Exception;

	/**
	 * Exports courses with the input course ids to the sis cm table
	 * @param courseIds
	 * @return status if call is successful (should only return true or throw exception)
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 */
	public boolean exportCourses(@WebParam(name="courseIds") List<String> courseIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

	/**
	 * Exports latest version of all courses to the sis cm table
	 * @return status if call is successful (should only return true or throw exception)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	public boolean exportAllCourses(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,	PermissionDeniedException;
	
	
	/**
	 * goes through all the active/retired/draft courses with that prefix and updates the curriculum oversight unit
	 * to the latest valid prefix unit relations
	 * @param prefixes
	 * @return true
	 * @throws MissingParameterException 
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 * @throws CircularReferenceException 
	 * @throws UnsupportedActionException 
	 * @throws DependentObjectsExistException 
	 * @throws CircularRelationshipException 
	 * @throws AlreadyExistsException 
	 * @throws VersionMismatchException 
	 * @throws DataValidationErrorException 
	 * @throws UnsupportedOperationException 
	 */
	public String updateCourseOrgsForPrefix(@WebParam(name="prefixes") String prefixes, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, DoesNotExistException, InvalidParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException, DataValidationErrorException, VersionMismatchException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, CircularReferenceException;
}
