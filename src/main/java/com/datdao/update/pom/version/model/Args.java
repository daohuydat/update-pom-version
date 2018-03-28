/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datdao.update.pom.version.model;

/**
 *
 * @author datdh
 */
public class Args {
    private int level;
    private String path;
    private String version;

    public Args() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Args{" + "level=" + level + ", path=" + path + ", version=" + version + '}';
    }
    
    
}
