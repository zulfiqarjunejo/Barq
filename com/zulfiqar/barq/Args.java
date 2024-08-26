package com.zulfiqar.barq;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(
            description = "Name of the config file. Default = BarqFile.json",
            names = {"--config", "-c"}
    )
    String configFile = "BarqFile.json";

    @Parameter(
            description = "Port on which to run the server. Default = 9090",
            names = {"--port", "-p"}
    )
    int port = 9090;
}
