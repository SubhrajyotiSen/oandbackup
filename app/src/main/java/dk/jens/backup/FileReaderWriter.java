package dk.jens.backup;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderWriter
{
    static final String TAG = OAndBackup.TAG;

    File file;

    FileReaderWriter(String absolutePath)
    {
        this.file = new File(absolutePath);
    }
    public FileReaderWriter(String rootDirectoryPath, String name)
    {
        this.file = new File(rootDirectoryPath, name);
    }

    public void putString(String string, boolean append)
    {
        if(string != null)
        {
            try
            {
                if(file != null)
                {
                    FileWriter fw = new FileWriter(file.getAbsoluteFile(), append);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(string + "\n");
                    bw.close();
                }
            }
            catch(IOException e)
            {
                Log.i(TAG, e.toString());
            }
        }
    }

    String read()
    {
        BufferedReader reader = null;
        try
        {
            FileReader fr = new FileReader(file);
            reader = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
        catch(FileNotFoundException e)
        {
            return e.toString();
        }
        catch(IOException e)
        {
            Log.i(TAG, e.toString());
            return e.toString();
        }
        finally
        {
            try
            {
                if(reader != null)
                {
                    reader.close();
                }
            }
            catch(IOException e)
            {
                return e.toString();
            }
        }
    }
    public boolean contains(String string)
    {
        String[] lines = read().split("\n");
        for(String line : lines)
        {
            if(string.equals(line.trim()))
            {
                return true;
            }
        }
        return false;
    }
    public void clear()
    {
        putString("", false);
    }

    public void rename(String newName)
    {
        if(file.exists())
        {
            File newFile = new File(file.getParent(), newName);
            boolean renamed = file.renameTo(newFile);
            if(renamed)
            {
                file = newFile;
            }
        }
    }

    public void delete()
    {
        file.delete();
    }
}
