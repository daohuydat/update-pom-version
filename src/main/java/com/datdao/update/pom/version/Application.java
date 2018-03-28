/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datdao.update.pom.version;

import com.datdao.update.pom.version.model.Args;
import com.datdao.update.pom.version.util.ProjectFileUtil;
import java.io.File;
import java.util.List;

/**
 *
 * @author datdh
 */
public class Application {

    public static void main(String[] args) {

        Args arguments = getArgs();
        File pathToProject = new File(arguments.getPath());
        if (pathToProject.exists() && pathToProject.isDirectory()) {

            List<File> pomFiles = ProjectFileUtil.listPomFiles(pathToProject);
            pomFiles.forEach(pom -> {
                System.out.println("Processing file: " + pom.getAbsolutePath());
                try {
                    
                    ProjectFileUtil.updatePomVersion(pom, arguments.getVersion());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } else {
            System.out.println("No such directory: " + arguments.getPath());
        }

    }

    public static Args getArgs() {
        Args args = new Args();
//        String level = System.getProperty("pom.level");
        String version = System.getProperty("pom.version");
        String path = System.getProperty("pom.path");
        if (version == null) {
            throw new IllegalArgumentException("Version must be set!");
        } else {
            args.setVersion(version);
        }
        
        if (path == null) {
            args.setPath(System.getProperty("user.dir"));
            System.out.println("Change pom version inside: " + System.getProperty("user.dir"));
        } else {
            args.setPath(path);
        }
        return args;
    }
}
