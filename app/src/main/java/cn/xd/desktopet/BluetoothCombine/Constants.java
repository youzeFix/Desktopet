package cn.xd.desktopet.BluetoothCombine;

/**
 * Created by LeeHam on 2017/5/6.
 */

public interface Constants {

    /**
     * Defines several constants used between {@link BluetoothChatService} and the UI.
     */

        // Message types sent from the BluetoothChatService Handler
        public static final int MESSAGE_STATE_CHANGE = 1;
        public static final int MESSAGE_READ = 2;
        public static final int MESSAGE_WRITE = 3;
        public static final int MESSAGE_DEVICE_NAME = 4;
        public static final int MESSAGE_TOAST = 5;

        // Key names received from the BluetoothChatService Handler
        public static final String DEVICE_NAME = "device_name";
        public static final String TOAST = "toast";

}

