package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Logging.LogObserver;
import edu.saddleback.cs4b.Backend.Logging.LogSubject;
import edu.saddleback.cs4b.Backend.Logging.ServerLog;

public class ServerScreenController implements LogObserver {
    private LogSubject logger = ServerLog.getLogger();

    ServerScreenController() {
        logger.registerObserver(this);
    }

    @Override
    public void update(String msg) {

    }
}
