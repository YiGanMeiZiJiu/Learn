package com.learn.odps;

//import com.aliyun.odps.Instance;
//import com.aliyun.odps.Odps;
//import com.aliyun.odps.OdpsException;
//import com.aliyun.odps.account.Account;
//import com.aliyun.odps.account.AliyunAccount;
//import com.aliyun.odps.data.Record;
//import com.aliyun.odps.task.SQLTask;
//
//import java.util.List;
//
///**
// * @author 王🍊 2020-01-11
// */
//public class MaxCompute {
//
//    private static volatile Odps odps;
//
//    /**
//     * 单例获得Odps对象
//     * @return
//     */
//    public static Odps getOdps() {
//        if (odps == null) {
//            synchronized (MaxCompute.class) {
//                if (odps == null) {
//                    Account account = new AliyunAccount("access_id", "access_key");
//                    Odps odps = new Odps(account);
//                    String odpsUrl = "odps endpoint";
//                    odps.setEndpoint(odpsUrl);
//                    // 类似database；
//                    odps.setDefaultProject("my project");
//                }
//            }
//        }
//        return odps;
//    }
//
//    /**
//     * 执行SQL获得返回值
//     * @param sql
//     * @return
//     */
//    public static List executeSQL(String sql) {
//        Odps odps = getOdps();
//        Instance instance;
//        try {
//            instance = SQLTask.run(odps, sql);
//            instance.waitForSuccess();
//            List<Record> result = SQLTask.getResult(instance);
//            return result;
//        } catch (OdpsException e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }
//
//}
