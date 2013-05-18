package com.esri;

import com.esri.arcgis.datasourcesfile.DEFile;
import com.esri.arcgis.datasourcesfile.DEFileType;
import com.esri.arcgis.geodatabase.IGPMessages;
import com.esri.arcgis.geoprocessing.BaseGeoprocessingTool;
import com.esri.arcgis.geoprocessing.GPParameter;
import com.esri.arcgis.geoprocessing.GPString;
import com.esri.arcgis.geoprocessing.GPStringType;
import com.esri.arcgis.geoprocessing.IGPEnvironmentManager;
import com.esri.arcgis.geoprocessing.esriGPParameterDirection;
import com.esri.arcgis.geoprocessing.esriGPParameterType;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IArray;
import com.esri.arcgis.system.IName;
import com.esri.arcgis.system.ITrackCancel;

import java.io.IOException;

/**
 */
public abstract class AbstractTool extends BaseGeoprocessingTool
{
    private final static WhirrToolFunctionFactory FACTORY = new WhirrToolFunctionFactory();

    @Override
    public IName getFullName() throws IOException, AutomationException
    {
        return (IName) FACTORY.getFunctionName(getName());
    }

    @Override
    public boolean isLicensed() throws IOException, AutomationException
    {
        return true;
    }

    @Override
    public void updateMessages(
            final IArray parameters,
            final IGPEnvironmentManager environmentManager,
            final IGPMessages messages)
    {
    }

    @Override
    public void updateParameters(
            final IArray parameters,
            final IGPEnvironmentManager environmentManager)
    {
    }

    @Override
    public String getMetadataFile() throws IOException, AutomationException
    {
        return null;
    }

    @Override
    public void execute(
            final IArray parameters,
            final ITrackCancel trackCancel,
            final IGPEnvironmentManager environmentManager,
            final IGPMessages messages) throws IOException, AutomationException
    {
        try
        {
            // Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
            doExecute(parameters, messages, environmentManager);
        }
        catch (Throwable t)
        {
            messages.addAbort(t.toString());
            for (final StackTraceElement stackTraceElement : t.getStackTrace())
            {
                messages.addAbort(stackTraceElement.toString());
            }
        }
    }

    protected void addParamString(
            final IArray parameters,
            final String displayName,
            final String name,
            final String value) throws IOException
    {
        final GPParameter parameter = new GPParameter();
        parameter.setDirection(esriGPParameterDirection.esriGPParameterDirectionInput);
        parameter.setDisplayName(displayName);
        parameter.setName(name);
        parameter.setParameterType(esriGPParameterType.esriGPParameterTypeRequired);
        parameter.setDataTypeByRef(new GPStringType());
        final GPString gpString = new GPString();
        gpString.setValue(value);
        parameter.setValueByRef(gpString);
        parameters.add(parameter);
    }

    protected void addParamFile(
            final IArray parameters,
            final String displayName,
            final String name,
            final String catalogPath
    ) throws IOException
    {
        final GPParameter parameter = new GPParameter();
        parameter.setDirection(esriGPParameterDirection.esriGPParameterDirectionInput);
        parameter.setDisplayName(displayName);
        parameter.setName(name);
        parameter.setParameterType(esriGPParameterType.esriGPParameterTypeRequired);
        parameter.setDataTypeByRef(new DEFileType());
        final DEFile file = new DEFile();
        file.setCatalogPath(catalogPath);
        parameter.setValueByRef(file);
        parameters.add(parameter);
    }

    protected abstract void doExecute(
            final IArray parameters,
            final IGPMessages messages,
            final IGPEnvironmentManager environmentManager) throws Throwable;

}
