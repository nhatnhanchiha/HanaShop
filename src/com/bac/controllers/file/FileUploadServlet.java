package com.bac.controllers.file;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nhatn
 */
@WebServlet(name = "FileUploadServlet", value = "/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {

    private Set<String> acceptImageTypes;
    private HashMap<String, String> handleAction;

    @Override
    public void init() throws ServletException {
        acceptImageTypes = new HashSet<>();
        acceptImageTypes.add("png");
        acceptImageTypes.add("pjp");
        acceptImageTypes.add("jpg");
        acceptImageTypes.add("pjpeg");
        acceptImageTypes.add("jpeg");
        acceptImageTypes.add("ifif");

        handleAction = new HashMap<>(2);
        handleAction.put("add-product", "AddingProductServlet");
        handleAction.put("edit-product", "EditingProductServlet");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");

        FileRenamePolicy policy = new FileRenamePolicy() {
            @Override
            public File rename(File f) {
                String substring = admin + "." + f.getName().substring(f.getName().lastIndexOf(".") + 1);
                return new File(f.getParentFile(),
                        System.nanoTime() + substring);
            }
        };
        String path = request.getServletContext().getRealPath("") + "shared\\images";

        MultipartRequest multi = new MultipartRequest(request, path,10*1024*1024, policy);

        String filename = multi.getFilesystemName("File.Image");
        if (filename != null) {
            path = path + filename;
            String extensions = filename.substring(filename.lastIndexOf('.') + 1);
            if (acceptImageTypes.contains(extensions)) {
                request.setAttribute("imageName", filename);
            } else {
                if (deleteFileWhileUploadImage(path)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
        }

        String action = multi.getParameter("action");
        if (action == null || !handleAction.containsKey(action)) {
            deleteFileWhileUploadImage(path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("multi", multi);
            RequestDispatcher rd = request.getRequestDispatcher(handleAction.get(action));
            rd.forward(request, response);
        }
    }

    private static boolean deleteFileWhileUploadImage(String path) {
        File myObj = new File(path);
        return myObj.delete();
    }
}
