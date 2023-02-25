package com.example.demoweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/hi")
public class HiController {

    /**
     * view1 [/hi/1]
     * @return
     */
    @RequestMapping(path = "/1", method = RequestMethod.GET)
    public String sayHi() {
        System.out.println("this.toString() = " + this.toString());
        System.out.println("DemoWebApplication.sayHi");
        return "index.html";
    }


    /**
     * [/hi/2]
     * @return
     */
    @RequestMapping(path = "/2", method = RequestMethod.GET)
    @ResponseBody//application/json; charset=utf-8 | REST
    public Map<String, Object> sayHi2() {
        System.out.println("sayHi2:this.toString() = " + this.toString());
        System.out.println("DemoWebApplication.sayHi");
        Map<String, Object> data = new HashMap<>();
        data.put("msg", "hi hi 2,");
        return data;
    }

    @RequestMapping(path = "/4", method = RequestMethod.GET)
    @ResponseBody//application/json; charset=utf-8
    public Map<String, Object> sayHi4() {
        System.out.println("sayHi2:this.toString() = " + this.toString());
        System.out.println("DemoWebApplication.sayHi");
        Map<String, Object> data = new HashMap<>();
        data.put("msg", "hi hi 4,");
        return data;
    }




    /**
     * view1 [/hi/3]
     * @return
     */
    @RequestMapping(path = "/3", method = RequestMethod.GET)
    public String sayView2() {
        System.out.println("this.toString() = " + this.toString());
        System.out.println("DemoWebApplication.sayHi");
        return "index1.html";
    }

}
