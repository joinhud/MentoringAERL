package io.swagger.api;

import io.swagger.model.AddNewUniversityRequest;
import io.swagger.model.AddNewUniversityResponse;
import io.swagger.model.GetNotAssignedStudentsResponse;
import io.swagger.model.GetStudentsOfUniversityResponse;
import io.swagger.model.SetUniversityStatusRequest;
import io.swagger.model.SetUniversityStatusResponse;

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
public class UniversitiesApiController implements UniversitiesApi {



    public ResponseEntity<AddNewUniversityResponse> addNewUniversity(@ApiParam(value = "University information" ,required=true )  @Valid @RequestBody AddNewUniversityRequest body) {
        // do some magic!
        return new ResponseEntity<AddNewUniversityResponse>(HttpStatus.OK);
    }

    public ResponseEntity<GetNotAssignedStudentsResponse> getNotAssignedStudents() {
        // do some magic!
        return new ResponseEntity<GetNotAssignedStudentsResponse>(HttpStatus.OK);
    }

    public ResponseEntity<GetStudentsOfUniversityResponse> getStudentsOfUniversity(@ApiParam(value = "ID of university",required=true ) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<GetStudentsOfUniversityResponse>(HttpStatus.OK);
    }

    public ResponseEntity<SetUniversityStatusResponse> setUniversityStatus(@ApiParam(value = "Statuses with usiversities" ,required=true )  @Valid @RequestBody SetUniversityStatusRequest body) {
        // do some magic!
        return new ResponseEntity<SetUniversityStatusResponse>(HttpStatus.OK);
    }

}
