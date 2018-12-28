package com.felink.workflow;

import java.io.File;

public class Path {
    private static String ABSOLUTE_PATH = new File("").getAbsolutePath();
    public static String TEMP_PATH = Path.ABSOLUTE_PATH + "\\src\\main\\temp\\";
}
