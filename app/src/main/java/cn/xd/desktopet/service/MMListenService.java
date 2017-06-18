package cn.xd.desktopet.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import cn.xd.desktopet.control.MyWindowManager;
import cn.xd.desktopet.control.PetControl;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

/**
 * Created by songzhixin on 2017/4/16.
 */


public class MMListenService extends AccessibilityService {

    public static boolean isRunning=false;

    private AccessibilityNodeInfo mListView;
    private AccessibilityNodeInfo mMoneyBigNode;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(!isRunning){
            stopSelf();
        }else {
            int eventType = event.getEventType();
            Log.w(TAG, "onAccessibilityEvent: " + "***in");

            switch (eventType) {
                case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:

                    Log.w("我是微信消息", "onAccessibilityEvent: " + "     in");
                    handleNotification(event);
                    break;

                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:

                    String className = event.getClassName().toString();
                    if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                        findMoneyAndOpen(event);
                    } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f")) {
                        openPacket();
                    } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
                        close();
                        waitUpdateTextView();
                    } else {
                        findMoneyAndOpen(event);
                    }


            }
        }
    }

    private void waitUpdateTextView() {
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void findMoneyAndOpen(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        Log.w(TAG, "findMoneyAndOpen: " + "          进入");
        /**
         * 获取ListView
         */

        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> items = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/a2i");
            if (items.size() != 0)
                mListView = items.get(0);
            if (mListView != null) {
                mMoneyBigNode = mListView.getChild(mListView.getChildCount() - 1);
                if (mMoneyBigNode != null) {
                    List<AccessibilityNodeInfo> list = mMoneyBigNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/a5c");
                    if (list.size() > 0) {
                        list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    /**
     * 处理通知栏信息
     * <p>
     * 如果是微信红包的提示信息,则模拟点击
     *
     * @param event
     */
    private void handleNotification(AccessibilityEvent event) {
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                Log.w("我是微信消息", "handleNotification:    " + content);


                /**
                 * 在这里调用WindowManager里的接口
                 */
                if(MyWindowManager.isPetWindowShowing()){
                    PetControl.displayPetMessage("微信消息："+content);
                }


                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if (content.contains("[微信红包]")) {
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 关闭红包详情界面,实现自动返回聊天窗口
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void close() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了关闭按钮的id
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gv");
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : infos) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 模拟点击,拆开红包
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了红包控件的id
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bjj");
            nodeInfo.recycle();

            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.w(TAG, "onInterrupt: ");
    }

    @Override
    protected void onServiceConnected() {
        Log.w(TAG, "onServiceConnected: ");
        super.onServiceConnected();
    }



}
