package com.esri;

import com.esri.arcgis.interop.AutomationException;

import java.io.IOException;

/**
 */
public class DestroyClusterTool extends AbstractClusterTool
{
    public final static String NAME = DestroyClusterTool.class.getSimpleName();

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

    @Override
    protected String getCommand()
    {
        return "destroy-cluster";
    }

}
