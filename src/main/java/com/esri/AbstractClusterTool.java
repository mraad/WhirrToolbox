package com.esri;

import com.esri.arcgis.datasourcesfile.DEFile;
import com.esri.arcgis.geodatabase.IGPMessages;
import com.esri.arcgis.geoprocessing.IGPEnvironmentManager;
import com.esri.arcgis.geoprocessing.IGPParameter;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.Array;
import com.esri.arcgis.system.IArray;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.whirr.cli.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 */
public abstract class AbstractClusterTool extends AbstractTool
{

    @Override
    public IArray getParameterInfo() throws IOException, AutomationException
    {
        final StringBuffer stringBuffer = new StringBuffer(System.getProperty("user.home"));
        stringBuffer.append(File.separator);
        final String prefix = stringBuffer.toString();

        final IArray parameters = new Array();

        addParamFile(parameters, "Cluster properties file", "in_file", prefix + "cluster.properties");

        return parameters;
    }

    @Override
    protected void doExecute(
            final IArray parameters,
            final IGPMessages messages,
            final IGPEnvironmentManager environmentManager) throws Exception
    {
        final String classPath = System.getProperty("java.class.path");
        if (classPath == null)
        {
            messages.addAbort("Cannot find system property java.class.path");
            return;
        }
        final int index = classPath.lastIndexOf("\\");
        if (index == -1)
        {
            messages.addAbort("Cannot find \\ in java.class.path system property");
            return;
        }
        final String extPath = classPath.substring(0, index) + File.separator + "ext";

        final DEFile propertiesFile = (DEFile) (((IGPParameter) (parameters.getElement(0))).getValue());
        final String propertiesPath = propertiesFile.getCatalogPath();

        final File[] files = getClasspathFiles(extPath);

        final String classpath = StringUtils.join(files, File.pathSeparatorChar);

        final String path = System.getProperty("java.home") + File.separatorChar + "bin" + File.separatorChar + "java";

        final ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath,
                Main.class.getName(),
                getCommand(),
                "--config", propertiesPath);
        final Process process = processBuilder.start();

        final BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        startErrorReader(errorReader, messages);

        final BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try
        {
            String line = inputReader.readLine();
            while (line != null)
            {
                messages.addMessage(line);
                line = inputReader.readLine();
            }
        }
        finally
        {
            IOUtils.closeQuietly(inputReader);
        }

        process.waitFor();
    }

    private void startErrorReader(
            final BufferedReader reader,
            final IGPMessages messages)
    {
        final Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    String line = reader.readLine();
                    while (line != null && !isInterrupted())
                    {
                        messages.addWarning(line);
                        line = reader.readLine();
                    }
                }
                catch (AutomationException e)
                {
                    System.err.println(e.toString());
                }
                catch (IOException e)
                {
                    System.err.println(e.toString());
                }
                finally
                {
                    IOUtils.closeQuietly(reader);
                }
            }
        };
        thread.start();
    }

    private File[] getClasspathFiles(final String extPath) throws IOException
    {
        final List<File> list = new ArrayList<File>();
        final InputStream stream = this.getClass().getResourceAsStream("/classpath.txt");
        if (stream != null)
        {
            try
            {
                final LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(stream));
                String line = lineNumberReader.readLine();
                while (line != null)
                {
                    list.add(new File(extPath + File.separator + line));
                    line = lineNumberReader.readLine();
                }
            }
            finally
            {
                stream.close();
            }
        }
        final File[] files = new File[list.size()];
        list.toArray(files);
        return files;
    }

    private File[] getFiles(
            final String extPath,
            final IGPMessages messages) throws IOException
    {
        final File file = new File(extPath);
        messages.addMessage(file.getAbsolutePath());
        return file.listFiles(new FileFilter()
        {
            public boolean accept(final File file)
            {
                return file.getName().endsWith(".jar");
            }
        });
    }

    protected abstract String getCommand();
}
