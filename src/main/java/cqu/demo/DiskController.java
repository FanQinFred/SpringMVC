package cqu.demo;

import cqu.ioc.annotation.Autowire;
import cqu.ioc.annotation.Controller;
import cqu.ioc.annotation.RequestMapping;
import cqu.ioc.annotation.RequestParam;
import cqu.mvc.model.UserDao;
import cqu.mvc.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/disk")
public class DiskController {

    @RequestMapping(value = "/show", method = "GET")
    public String showGET(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        return "disk";
    }

    @RequestMapping(value = "/upload", method = "POST")
    public String uploadPOST(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(req.getInputStream());
        String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("/")).getPath();
        String filepath = classpath.replace("WEB-INF/classes", "WEB-INF/file");
        File dir = new File(filepath);
        System.out.println(filepath);
        File file = new File(filepath + "/ttt.txt");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        while (bufferedInputStream.read(buffer) != -1) {
            fileOutputStream.write(buffer);
        }
        fileOutputStream.close();
        bufferedInputStream.close();
        return "disk";
    }


}