package com.esri;

import com.esri.arcgis.geodatabase.IEnumGPName;
import com.esri.arcgis.geodatabase.IGPName;
import com.esri.arcgis.geoprocessing.EnumGPName;
import com.esri.arcgis.geoprocessing.GPFunctionName;
import com.esri.arcgis.geoprocessing.IEnumGPEnvironment;
import com.esri.arcgis.geoprocessing.IGPFunction;
import com.esri.arcgis.geoprocessing.IGPFunctionFactory;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.interop.extn.ArcGISCategories;
import com.esri.arcgis.interop.extn.ArcGISExtension;
import com.esri.arcgis.system.IUID;
import com.esri.arcgis.system.UID;

import java.io.IOException;
import java.util.UUID;

/**
 */
@ArcGISExtension(categories = {ArcGISCategories.GPFunctionFactories})
public class WhirrToolFunctionFactory implements IGPFunctionFactory
{

    private static final long serialVersionUID = 1L;

    private static final String NAME = "WhirrToolbox";

    public IUID getCLSID() throws IOException, AutomationException
    {
        final UID uid = new UID();
        uid.setValue("{" + UUID.nameUUIDFromBytes(this.getClass().getName().getBytes()) + "}");
        return uid;
    }

    public String getName() throws IOException, AutomationException
    {
        return NAME;
    }

    public String getAlias() throws IOException, AutomationException
    {
        return NAME;
    }

    public IGPFunction getFunction(final String s) throws IOException, AutomationException
    {
        if (ClusterPropertiesTool.NAME.equalsIgnoreCase(s))
        {
            return new ClusterPropertiesTool();
        }
        if (DestroyClusterTool.NAME.equalsIgnoreCase(s))
        {
            return new DestroyClusterTool();
        }
        if (LaunchClusterTool.NAME.equalsIgnoreCase(s))
        {
            return new LaunchClusterTool();
        }
        return null;
    }

    public IGPName getFunctionName(final String s) throws IOException, AutomationException
    {
        if (ClusterPropertiesTool.NAME.equalsIgnoreCase(s))
        {
            final GPFunctionName functionName = new GPFunctionName();
            functionName.setCategory(ClusterPropertiesTool.NAME);
            functionName.setDescription(ClusterPropertiesTool.NAME);
            functionName.setDisplayName(ClusterPropertiesTool.NAME);
            functionName.setName(ClusterPropertiesTool.NAME);
            functionName.setFactoryByRef(this);
            return functionName;
        }
        if (DestroyClusterTool.NAME.equalsIgnoreCase(s))
        {
            final GPFunctionName functionName = new GPFunctionName();
            functionName.setCategory(DestroyClusterTool.NAME);
            functionName.setDescription(DestroyClusterTool.NAME);
            functionName.setDisplayName(DestroyClusterTool.NAME);
            functionName.setName(DestroyClusterTool.NAME);
            functionName.setFactoryByRef(this);
            return functionName;
        }
        if (LaunchClusterTool.NAME.equalsIgnoreCase(s))
        {
            final GPFunctionName functionName = new GPFunctionName();
            functionName.setCategory(LaunchClusterTool.NAME);
            functionName.setDescription(LaunchClusterTool.NAME);
            functionName.setDisplayName(LaunchClusterTool.NAME);
            functionName.setName(LaunchClusterTool.NAME);
            functionName.setFactoryByRef(this);
            return functionName;
        }
        return null;
    }

    public IEnumGPName getFunctionNames() throws IOException, AutomationException
    {
        final EnumGPName nameArray = new EnumGPName();
        nameArray.add(getFunctionName(LaunchClusterTool.NAME));
        nameArray.add(getFunctionName(DestroyClusterTool.NAME));
        nameArray.add(getFunctionName(ClusterPropertiesTool.NAME));
        return nameArray;
    }

    public IEnumGPEnvironment getFunctionEnvironments() throws IOException, AutomationException
    {
        return null;
    }
}