package com.bac.models.services;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.SyncFailedException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FileUploadService {
    private static final Set<String> ACCEPT_IMAGE_TYPES;

    static {
//        ACCEPT_IMAGE_TYPES = new String[]{"png", "pjp", "jpg", "pjpeg", "jpeg", "ifif"};
        ACCEPT_IMAGE_TYPES = new HashSet<>();
        ACCEPT_IMAGE_TYPES.add("png");
        ACCEPT_IMAGE_TYPES.add("pjp");
        ACCEPT_IMAGE_TYPES.add("jpg");
        ACCEPT_IMAGE_TYPES.add("pjpeg");
        ACCEPT_IMAGE_TYPES.add("jpeg");
        ACCEPT_IMAGE_TYPES.add("ifif");
    }

    public static String uploadFileImage(HttpServletRequest request) throws IOException {
        String admin = "vanbac";
        FileRenamePolicy policy = new FileRenamePolicy() {
            @Override
            public File rename(File f) {
                String substring = admin + "." + f.getName().substring(f.getName().lastIndexOf(".") + 1);
                return new File(f.getParentFile(),
                        System.nanoTime() + substring);
            }
        };

        String path = request.getServletContext().getRealPath("") + "shared\\images";

        MultipartRequest m = new MultipartRequest(request, path, 1024 * 1024, policy);
        String filename = m.getFilesystemName("File.Image");
        if (filename != null) {
            path = path + filename;
            String extensions = filename.substring(filename.lastIndexOf('.') + 1);
            if (ACCEPT_IMAGE_TYPES.contains(extensions)) {
                return path;
            } else {
                if (deleteFileWhileUploadImage(path)) {
                    return null;
                } else {
                    throw new SyncFailedException("Cannot delete file " + path);
                }
            }
        } else {
            return null;
        }
    }

    private static boolean deleteFileWhileUploadImage(String path) {
        File myObj = new File(path);
        return myObj.delete();
    }
}
