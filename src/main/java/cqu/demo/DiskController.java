package cqu.demo;

import cqu.ioc.annotation.Controller;
import cqu.ioc.annotation.RequestMapping;
import cqu.mvc.model.FileDao;
import cqu.mvc.model.FileEntity;
import cqu.mvc.model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.Part;

/**
 * 处理展示文件和上传文件的控制类
 */
@Controller
@RequestMapping("/disk")
public class DiskController {

    @RequestMapping(value = "/show", method = "GET")
    public String showGET(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
        if(!flag) return "redirect:http://localhost:8080/user/login";
        List<FileEntity> fileEntityList = FileDao.getFileByUser(username);
        System.out.println("fileEntityList: "+fileEntityList);
        List<String> fileNameList = new ArrayList<>();
        for (FileEntity fileEntity:
             fileEntityList) {
            fileNameList.add(fileEntity.getName());
        }
        System.out.println("fileNameList: "+fileNameList);
        req.setAttribute("Files",fileNameList);
        return "disk";
    }

    @RequestMapping(value = "/upload", method = "POST")
    public String uploadPOST(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
        String fileName=null;
        try {

            Part part= req.getPart("file");
            String fileHeader=part.getHeader("content-disposition");
            fileName=fileHeader.substring(fileHeader.indexOf("filename=")+10, fileHeader.lastIndexOf("\""));
            fileName=fileName.replace(' ','_');
            String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("/")).getPath();
            String filepath = classpath.replace("WEB-INF/classes", "WEB-INF/file");
            String usrpath = classpath.replace("WEB-INF/classes", "WEB-INF/file/" + username);
            File dir = new File(filepath);
            File usrdir = new File(usrpath);
            if (!dir.exists()) dir.mkdir();
            if (!usrdir.exists()) usrdir.mkdir();
            part.write(usrpath + File.separator + fileName);
            FileDao.addFile(username+fileName,fileName,UserDao.getUserByName(username));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:http://localhost:8080/disk/show";

    }


}