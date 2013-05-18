package com.esri;

import com.esri.arcgis.interop.AutomationException;

import java.io.IOException;

/**
 */
public class LaunchClusterTool extends AbstractClusterTool
{
    public final static String NAME = LaunchClusterTool.class.getSimpleName();

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
        return "launch-cluster";
    }

}
