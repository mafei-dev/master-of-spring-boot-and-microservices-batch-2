package com.example.demoweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    ///employee  [GET get all the emp details]
    @RequestMapping(path = "/",method = RequestMethod.GET)
    public List<?> getAllEmps(){
        return null;
    }

    @RequestMapping(path = "/{id} ",method = RequestMethod.GET)
    public List<?> getEmpById(@PathVariable String id){
        return null;
    }


    /*
/employee  [GET get all the emp details]
/employee/{id} [GET get the emp details]
/employee/create [POST creat a new emp]
/employee/update [UPDATE update ex emp]
*/
}
