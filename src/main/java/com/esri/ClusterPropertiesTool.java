package com.esri;

import com.esri.arcgis.datasourcesfile.DEFile;
import com.esri.arcgis.geodatabase.IGPMessages;
import com.esri.arcgis.geoprocessing.GPString;
import com.esri.arcgis.geoprocessing.IGPEnvironmentManager;
import com.esri.arcgis.geoprocessing.IGPParameter;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.Array;
import com.esri.arcgis.system.IArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

/**
 */
public class ClusterPropertiesTool extends AbstractTool
{
    public final static String NAME = ClusterPropertiesTool.class.getSimpleName();

    @Override
    protected void doExecute(
            final IArray parameters,
            final IGPMessages messages,
            final IGPEnvironmentManager environmentManager) throws Throwable
    {
        final DEFile clusterFile = (DEFile) (((IGPParameter) (parameters.getElement(0))).getValue());
        final String clusterText = clusterFile.getCatalogPath();

        final GPString hadoopText = (GPString) (((IGPParameter) (parameters.getElement(1))).getValue());

        final Properties clusterProperties = loadClusterProperties(clusterText);
        final String clusterName = clusterProperties.getProperty("whirr.cluster-name");
        if (clusterName == null)
        {
            messages.addAbort("Key 'whirr.cluster-name' is missing !");
        }
        else
        {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(System.getProperty("user.home")).
                    append(File.separator).
                    append(".whirr").
                    append(File.separator).
                    append(clusterName).
                    append(File.separator).
                    append("hadoop-site.xml");
            final File file = new File(stringBuilder.toString());
            if (file.exists())
            {
                final Properties hadoopProperties = loadXMLFile(file);
                final OutputStream outputStream = new FileOutputStream(hadoopText.getAsText());
                try
                {
                    hadoopProperties.store(outputStream, clusterText);
                }
                finally
                {
                    outputStream.close();
                }
            }
            else
            {
                messages.addAbort("Cannot find " + file.getAbsolutePath());
            }
        }
    }

    private Properties loadXMLFile(final File file) throws ParserConfigurationException, IOException, SAXException
    {
        final Properties properties = new Properties();
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        final NodeList nodeList = document.getElementsByTagName("property");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            final Node node = nodeList.item(i);
            final Element element = (Element) node;
            final String name = element.getElementsByTagName("name").item(0).getTextContent();
            final String value = element.getElementsByTagName("value").item(0).getTextContent();
            properties.put(name, value);
        }
        return properties;
    }

    private Properties loadClusterProperties(final String clusterText) throws IOException
    {
        final Properties clusterProperties = new Properties();
        final Reader reader = new FileReader(clusterText);
        try
        {
            clusterProperties.load(reader);
        }
        finally
        {
            reader.close();
        }
        return clusterProperties;
    }

    @Override
    public IArray getParameterInfo() throws IOException, AutomationException
    {
        final StringBuffer stringBuffer = new StringBuffer(System.getProperty("user.home"));
        stringBuffer.append(File.separator);
        final String prefix = stringBuffer.toString();

        final IArray parameters = new Array();

        addParamFile(parameters, "Cluster properties file", "in_cluster", prefix + "cluster.properties");
        addParamString(parameters, "Hadoop properties files", "in_hadoop", prefix + "hadoop.properties");

        return parameters;
    }

    @Override
    public String getName() throws IOException, AutomationException
    {
        return NAME;
    }

    @Override
    public String getDisplayName() throws IOException, AutomationException
    {
        return NAME;
    }
}
