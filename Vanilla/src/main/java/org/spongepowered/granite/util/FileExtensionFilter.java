package org.spongepowered.granite.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileExtensionFilter implements FilenameFilter {

    private final String extension;

    public FileExtensionFilter(String extension) {
        this.extension = '.' + extension;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(this.extension);
    }

}
