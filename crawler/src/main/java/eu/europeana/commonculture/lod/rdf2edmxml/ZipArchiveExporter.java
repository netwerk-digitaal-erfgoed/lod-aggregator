package eu.europeana.commonculture.lod.rdf2edmxml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

public class ZipArchiveExporter {
    FileOutputStream fos;
    ZipOutputStream zos;
    OutputStreamWriter writer;
    
    /**
     * Creates a new instance of this class.
     * @throws IOException 
     */
    public ZipArchiveExporter(File targetZipFile) throws IOException {
        fos = new FileOutputStream(targetZipFile);
        zos = new ZipOutputStream(fos);
        zos.setLevel(Deflater.BEST_COMPRESSION);
        writer=new OutputStreamWriter(zos, "UTF8");
    }
   
    public void addFolder(String name) throws IOException {
        try {
            writer.flush();
        } catch (ZipException e) {
            //fails on the first addition
        }
        if(!name.endsWith("/"))
            name+="/";
        ZipEntry zipEntry = new ZipEntry(name);
        zos.putNextEntry(zipEntry);
    }

    public void addFile(String name) throws IOException {
        try {
            writer.flush();
        } catch (ZipException e) {
            //fails on the first addition
        }
        ZipEntry zipEntry = new ZipEntry(name);
        zos.putNextEntry(zipEntry);
    }
    
    public OutputStreamWriter writer() {
        return writer;
    }
    
    public OutputStream outputStream() {
        return zos;
    }
    
    public void close() throws IOException {
        if(writer!=null) {
            writer.flush();
            writer.close();
            zos.close();
            fos.close();
            writer=null;
            zos=null;
            fos=null;
        }
    }
}
