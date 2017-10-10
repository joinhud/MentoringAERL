package io.swagger.api;

import io.swagger.model.AssignStudentsRequest;
import io.swagger.model.AssignStudentsResponse;
import io.swagger.model.GenerationStudentsNumber;
import io.swagger.model.ListOfIdRequest;
import io.swagger.model.ListOfIdResponse;
import io.swagger.model.StudentsListResponse;
import io.swagger.model.UpdateStudentsRequest;
import io.swagger.model.UpdateStudentsResponse;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-10T10:52:14.903Z")

@Controller
public class StudentsApiController implements StudentsApi {



    public ResponseEntity<AssignStudentsResponse> assignStudentsToUniversities(@ApiParam(value = "Assigning pairs" ,required=true )  @Valid @RequestBody AssignStudentsRequest body) {
        // do some magic!
        return new ResponseEntity<AssignStudentsResponse>(HttpStatus.OK);
    }

    public ResponseEntity<StudentsListResponse> findStudentsById(@ApiParam(value = "Students' IDs",required=true ) @PathVariable("parameter") List<Integer> parameter) {
        // do some magic!
        return new ResponseEntity<StudentsListResponse>(HttpStatus.OK);
    }

    public ResponseEntity<StudentsListResponse> generateStudentByCriteria(@ApiParam(value = "Criteria string",required=true ) @PathVariable("parameter") String parameter) {
        // do some magic!
        return new ResponseEntity<StudentsListResponse>(HttpStatus.OK);
    }

    public ResponseEntity<StudentsListResponse> generateStudentsByStudentsNumber(@ApiParam(value = "Number of students to be generated" ,required=true )  @Valid @RequestBody GenerationStudentsNumber body) {
        // do some magic!
        return new ResponseEntity<StudentsListResponse>(HttpStatus.OK);
    }

    public ResponseEntity<ListOfIdResponse> removeStudentsBySpecificIds(@ApiParam(value = "Students' IDs which should be deleted" ,required=true )  @Valid @RequestBody ListOfIdRequest body) {
        // do some magic!
        return new ResponseEntity<ListOfIdResponse>(HttpStatus.OK);
    }

    public ResponseEntity<UpdateStudentsResponse> updateStudents(@ApiParam(value = "Students' information" ,required=true )  @Valid @RequestBody UpdateStudentsRequest body) {
        // do some magic!
        return new ResponseEntity<UpdateStudentsResponse>(HttpStatus.OK);
    }

}
