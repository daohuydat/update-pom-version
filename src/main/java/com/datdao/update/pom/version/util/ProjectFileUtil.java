/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datdao.update.pom.version.util;

import com.datdao.update.pom.version.model.VersionPosition;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

/**
 *
 * @author datdh
 */
public class ProjectFileUtil {

    static final List<String> ignoreDirs
            = Arrays.asList("src", "node_modules", "npm", "target", "docs", "scripts", "sql", "static");

    public static List<File> listPomFiles(File prjDir) {
        List<File> pomFiles = new ArrayList<>();
        for (File f : prjDir.listFiles()) {
            if (f.isFile()) {
                if ("pom.xml".equals(f.getName())) {
                    pomFiles.add(f);
                }
            } else {
                if (!ignoreDirs.contains(f.getName()) && !f.getName().startsWith(".")) {
                    pomFiles.addAll(listPomFiles(f));
                }
            }

        }
        return pomFiles;
    }

    public static void updatePomVersion(File file, String version) throws Exception {
        byte[] readAllBytes = Files.readAllBytes(file.toPath());
        String content = new String(readAllBytes);
        int projectTagIdx = content.indexOf("<project");
        int parentTagIdx = -1;
        int parentTagEndIdx = -1;
        int versionTagIdx = -1;
        int versionTagEndIdx = -1;
        VersionPosition position = versionPosition(file);
        switch (position) {
            case ONLY_INSIDE_PROJECT_TAG:
                versionTagIdx = content.indexOf("<version>", projectTagIdx);
                versionTagEndIdx = content.indexOf("</version>", versionTagIdx);
                String res1 = buildNewContent(content, version, versionTagIdx, versionTagEndIdx);
                System.out.println(res1);
                Files.write(file.toPath(), res1.getBytes());
                break;
            case ONLY_INSIDE_PARENT_TAG:
                parentTagIdx = content.indexOf("<parent", projectTagIdx);
                parentTagEndIdx = content.indexOf("</parent>", parentTagIdx);
                versionTagIdx = content.indexOf("<version>", parentTagIdx);
                versionTagEndIdx = content.indexOf("</version>", versionTagIdx);
                String res2 = buildNewContent(content, version, versionTagIdx, versionTagEndIdx);
                System.out.println(res2);
                Files.write(file.toPath(), res2.getBytes());
                break;
        }
    }

    public static VersionPosition versionPosition(File file) throws Exception {
        Document xmlDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new FileInputStream(file));

        String exp1 = "/project/version";
        String projectTagVersion = (String) XPathFactory.newInstance().newXPath()
                .compile(exp1).evaluate(xmlDocument, XPathConstants.STRING);

        String exp2 = "/project/parent/version";
        String parentTagVersion = (String) XPathFactory.newInstance().newXPath()
                .compile(exp2).evaluate(xmlDocument, XPathConstants.STRING);

        if (!projectTagVersion.isEmpty() && !parentTagVersion.isEmpty()) {
            return VersionPosition.INSIDE_PROJECT_AND_PARENT_TAG;
        }
        if (!projectTagVersion.isEmpty() && parentTagVersion.isEmpty()) {
            return VersionPosition.ONLY_INSIDE_PROJECT_TAG;
        }
        if (projectTagVersion.isEmpty() && !parentTagVersion.isEmpty()) {
            return VersionPosition.ONLY_INSIDE_PARENT_TAG;
        }

        return VersionPosition.INVALID;
    }

    public static String buildNewContent(String content, String version, int versionTagIdx, int versionTagEndIdx) {
        return content.substring(0, versionTagIdx + 9) + version + content.substring(versionTagEndIdx);
    }
}
