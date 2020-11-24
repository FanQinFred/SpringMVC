package cqu.demo;

import cqu.ioc.annotation.Autowire;
import cqu.ioc.annotation.Controller;
import cqu.ioc.annotation.RequestMapping;
import cqu.ioc.annotation.RequestParam;
import cqu.mvc.model.UserDao;
import cqu.mvc.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import javax.servlet.http.Part;

@Controller
@RequestMapping("/disk")
public class DiskController {

    @RequestMapping(value = "/show", method = "GET")
    public String showGET(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        return "disk";
    }

    @RequestMapping(value = "/upload", method = "POST")
    public String uploadPOST(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("12312312312" + req.getParameter("username"));
        Cookie []cookies = req.getCookies();
        boolean flag = false;
        String username = "";
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("uploader")) {
                flag = true;
                username = cookie.getValue();
                break;
            }
        }
        if(!flag)
            return "redirect:http://localhost:8080/user/login";
        String fileName=req.getParameter("name");
        System.out.println("fileName= "+fileName);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(req.getInputStream());
        String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("/")).getPath();
        String filepath = classpath.replace("WEB-INF/classes", "WEB-INF/file");
        String usrpath = classpath.replace("WEB-INF/classes", "WEB-INF/file/" + username);
        File dir = new File(filepath);
        File usrdir = new File(usrpath);
        System.out.println(filepath);
        File file = new File(usrpath + "/"+fileName);
        if (!dir.exists()) dir.mkdir();
        if (!usrdir.exists()) usrdir.mkdir();
        if (!file.exists()) file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        while (bufferedInputStream.read(buffer) != -1) {
            fileOutputStream.write(buffer);
        }
        fileOutputStream.close();
        bufferedInputStream.close();
        return "disk";
//        Part filePart = req.getPart("file");
//        String filename = filePart.getName();
//        System.out.println(filename);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(filePart.getInputStream());
//        String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("/")).getPath();
//        String filepath = classpath.replace("WEB-INF/classes", "WEB-INF/file");
//        String usrpath = classpath.replace("WEB-INF/classes", "WEB-INF/file/" + username);
//        File dir = new File(filepath);
//        File usrdir = new File(usrpath);
//        System.out.println(filepath);
//        File file = new File(usrpath + "/"+filename);
//        if (!dir.exists()) dir.mkdir();
//        if (!usrdir.exists()) usrdir.mkdir();
//        if (!file.exists()) file.createNewFile();
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        byte[] buffer = new byte[1024];
//        while (bufferedInputStream.read(buffer) != -1) {
//            fileOutputStream.write(buffer);
//        }
//        fileOutputStream.close();
//        bufferedInputStream.close();
//
//        return "disk";
    }


}